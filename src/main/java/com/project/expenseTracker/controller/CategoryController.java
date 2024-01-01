package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Category;
import com.project.expenseTracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = WebAPIUrlConstants.CATEGORY_API)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping(value = WebAPIUrlConstants.CATEGORY_CREATE_API, produces = "application/json")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryRequest categoryReqData){
        try{
            categoryService.addCategory(categoryReqData);
            return ResponseHandler.generateResponse(ResponseMessageConstants.SAVE_SUCCESS, HttpStatus.CREATED);

        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = WebAPIUrlConstants.CATEGORY_SUB_ADD_API, produces = "application/json")
    public ResponseEntity<Object> addSubcategory(@PathVariable Integer categoryId, @RequestBody List<CategoryRequest> reqData) {
        try {
            String successMsg = categoryService.addSubcategory(categoryId, reqData);
            return ResponseHandler.generateResponse(ResponseMessageConstants.SAVE_SUCCESS, HttpStatus.CREATED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping(value = WebAPIUrlConstants.CATEGORY_ID_WISE_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getIdWiseCategoryList(@PathVariable String categoryId) {
        try {
            Optional<Category> category = categoryService.getIdWiseCategoryDetails(categoryId);
            return ResponseHandler.generateResponse(category, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }

}
