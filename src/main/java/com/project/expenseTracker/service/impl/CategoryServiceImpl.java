package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.CategoryResData;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.entity.Category;
import com.project.expenseTracker.entity.User;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResponse saveUpdateCategory(CategoryDto reqData, Long currentUserId) {
        // update
        if (reqData.getCategoryId() != null) {
            Category category = categoryRepository.findById(reqData.getCategoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("Category not found"));

            if (!category.getKey().equals(reqData.getKey())) {
                throw new ForbiddenException("Category key cannot be changed.");
            }

            category.setName(reqData.getName());
            category.setDescription(reqData.getDescription());
            category.setParentId(reqData.getParentId());
            category.setIsActive(reqData.getIsActive());
            category.setUpdatedBy(currentUserId);
            categoryRepository.save(category);

            return SuccessResponse.of("Category updated successfully!");
        }

        User user = userRepository.findById(currentUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Category category = Category.builder()
                .key(reqData.getKey())
                .name(reqData.getName())
                .description(reqData.getDescription())
                .parentId(reqData.getParentId())
                .user(user)
                .createdBy(currentUserId)
                .isActive(true)
                .build();

        categoryRepository.save(category);

        return SuccessResponse.of("Category saved successfully!", HttpStatus.CREATED);
    }


    @Override
    public ApiResponse getCategoryDetailsById(Long userId, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );

        if (!category.getUser().getUserId().equals(userId)) {
            throw new ForbiddenException("You are not authorized to view this category");
        }

        return SuccessResponse.of(category.toCategoryDto(),
                "Category found successfully!", HttpStatus.FOUND);
    }

    @Override
    public ApiResponse getAllCategory(Long currentUserId) {
        List<CategoryResData> allCategories = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllByUserIdAndParentIdIsNull(currentUserId);

        if (!categories.isEmpty()) {
            allCategories = categories.stream().map(
                            c -> {
                                CategoryResData category = CategoryResData.builder()
                                        .categoryName(c.getName())
                                        .description(c.getDescription())
                                        .build();

                                List<Category> subcategories = categoryRepository.findByParentId(c.getCategoryId());
                                category.setSubcategories(subcategories.stream().map(
                                        sub -> CategoryResData.builder()
                                                .categoryName(sub.getName())
                                                .description(sub.getDescription())
                                                .build()).toList());
                                return category;
                            })
                    .toList();
        }

        return SuccessResponse.of(allCategories, "Categories found successfully!");
    }

    @Override
    public ApiResponse getAllParentCategory(Long currentUserId) {
        List<Category> categoryList = categoryRepository.findAllCategoryByUserId(currentUserId);

        List<CategoryDto> list = new ArrayList<>();
        if (!categoryList.isEmpty()) {
            list = categoryList.stream().map(Category::toCategoryDto).toList();
        }

        return SuccessResponse.of(list, "Categories found successfully!");
    }

    @Override
    public ApiResponse getSubcategoryByParent(Long categoryId, Long currentUserId) {
        Category category = categoryRepository.findByCategoryIdAndUserId(categoryId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Category> subcategories = categoryRepository.findByParentId(category.getCategoryId());
        List<CategoryDto> responseList = new ArrayList<>();

        if (!subcategories.isEmpty()) {
            responseList = subcategories.stream().map(Category::toCategoryDto).toList();
        }
        return SuccessResponse.of(responseList, "Subcategories found successfully!");
    }

}
