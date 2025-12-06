package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.response.CategoryResData;
import com.project.expenseTracker.dto.response.ResponseBaseData;

import java.util.List;

public interface CategoryService {
    ResponseBaseData saveUpdateCategory(CategoryDto categoryReqData, Long currentUserId);

    ResponseBaseData getIdWiseCategoryDetails(Long userId, Long categoryId);

    List<CategoryResData> getAllCategory(Long currentUserId);
}
