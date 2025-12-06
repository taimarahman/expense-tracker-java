package com.project.expenseTracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.UserInfoReqData;
import com.project.expenseTracker.dto.request.UserLoginReqData;
import com.project.expenseTracker.dto.request.UserProfileReqData;
import com.project.expenseTracker.dto.response.ResponseHandler;
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

import java.util.Objects;

@RestController
@RequestMapping(WebAPIUrlConstants.USER_API)
public class UserController {

    private final ObjectMapper objectMapper;

    public UserController() {
        this.objectMapper = new ObjectMapper();
    }

    @Autowired
    UserService userService;

    @PostMapping(WebAPIUrlConstants.USER_REGISTER_API)
    public ResponseEntity<Object> register(@Valid @RequestBody UserInfoReqData user) {
        userService.register(user);
        return ResponseHandler.generateResponse("Successfully registered user!", HttpStatus.CREATED);
    }

    @PostMapping(value = WebAPIUrlConstants.USER_LOGIN_API, produces = "application/json")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginReqData reqData, HttpServletRequest req) {
        User loggedUser = userService.authenticateLogin(reqData);

        if (Objects.nonNull(loggedUser)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUserId", loggedUser.getUserId());
            session.setAttribute("currentUser", loggedUser.getUsername());
            return ResponseHandler.generateResponse("User login successfully", HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(null, "Login Failure! Username or Password incorrect", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = WebAPIUrlConstants.USER_PROFILE_INFO_API,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUserProfile(@PathVariable String username,
                                                    @Valid @ModelAttribute UserProfileReqData userProfileInfo) {
        try {
            String successMsg = userService.updateUserProfile(username, userProfileInfo);

            return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.USER_PROFILE_INFO_API, produces = "application/json")
    public ResponseEntity<Object> getUserProfileInfo(@PathVariable String username, HttpSession session) {
        if (!session.getAttribute("currentUser").equals(username)) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        UserInfoResData userInfo = userService.getUserProfileInfo(username);
        return ResponseHandler.generateResponse(userInfo, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
    }
}
