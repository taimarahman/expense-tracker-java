package com.project.expenseTracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profession;

    public UserInfoResponse(String username, String email, String firstname, String lastname, String profession) {
        this.username = username;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.profession = profession;
    }
}
