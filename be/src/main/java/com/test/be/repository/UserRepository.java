package com.test.be.repository;

import com.test.be.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.is_active = true")
    public Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.ssn = ?1")
    public User getUserBySSN(String checkSsn);
}
