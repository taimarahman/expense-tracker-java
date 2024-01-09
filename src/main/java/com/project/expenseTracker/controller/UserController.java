package com.project.expenseTracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.model.Users;
import com.project.expenseTracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping(WebAPIUrlConstants.USER_API)
public class UserController {

    private final ObjectMapper objectMapper;

    public UserController(){
        this.objectMapper = new ObjectMapper();
    }
    @Autowired
    UserService userService;
    @PostMapping(WebAPIUrlConstants.USER_REGISTER_API)
    public ResponseEntity<Object> register(@RequestBody UserInfoReqData user){
        try{
            if(userService.isUsernameExists(user.getUsername()))
                return ResponseHandler.generateResponse("Username already exists", HttpStatus.BAD_REQUEST);

            if(userService.isUserEmailExists(user.getEmail()))
                return ResponseHandler.generateResponse("Email already exists", HttpStatus.BAD_REQUEST);

            if(user.getUsername() != null && user.getEmail() != null)
                userService.register(user);

            return ResponseHandler.generateResponse("success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("failure", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping( value=WebAPIUrlConstants.USER_LOGIN_API, produces="application/json" )
    public ResponseEntity<Object> login(@RequestBody Users user, HttpSession session){
        try{
            Users loggedUser = userService.authenticateLogin(user);
            if(Objects.nonNull(loggedUser)){
                session.setAttribute("currentUserId", loggedUser.getUserId());
                session.setAttribute("currentUser", loggedUser.getUsername());
                return ResponseHandler.generateResponse("User login successfully", HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(null, "Login Failure! Username or Password incorrect", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping( value = WebAPIUrlConstants.USER_PROFILE_UPDATE_API, consumes={"multipart/form-data"}, produces = "application/json")
    public ResponseEntity<Object> updateUserProfile(@PathVariable String username, @RequestParam("userProfileInfo") String userProfileInfo, @RequestParam MultipartFile profileImage){
        try{
            UserProfileReqData userInfo = objectMapper.readValue(userProfileInfo, UserProfileReqData.class);
            String successMsg = userService.updateUserProfile(username, profileImage, userInfo);

            if(Objects.nonNull(successMsg) && !successMsg.isEmpty()){
                return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.USER_PROFILE_INFO_API, produces = "application/json")
    public ResponseEntity<Object> getUserProfileInfo(@PathVariable String username, HttpSession session){
        try{
            if(session.getAttribute("currentUser").equals(username)){
                UserInfoResData userInfo = userService.getUserProfileInfo(username);
                if(userInfo != null){
                    return ResponseHandler.generateResponse(userInfo, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
                } else {
                    return ResponseHandler.generateResponse(null, ResponseMessageConstants.DATA_NOT_FOUND, HttpStatus.BAD_REQUEST);
                }
            } else {
                return  ResponseHandler.generateResponse(null, ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.FORBIDDEN);
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
