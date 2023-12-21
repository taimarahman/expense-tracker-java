package com.project.expenseTracker.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInfoRequest {
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String profession;
    private MultipartFile profileImage;
}
