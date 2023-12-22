package com.project.expenseTracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserProfileRequest {
    private String firstName;
    private String lastName;
    private String profession;
}
