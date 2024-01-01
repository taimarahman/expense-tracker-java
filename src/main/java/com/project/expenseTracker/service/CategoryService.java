package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void addCategory(CategoryRequest categoryReqData);

    String addSubcategory(Integer categoryId, List<CategoryRequest> reqData);

    Optional<Category> getIdWiseCategoryDetails(String categoryId);
}
