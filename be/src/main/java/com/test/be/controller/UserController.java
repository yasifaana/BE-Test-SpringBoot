package com.test.be.controller;

import com.test.be.entity.User;
import com.test.be.entity.UserSettings;
import com.test.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public HashMap<String, Object> userList(
            @RequestParam(name = "max_records", defaultValue = "5") int maxRecord,
            @RequestParam(name = "offset", defaultValue = "0") int offset){
        Page<User> page = service.getDataUser(maxRecord, offset);
        List<User> userData = page.getContent();
        HashMap<String, Object> show = new HashMap<>();
        show.put("user_data", userData);
        show.put("max_records", String.valueOf(maxRecord));
        show.put("offset", String.valueOf(offset));
        return show;
    }

    @GetMapping("/users/{id}")
    public HashMap<Object, Object> userById(@PathVariable(name = "id") int id) throws Exception {
        User getUser = service.getUserDataID(id);
        List<UserSettings> getUserSettings = service.getUserSettingsID(id);
        return service.showUserandSetting(getUser, getUserSettings);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<Object, Object> addUser(@RequestBody User user) throws Exception {
        return service.addNewUser(user);
    }

    @PutMapping("/users/{id}")
    public HashMap<Object, Object> updateUser(@PathVariable(name = "id") int id,
                                              @RequestBody User user) throws Exception {
        return service.updateDataUser(id, user);
    }

    @PutMapping("/users/{id}/settings")
    public HashMap<Object, Object> updateSettings(@PathVariable(name = "id") int id,
                                                  @RequestBody ArrayList<HashMap<String, String>> userSettings) throws Exception {
        return service.updateUserSettings(id, userSettings);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "id") int id){
        service.softDelUser(id);
    }

    @PutMapping("/users/{id}/refresh")
    public HashMap<Object, Object> reactiveUser(@PathVariable(name = "id") int id){
        return service.activateUser(id);
    }
}
