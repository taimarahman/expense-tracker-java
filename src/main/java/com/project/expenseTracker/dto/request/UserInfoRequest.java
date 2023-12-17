package com.project.expenseTracker.dto.request;

import lombok.Data;

@Data
public class UserInfoRequest {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String profession;
}
