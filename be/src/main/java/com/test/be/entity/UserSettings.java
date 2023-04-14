package com.test.be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String key;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserSettings() {
    }

    public UserSettings(String key, User user, String value) {
        this.key = key;
        this.value = value;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +'\''+ key + '\'' +":"+ '\'' +
                value + '\'' +
                '}';
    }
}
