package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {
     void register(UserInfoReqData user);
     Users authenticateLogin(Users user);

     UserInfoResData getUserProfileInfo(String username);

     boolean isUsernameExists(String username);

     boolean isUserEmailExists(String email);

     Long findIdByUsername(String username);

     String updateUserProfile(String username, MultipartFile profileImage, UserProfileReqData userInfo) throws IOException;
}
