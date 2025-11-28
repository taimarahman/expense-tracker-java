package com.project.expenseTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResData {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String profession;

    private String address;

    private String profileImageUrl;
}