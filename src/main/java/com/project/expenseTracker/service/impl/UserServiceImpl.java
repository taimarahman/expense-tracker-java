package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.model.Users;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;
    @Override
    public Users register(Users user) {
        try {
            return userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the list of expenses", e);
        }
    }

    @Override
    public Users findUser(Users user) {
        return userRepo.findUsersByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean isUserEmailExists(String email) {
        return userRepo.existsByEmail(email);
    }
}
