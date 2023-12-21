package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.UserInfoRequest;
import com.project.expenseTracker.dto.response.UserInfoResponse;
import com.project.expenseTracker.model.UserProfileInfo;
import com.project.expenseTracker.model.Users;

import java.io.IOException;


public interface UserService {
     void register(UserInfoRequest user);
     UserInfoResponse authenticateLogin(Users user);

     boolean isUsernameExists(String username);

     boolean isUserEmailExists(String email);

     String updateUserProfile(String username, UserInfoRequest userInfo) throws IOException;
}
