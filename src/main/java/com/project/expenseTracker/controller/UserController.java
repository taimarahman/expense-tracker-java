package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.WebConstants;
import com.project.expenseTracker.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping(WebConstants.USER_REGISTER_API)
    public ResponseEntity<String> register(@RequestBody Users user){
        return null;
    }
    
}
