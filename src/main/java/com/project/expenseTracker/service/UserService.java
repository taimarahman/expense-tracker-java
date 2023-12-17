package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.UserInfoRequest;
import com.project.expenseTracker.dto.response.UserInfoResponse;
import com.project.expenseTracker.model.Users;


public interface UserService {
     void register(UserInfoRequest user);
     UserInfoResponse authenticateLogin(Users user);

     boolean isUsernameExists(String username);

     boolean isUserEmailExists(String email);
}
