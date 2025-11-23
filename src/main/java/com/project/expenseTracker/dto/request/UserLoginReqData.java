package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginReqData {
    @NotBlank(message = "Username can not be empty.")
    private String username;
    @NotBlank(message = "Password can not be empty.")
    private String password;
}
