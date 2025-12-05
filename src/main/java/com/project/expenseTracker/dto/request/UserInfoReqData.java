package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoReqData {

    @NotBlank(message = "Username can not be empty.")
    private String username;

    @NotBlank(message = "Email can not be empty.")
    @Email
    private String email;
    @NotBlank(message = "Password can not be empty.")
    private String password;

    private String firstName;
    private String lastName;
    private String profession;
    private String activeYn;
}
