package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryReqData);

    String addSubcategory(Integer categoryId, List<CategoryRequest> reqData);

    CategoryResponse getIdWiseCategoryDetails(Integer categoryId);
}
