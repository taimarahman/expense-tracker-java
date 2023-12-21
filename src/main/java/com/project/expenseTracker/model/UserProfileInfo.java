package com.project.expenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProfileId;
    private String firstname;
    private String lastname;
    private String profession;
    private String profileImageUrl;

    public UserProfileInfo(String firstname, String lastname, String profession) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.profession = profession;
    }

}
