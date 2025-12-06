package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.service.CategoryService;
import com.project.expenseTracker.service.UserService;
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
    private final UserService userService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<Object> saveUpdateCategory(@RequestBody @Valid CategoryDto categoryReqData, HttpSession session) {
        return ResponseEntity.ok(categoryService.saveUpdateCategory(categoryReqData, getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.CATEGORY_ID_WISE_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getIdWiseCategoryList(@PathVariable Long id, HttpSession session) {
        return ResponseEntity.ok(categoryService.getIdWiseCategoryDetails(getCurrentUserId(session), id));
    }

}
