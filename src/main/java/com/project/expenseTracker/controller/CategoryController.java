package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = WebAPIUrlConstants.CATEGORY_API)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveUpdateCategory(@Valid @RequestBody CategoryDto reqData, HttpSession session) {
        return ResponseEntity.ok(categoryService.saveUpdateCategory(reqData, getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.CATEGORY_ID_WISE_DETAILS_API, produces = "application/json")
    public ResponseEntity<ApiResponse> getIdWiseCategoryList(@PathVariable Long id, HttpSession session) {
        return ResponseEntity.ok(categoryService.getCategoryDetailsById(getCurrentUserId(session), id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllParentCategory(HttpSession session) {
        return ResponseEntity.ok(categoryService.getAllParentCategory(getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.SUBCATEGORY_LIST_API)
    public ResponseEntity<ApiResponse> getSubcategoryList(@PathVariable Long categoryId, HttpSession session) {
        return ResponseEntity.ok(categoryService.getSubcategoryByParent(categoryId, getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.CATEGORY_TREE_API)
    public ResponseEntity<ApiResponse> getCategoryTree(HttpSession session) {
        return ResponseEntity.ok(categoryService.getAllCategory(getCurrentUserId(session)));
    }


}
