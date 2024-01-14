package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserInfoReqData {

    @NotEmpty(message = "Username can not be empty.")
    private String username;
    @NotEmpty(message = "Email can not be empty.")
    private String email;
    @NotEmpty(message = "Password can not be empty.")
    private String password;

    private String firstName;
    private String lastName;
    private String profession;
}
