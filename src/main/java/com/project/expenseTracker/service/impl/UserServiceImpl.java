package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.model.UserProfileInfo;
import com.project.expenseTracker.model.Users;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void register(UserInfoReqData user) {
        try {
            Users newUser = new Users(user.getUsername(), user.getEmail());

            String encryptedPass = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(encryptedPass);

            UserProfileInfo newUserProfile = new UserProfileInfo(user.getFirstName(), user.getLastName());
            newUser.setUserProfileInfo(newUserProfile);

            userRepo.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the list of expenses", e);
        }
    }

    @Override
    public Users authenticateLogin(Users user) {
        try {
            Users foundUser = userRepo.findUsersByUsername(user.getUsername());
            if(foundUser != null){
                if(passwordEncoder.matches(user.getPassword(), foundUser.getPassword())){
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
            Users foundUser = userRepo.findUsersByUsername(username);
            if(foundUser != null){
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
            Users user = userRepo.findUsersByUsername(username);

            if(user != null && !profileImage.isEmpty()){
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
                        .profileImageUrl("profile-images/" +imagePath)
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
