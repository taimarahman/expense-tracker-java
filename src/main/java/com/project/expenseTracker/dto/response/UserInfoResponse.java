package com.project.expenseTracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String profession;

    public UserInfoResponse(String username, String email, String firstname, String lastname, String profession) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profession = profession;
    }
}
