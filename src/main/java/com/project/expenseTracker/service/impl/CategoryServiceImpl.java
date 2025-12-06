package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.response.CategoryResData;
import com.project.expenseTracker.dto.response.ResponseBaseData;
import com.project.expenseTracker.dto.response.ResponseSuccessData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.model.Category;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseBaseData saveUpdateCategory(CategoryDto reqData, Long currentUserId) {
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

            return new ResponseSuccessData("Category updated successfully!", HttpStatus.OK);
        }

        Category category = Category.builder()
                .key(reqData.getKey())
                .name(reqData.getName())
                .description(reqData.getDescription())
                .parentId(reqData.getParentId())
                .userId(currentUserId)
                .createdBy(currentUserId)
                .isActive(true)
                .build();

        categoryRepository.save(category);

        return new ResponseSuccessData("Category saved successfully!", HttpStatus.CREATED);
    }


    @Override
    public ResponseBaseData getIdWiseCategoryDetails(Long userId, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );

        if(!category.getUserId().equals(userId)){
            throw new ForbiddenException("You are not authorized to view this category");
        }

        return new ResponseSuccessData(category.toCategoryDto(),
                "Category found successfully!", HttpStatus.FOUND);
    }

    @Override
    public List<CategoryResData> getAllCategory(Long currentUserId) {
        try {
            List<CategoryResData> allCategories = new ArrayList<>();
            List<Category> categories;
            Long adminId = userRepository.findIdByUsername("admin");

            if (adminId.equals(currentUserId)) {
                categories = categoryRepository.findByParentId(null);
            } else {
                categories = categoryRepository.findAllCategoryByUserId(currentUserId, adminId);
            }

            if (categories.size() > 0) {
                for (Category c : categories) {
                    CategoryResData cr = new CategoryResData();
                    List<Category> subcategories;

                    if (adminId.equals(currentUserId)) {
                        subcategories = categoryRepository.findByParentId(c.getCategoryId());
                    } else {
                        subcategories = categoryRepository.findByParentIdAndCreatedByIn(c.getCategoryId(), List.of(adminId, currentUserId));
                    }

                    if (subcategories.size() > 0) {

                        cr.getSubcategories().addAll(
                                subcategories.stream()
                                        .map(sub -> CategoryResData.builder()
                                                .categoryName(sub.getName())
                                                .description(sub.getDescription())
                                                .build())
                                        .collect(Collectors.toList())
                        );
                    }
                    allCategories.add(cr);
                }
                return allCategories;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
