package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.dto.response.CategoryResponse;
import com.project.expenseTracker.model.Category;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(CategoryRequest categoryReqData) {
        try{
            Category category = new Category(categoryReqData.getCategoryName(), categoryReqData.getDescription());
            categoryRepo.save(category);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String addSubcategory(Integer categoryId, List<CategoryRequest> reqData) {
        try {
            Optional<Category> category = categoryRepo.findById(categoryId);

            if(category.isPresent()){
                Category parentCategory = category.get();
                if (parentCategory.getParentId() == null){
                    for (CategoryRequest sub: reqData
                    ) {
                        Category subCategory = new Category(sub.getCategoryName(), sub.getDescription(), categoryId);
                        categoryRepo.save(subCategory);
                    }

                    return ResponseMessageConstants.SAVE_SUCCESS;

                } else {
                    return "Invalid Category to add subcategory";
                }
            } else {
                return "Invalid Category to add subcategory";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public CategoryResponse getIdWiseCategoryDetails(Integer categoryId) {
        try{
            Optional<Category> category = categoryRepo.findById(categoryId);
            if(category.isPresent()){
                Category parentCategory = category.get();

                List<Category> subcategories = categoryRepo.findByParentId(categoryId);
                List<CategoryResponse> subcategoryList = new ArrayList<>();
                for (Category sub: subcategories
                ) {
                    CategoryResponse subCategory = new CategoryResponse();
                    subcategoryList.add(CategoryResponse.builder()
                    .categoryName(sub.getCategoryName())
                    .description(sub.getDescription())
                    .build()
                    );
                }
                return CategoryResponse.builder()
                        .categoryName(parentCategory.getCategoryName())
                        .description(parentCategory.getDescription())
                        .subcategories(subcategoryList)
                        .build();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        try {
            List<CategoryResponse> allCategories = new ArrayList<>();
            List<Category> categories = categoryRepo.findAllWhereParentIdIsNull();
            if(categories.size() > 0){
                for (Category c: categories) {
                    CategoryResponse cr = new CategoryResponse(c.getCategoryName(), c.getDescription());
                    List<Category> subcategories = categoryRepo.findByParentId(c.getCategoryId());

                    if(subcategories.size() > 0) {
                        for (Category sub :
                                subcategories) {
                            cr.getSubcategories().add(CategoryResponse.builder()
                                    .categoryName(sub.getCategoryName())
                                    .description(sub.getDescription())
                                    .build());
                        }

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
