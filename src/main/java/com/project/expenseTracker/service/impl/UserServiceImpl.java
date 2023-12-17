package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.request.UserInfoRequest;
import com.project.expenseTracker.dto.response.UserInfoResponse;
import com.project.expenseTracker.model.UserProfileInfo;
import com.project.expenseTracker.model.Users;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void register(UserInfoRequest user) {
        try {
            Users newUser = new Users(user.getUsername(), user.getEmail());

            String encryptedPass = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(encryptedPass);

            UserProfileInfo newUserProfile = new UserProfileInfo(user.getFirstname(), user.getLastname(), user.getProfession());
            newUser.setUserProfileInfo(newUserProfile);

            userRepo.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the list of expenses", e);
        }
    }

    @Override
    public UserInfoResponse authenticateLogin(Users user) {
        Users foundUser = userRepo.findUsersByUsername(user.getUsername());
        if(foundUser != null){
            if(passwordEncoder.matches(user.getPassword(), foundUser.getPassword())){
                UserProfileInfo foundUserProfile = foundUser.getUserProfileInfo();

                return UserInfoResponse.builder()
                        .username(foundUser.getUsername())
                        .email(foundUser.getEmail())
                        .firstname(foundUserProfile.getFirstname())
                        .lastname(foundUserProfile.getLastname())
                        .profession(foundUserProfile.getProfession())
                        .build();
            }
        }
        return null;
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
