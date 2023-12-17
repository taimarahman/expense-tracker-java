package com.project.expenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserProfileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProfileId;
    private String firstname;
    private String lastname;
    private String profession;

    public UserProfileInfo(String firstname, String lastname, String profession) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.profession = profession;
    }

    public UserProfileInfo() {}
}
