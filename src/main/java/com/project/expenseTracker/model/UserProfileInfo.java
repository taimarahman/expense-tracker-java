package com.project.expenseTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserProfileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileId;
    private String firstName;
    private String lastName;
    private String profession;
    private String profileImageUrl;

    public UserProfileInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
