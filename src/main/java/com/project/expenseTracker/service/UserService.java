package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.UserInfoRequest;
import com.project.expenseTracker.dto.request.UserProfileRequest;
import com.project.expenseTracker.dto.response.UserInfoResponse;
import com.project.expenseTracker.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {
     void register(UserInfoRequest user);
     Users authenticateLogin(Users user);
     UserInfoResponse getUserProfileInfo(String username);

     boolean isUsernameExists(String username);

     boolean isUserEmailExists(String email);

     String updateUserProfile(String username, MultipartFile profileImage, UserProfileRequest userInfo) throws IOException;
}
