package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Users;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebAPIUrlConstants.USER_API)
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping(WebAPIUrlConstants.USER_REGISTER_API)
    public ResponseEntity<Object> register(@RequestBody Users user){
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
    public ResponseEntity<Object> login(@RequestBody Users user){
        try{
            Users foundUser = userService.findUser(user);
            System.out.println(foundUser);
            if(foundUser != null){
                return ResponseHandler.generateResponse(foundUser, "User login successfully", HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(null, "Login Failure! Username or Password incorrect", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
