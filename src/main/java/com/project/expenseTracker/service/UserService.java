package com.project.expenseTracker.service;

import com.project.expenseTracker.model.Users;
import org.springframework.stereotype.Service;


public interface UserService {
     Users register(Users user);
     Users findUser(Users user);

     boolean isUsernameExists(String username);

     boolean isUserEmailExists(String email);
}
