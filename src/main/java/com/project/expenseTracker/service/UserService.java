package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.UserInfoDto;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.entity.Users;

import java.io.IOException;


public interface UserService {
     ApiResponse register(UserInfoDto user);

     Users authenticateLogin(UserLoginReqData reqData);

     UserInfoDto getUserProfileInfo(String username);

     ApiResponse updateUserProfile(String username, UserProfileReqData userInfo) throws IOException;
}
