package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.CategoryReqData;
import com.project.expenseTracker.dto.response.CategoryResData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.service.CategoryService;
import com.project.expenseTracker.service.UserService;
import com.project.expenseTracker.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = WebAPIUrlConstants.CATEGORY_API)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    @PostMapping(value = WebAPIUrlConstants.CATEGORY_CREATE_API, produces = "application/json")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryReqData categoryReqData){
        try{
            Long currentUserId = userService.findIdByUsername(SecurityUtils.getCurrentUsername());
            if(Objects.nonNull(currentUserId)){
                categoryService.addCategory(categoryReqData, currentUserId);
                return ResponseHandler.generateResponse(ResponseMessageConstants.SAVE_SUCCESS, HttpStatus.CREATED);
            } else {
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = WebAPIUrlConstants.CATEGORY_SUB_ADD_API, produces = "application/json")
    public ResponseEntity<Object> addSubcategory(@PathVariable Long categoryId, @RequestBody List<CategoryReqData> reqData) {
        try {
            Long currentUserId = userService.findIdByUsername(SecurityUtils.getCurrentUsername());

            if(Objects.nonNull(currentUserId)){
                String successMsg = categoryService.addSubcategory(categoryId, reqData, currentUserId);

                if(Objects.nonNull(successMsg) && !successMsg.isEmpty()){
                    return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.CATEGORY_ID_WISE_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getIdWiseCategoryList(@PathVariable Long categoryId) {
        try {
            CategoryResData category = categoryService.getIdWiseCategoryDetails(categoryId);

            if(Objects.nonNull(category)){
                return ResponseHandler.generateResponse(category, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(null, ResponseMessageConstants.DATA_NOT_FOUND, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.CATEGORY_ALL_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getCategoryDetails() {
        try {

            Long currentUserId = userService.findIdByUsername(SecurityUtils.getCurrentUsername());

            if(Objects.nonNull(currentUserId)){
                List<CategoryResData> allCategory = categoryService.getAllCategory(currentUserId);

                if(Objects.nonNull(allCategory) && allCategory.size() > 0){
                    return ResponseHandler.generateResponse(allCategory, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(null, ResponseMessageConstants.DATA_NOT_FOUND, HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }
}
