package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.CategoryResData;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    ApiResponse saveUpdateCategory(CategoryDto categoryReqData, Long currentUserId);

    ApiResponse getCategoryDetailsById(Long userId, Long categoryId);

    ApiResponse getAllCategory(Long currentUserId);

    ApiResponse getAllParentCategory(Long currentUserId);

    ApiResponse getSubcategoryByParent(Long categoryId, Long currentUserId);
}
