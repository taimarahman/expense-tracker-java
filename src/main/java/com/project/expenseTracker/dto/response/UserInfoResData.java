package com.project.expenseTracker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User profile information returned after login or profile fetch")
public class UserInfoResData {

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Registered email address", example = "john@example.com")
    private String email;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Profession or job title", example = "Software Engineer")
    private String profession;

    @Schema(description = "URL of the user's profile picture",
            example = "https://example.com/images/john.jpg",
            nullable = true)
    private String profileImageUrl;
}