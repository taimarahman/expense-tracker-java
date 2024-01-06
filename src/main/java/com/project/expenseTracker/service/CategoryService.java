package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryReqData, Long currentUserId);

    String addSubcategory(Long categoryId, List<CategoryRequest> reqData, Long currentUserId);

    CategoryResponse getIdWiseCategoryDetails(Long categoryId);

    List<CategoryResponse> getAllCategory(Long currentUserId);
}
