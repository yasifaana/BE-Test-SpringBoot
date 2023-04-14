package com.test.be.repository;

import com.test.be.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Integer> {
    @Query("SELECT u FROM UserSettings u WHERE u.user.id = ?1")
    List<UserSettings> findByIdUser(int id);
}
