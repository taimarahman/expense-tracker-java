package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {
     ApiResponse register(UserInfoReqData user);

     User authenticateLogin(UserLoginReqData reqData);

     UserInfoResData getUserProfileInfo(String username);

     Long findIdByUsername(String username);

     String updateUserProfile(String username, UserProfileReqData userInfo) throws IOException;
}
