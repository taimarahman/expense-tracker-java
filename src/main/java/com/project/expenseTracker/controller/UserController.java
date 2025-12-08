package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.dto.response.UserInfoResData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.model.User;
import com.project.expenseTracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebAPIUrlConstants.USER_API)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(WebAPIUrlConstants.USER_REGISTER_API)
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserInfoReqData user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping(value = WebAPIUrlConstants.USER_LOGIN_API, produces = "application/json")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginReqData reqData, HttpServletRequest req) {
        User loggedUser = userService.authenticateLogin(reqData);

        HttpSession session = req.getSession(true);
        session.setAttribute("currentUserId", loggedUser.getUserId());
        session.setAttribute("currentUser", loggedUser.getUsername());

        return ResponseEntity.ok(SuccessResponse.of("User login successfully"));
    }

    @PostMapping(value = WebAPIUrlConstants.USER_PROFILE_INFO_API,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable String username,
                                                         @Valid @ModelAttribute UserProfileReqData userProfileInfo) {
        try {
            return ResponseEntity.ok(userService.updateUserProfile(username, userProfileInfo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong", e);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.USER_PROFILE_INFO_API, produces = "application/json")
    public ResponseEntity<ApiResponse> getUserProfileInfo(@PathVariable String username, HttpSession session) {
        if (!session.getAttribute("currentUser").equals(username)) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        UserInfoResData userInfo = userService.getUserProfileInfo(username);
        return ResponseEntity.ok(
                SuccessResponse.of(userInfo, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK));
    }
}
