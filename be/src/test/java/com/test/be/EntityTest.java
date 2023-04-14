package com.test.be;

import com.test.be.entity.User;
import com.test.be.entity.UserSettings;
import com.test.be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EntityTest {

    @Autowired
    private UserRepository repo;

//    @Test
//    public void testCreateUser(){
//        User newUser = new User("0000000000123456", "Test", "User", LocalDate.of(1990, 01, 02));
//        UserSettings newSetting = new UserSettings("biometric_login", "false");
//        System.out.println(this.repo);
//        User savedUser = repo.save(newUser);
//        assertThat(savedUser.getId()).isGreaterThan(0);
//    }
}
