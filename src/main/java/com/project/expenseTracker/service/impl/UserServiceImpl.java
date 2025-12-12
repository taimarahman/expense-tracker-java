package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.UserInfoDto;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.entity.User;
import com.project.expenseTracker.entity.UserProfileInfo;
import com.project.expenseTracker.exception.EmailAlreadyExistsException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.exception.UsernameAlreadyExistException;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Configurable upload directory + max size
    private static final String UPLOAD_DIR = "uploads/profile-images/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp");

    @Override
    public ApiResponse register(UserInfoDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
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
                .isActive(true)
                .build();

        userRepository.save(newUser);

        return SuccessResponse.of("Successfully registered user!", HttpStatus.CREATED);
    }

    @Override
    public User authenticateLogin(UserLoginReqData reqData) {
        User foundUser = userRepository.findByUsername(reqData.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));
        if (!passwordEncoder.matches(reqData.getPassword(), foundUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        return foundUser;
    }

    @Override
    @Transactional
    public ApiResponse updateUserProfile(String username, UserProfileReqData reqData) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        UserProfileInfo userProfile = user.getUserProfileInfo();
        if (userProfile == null) {
            userProfile = new UserProfileInfo();
            user.setUserProfileInfo(userProfile);
        }

        userProfile.setFirstName(reqData.getFirstName());
        userProfile.setLastName(reqData.getLastName());
        userProfile.setAddress(reqData.getAddress());
        userProfile.setProfession(reqData.getProfession());

        MultipartFile profileImage = reqData.getProfileImage();

        // Handle profile image upload
        if (profileImage != null && !profileImage.isEmpty()) {
            validateImage(profileImage);

            if (userProfile.getProfileImageUrl() != null) {
                deleteOldImage(userProfile.getProfileImageUrl());
            }

            String fileName = username + "_" + System.currentTimeMillis() + getFileExtension(profileImage);
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            try {
                // Create directory if not exists
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, profileImage.getBytes());
                userProfile.setProfileImageUrl("/" + UPLOAD_DIR + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e); // ← don't swallow!
            }
        }

        userRepository.save(user);
        return SuccessResponse.of("User Profile Updated Successfully!");
    }

    @Override
    public UserInfoDto getUserProfileInfo(String username) {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        UserProfileInfo foundUserProfile = foundUser.getUserProfileInfo();
        if(foundUserProfile == null){
            throw new ResourceNotFoundException("User profile information not found");
        }

        return UserInfoDto.builder()
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .firstName(foundUserProfile.getFirstName())
                .lastName(foundUserProfile.getLastName())
                .profession(foundUserProfile.getProfession())
                .address(foundUserProfile.getAddress())
                .profileImageUrl(foundUserProfile.getProfileImageUrl())
                .build();
    }

    private void deleteOldImage(String imageUrl) {
        try {
            Path oldImgPath = Paths.get("." + imageUrl); // remove leading "/"
            Files.deleteIfExists(oldImgPath);
        } catch (Exception ex) {
        }
    }

    private void validateImage(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File too large! File size should be less than 5MB");
        }
        String ext = getFileExtension(file);
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("Unsupported file type! File type should be .jpg, .jpeg, .png or .webp");
        }
    }

    private String getFileExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        return originalName != null && originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
    }

}
