package com.project.expenseTracker.dto.request;

import lombok.Data;

@Data
public class UserInfoReqData {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profession;
}
