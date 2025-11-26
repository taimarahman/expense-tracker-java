package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.exception.EmailAlreadyExistsException;
import com.project.expenseTracker.exception.UsernameAlreadyExistException;
import com.project.expenseTracker.model.User;
import com.project.expenseTracker.model.UserProfileInfo;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(UserInfoReqData user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String encryptedPass = passwordEncoder.encode(user.getPassword());

        UserProfileInfo newUserProfile = UserProfileInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profession(user.getProfession())
                .build();

        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(encryptedPass)
                .userProfileInfo(newUserProfile)
                .build();

        userRepo.save(newUser);
    }

    @Override
    public User authenticateLogin(UserLoginReqData reqData) {
        try {
            User foundUser = userRepo.findByUsername(reqData.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + reqData.getUsername()));
            if (foundUser != null) {
                if (passwordEncoder.matches(reqData.getPassword(), foundUser.getPassword())) {
                    return foundUser;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public UserInfoResData getUserProfileInfo(String username) {
        try {
            User foundUser = userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            ;
            if (foundUser != null) {
                UserProfileInfo foundUserProfile = foundUser.getUserProfileInfo();

                return UserInfoResData.builder()
                        .username(foundUser.getUsername())
                        .email(foundUser.getEmail())
                        .firstName(foundUserProfile.getFirstName())
                        .lastName(foundUserProfile.getLastName())
                        .profession(foundUserProfile.getProfession())
                        .profileImageUrl(foundUserProfile.getProfileImageUrl())
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    @Override
    public Long findIdByUsername(String username) {
        return userRepo.findIdByUsername(username);
    }

    @Override
    public String updateUserProfile(String username, MultipartFile profileImage, UserProfileReqData userInfo) throws IOException {
        try {
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            ;

            if (user != null && !profileImage.isEmpty()) {
                String originalFilename = profileImage.getOriginalFilename();
                String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
                String imagePath = username + "_profile" + fileExtension;
                Path filePath = Paths.get("src/main/resources/static/profile-images/" + imagePath);
                Files.write(filePath, profileImage.getBytes());

                UserProfileInfo userProfileInfo = UserProfileInfo.builder()
                        .userProfileId(user.getUserProfileInfo().getUserProfileId())
                        .firstName(userInfo.getFirstName())
                        .lastName(userInfo.getLastName())
                        .profession(userInfo.getProfession())
                        .profileImageUrl("profile-images/" + imagePath)
                        .build();

                user.setUserProfileInfo(userProfileInfo);
                userRepo.save(user);

                return "User Profile Updated Successfully";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
