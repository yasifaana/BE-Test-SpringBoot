package com.test.be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "user_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, unique = true, length = 16)
    @NotBlank(message = "Invalid value field ssn, rejected value: ")
    @Length(max = 16, message = "Invalid value field ssn, rejected value: ")
    @Pattern(regexp = "^[0-9]*$", message = "Invalid value field ssn, rejected value: ")
    private String ssn;

    @Column(nullable = false)
    @NotBlank(message = "Invalid value field first_name, rejected value: ")
    @Size(min = 3, max = 100, message = "Invalid value field first_name, rejected value: ")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Invalid value field first_name, rejected value: ")
    private String first_name;

    @Size(min = 3, max = 100, message = "Invalid value field middle_name, rejected value: ")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Invalid value field middle_name, rejected value: ")
    private String middle_name;

    @Column(nullable = false)
    @NotBlank(message = "Invalid value field family_name, rejected value: ")
    @Size(min = 3, max = 100, message = "Invalid value field family_name, rejected value: ")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Invalid value field family_name, rejected value: ")
    private String  family_name;

    @Past(message = "Invalid value field birth_date, rejected value: ")
    private LocalDate birth_date;

    @CreationTimestamp
    private OffsetDateTime created_time;

    @UpdateTimestamp
    private OffsetDateTime updated_time;

    @Column(nullable = false)
    @Size(max = 100)
    private String created_by = "SYSTEM";

    @Column(nullable = false)
    @Size(max = 100)
    private String updated_by = "SYSTEM";

    @Column(nullable = false)
    private boolean is_active = true;

    private OffsetDateTime deleted_time;

    @OneToMany(mappedBy = "user")
    private List<UserSettings> userSettings;

    public User() {
    }

    public User(String first_name, String middle_name, String family_name, LocalDate birth_date){
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.family_name = family_name;
        this.birth_date = birth_date;
    }

    public User(String ssn, String first_name, String middle_name, String family_name, LocalDate birth_date) {
        this.ssn = ssn;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.family_name = family_name;
        this.birth_date = birth_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public OffsetDateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(OffsetDateTime created_time) {
        this.created_time = created_time;
    }

    public OffsetDateTime getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(OffsetDateTime updated_time) {
        this.updated_time = updated_time;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public OffsetDateTime getDeleted_time() {
        return deleted_time;
    }

    public void setDeleted_time(OffsetDateTime deleted_time) {
        this.deleted_time = deleted_time;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", ssn='" + ssn + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", birth_date=" + birth_date +
                ", created_time=" + created_time +
                ", updated_time=" + updated_time +
                ", created_by='" + created_by + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
