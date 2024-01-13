package com.project.expenseTracker.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserInfoReqData {

    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String firstName;
    private String lastName;
    private String profession;
}
