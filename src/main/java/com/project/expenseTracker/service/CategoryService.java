package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.CategoryReqData;
import com.project.expenseTracker.dto.response.CategoryResData;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryReqData categoryReqData, Long currentUserId);

    String addSubcategory(Long categoryId, List<CategoryReqData> reqData, Long currentUserId);

    CategoryResData getIdWiseCategoryDetails(Long categoryId);

    List<CategoryResData> getAllCategory(Long currentUserId);
}
