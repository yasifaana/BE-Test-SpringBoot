package com.test.be.service;

import com.test.be.entity.User;
import com.test.be.entity.UserSettings;
import com.test.be.repository.UserRepository;
import com.test.be.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository repoUser;

    @Autowired
    private UserSettingsRepository repoSetting;

    public Page<User> getDataUser(int maxRecord, int offset){
        Pageable pageable = PageRequest.of(offset, maxRecord);
        return repoUser.findAll(pageable);
    }

    public HashMap<Object, Object> addNewUser(User user) throws Exception {
        String checkSsn = user.getSsn();
        if (checkSsn.length()<16){
            String leftPad = String.format("%016d", Integer.parseInt(checkSsn));
            if(repoUser.getUserBySSN(leftPad)==null){
                user.setSsn(leftPad);
            } else {
                throw new Exception("SSN Already used");
            }
        }

        checkSpecialChar(user.getFirst_name());
        checkSpecialChar(user.getMiddle_name());
        checkSpecialChar(user.getFamily_name());

        checkAge(user.getBirth_date());

        repoUser.save(user);


        List<UserSettings> listSettings = new ArrayList<>();
        listSettings.add(new UserSettings("biometric_login", user, "false"));
        listSettings.add(new UserSettings("push_notification", user, "false"));
        listSettings.add(new UserSettings("sms_notification", user,"false"));
        listSettings.add(new UserSettings("show_onboarding", user, "false"));
        listSettings.add(new UserSettings("widget_order", user,"1,2,3,4,5"));

        repoSetting.saveAll(listSettings);

        return showUserandSetting(user, listSettings);
    }

    public HashMap<Object, Object> showUserandSetting(User getUser, List<UserSettings> getUserSettings){
        HashMap<Object, Object> showAll = new HashMap<>();
        showAll.put("user_data",getUser);
        showAll.put("user_settings",getUserSettings.toString());
        return showAll;
    }

    private void checkAge(LocalDate birth_date) throws Exception {
        int countAge = Year.now().getValue() - birth_date.getYear();
        if(countAge > 100){
            throw new Exception("CANNOT BE OLDER THAN 100 YEARS");
        }
    }

    public void checkSpecialChar(String name) throws Exception {
        if (name != null){
            Pattern pattern = Pattern.compile("[^a-zA-Z]");
            Matcher matcher = pattern.matcher(name);
            boolean check = matcher.find();
            if(check){
                throw new Exception("There is a special character in your first name");
            }
        }
    }

    public HashMap<Object, Object> updateDataUser(int id, User user) throws Exception {
        if(!(repoUser.findById(id).isEmpty()) && repoUser.findById(id).get().isIs_active()){

            User dataFixed = repoUser.findById(id).get();

            dataFixed.setUpdated_by(user.getUpdated_by());
            dataFixed.setUpdated_time(user.getUpdated_time());

            checkSpecialChar(user.getFirst_name());
            dataFixed.setFirst_name(user.getFirst_name());
            checkSpecialChar(user.getFamily_name());
            dataFixed.setFamily_name(user.getFamily_name());
            checkSpecialChar(user.getMiddle_name());
            dataFixed.setMiddle_name(user.getMiddle_name());

            checkAge(user.getBirth_date());
            dataFixed.setBirth_date(user.getBirth_date());

            repoUser.save(dataFixed);

            return showUserandSetting(dataFixed, repoSetting.findByIdUser(id));

        } else {
            throw new Exception("ID NOT FOUND");
        }
    }

    public HashMap<Object, Object> updateUserSettings(int id, ArrayList<HashMap<String, String>> userSettings) throws Exception {
        if(repoUser.findById(id).get().isIs_active()){
            List<UserSettings> settingsFixed = repoSetting.findByIdUser(id);
            for(int i=0;i<settingsFixed.size();i++){
                settingsFixed.get(i).setValue(userSettings.get(i).get(settingsFixed.get(i).getKey()));
            }
            repoSetting.saveAll(settingsFixed);
            return showUserandSetting(repoUser.findById(id).get(),settingsFixed);
        } else{
            throw new Exception("ID NOT FOUND");
        }
    }

    public void softDelUser(int id) {
        if(!(repoUser.findById(id).isEmpty()) && repoUser.findById(id).get().isIs_active()){
            User dataFixed = repoUser.findById(id).get();
            dataFixed.setIs_active(false);
            dataFixed.setDeleted_time(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
            repoUser.save(dataFixed);
        }
    }

    public HashMap<Object, Object> activateUser(int id) {
        User dataFixed = repoUser.findById(id).get();
        dataFixed.setIs_active(true);
        dataFixed.setDeleted_time(null);
        repoUser.save(dataFixed);
        return showUserandSetting(repoUser.findById(id).get(), getUserSettingsID(id));
    }

    public User getUserDataID(int id) throws Exception {
        if(!(repoUser.findById(id).isEmpty()) && repoUser.findById(id).get().isIs_active()) {
            return repoUser.findById(id).get();
        } else{
            throw new Exception("ID NOT FOUND");
        }
    }

    public List<UserSettings> getUserSettingsID(int id) {
        return repoSetting.findByIdUser(id);
    }

}
