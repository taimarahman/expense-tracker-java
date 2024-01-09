package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.CategoryReqData;
import com.project.expenseTracker.dto.response.CategoryResData;
import com.project.expenseTracker.model.Category;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(CategoryReqData categoryReqData, Long currentUserId) {
        try{
            Category category = new Category(categoryReqData.getCategoryName(), categoryReqData.getDescription(), currentUserId);
            categoryRepo.save(category);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String addSubcategory(Long categoryId, List<CategoryReqData> reqData, Long currentUserId) {
        try {
            Optional<Category> category = categoryRepo.findById(categoryId);

            if(category.isPresent()){
                Category parentCategory = category.get();
                if (parentCategory.getParentId() == null){
                    reqData.stream().map(sub -> new Category(sub.getCategoryName(), sub.getDescription(), categoryId, currentUserId))
                            .forEach(categoryRepo::save);

                    return ResponseMessageConstants.SAVE_SUCCESS;

                } else
                    return "Invalid Category to add subcategory";

            } else
                return "Invalid Category to add subcategory";


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public CategoryResData getIdWiseCategoryDetails(Long categoryId) {
        try{
            Optional<Category> category = categoryRepo.findById(categoryId);
            if(category.isPresent()){
                Category parentCategory = category.get();

                List<Category> subcategories = categoryRepo.findByParentId(categoryId);

                List<CategoryResData> subcategoryList = subcategories.stream()
                        .map(sub -> CategoryResData.builder()
                                .categoryName(sub.getCategoryName())
                                .description(sub.getDescription())
                                .build())
                        .collect(Collectors.toList());

                return CategoryResData.builder()
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
    public List<CategoryResData> getAllCategory(Long currentUserId) {
        try {
            List<CategoryResData> allCategories = new ArrayList<>();
            List<Category> categories;
            Long adminId = userRepo.findIdByUsername("admin");

            if(adminId.equals(currentUserId) ){
                categories = categoryRepo.findByParentId(null);
            } else {
                categories = categoryRepo.findAllCategoryByUserId(currentUserId, adminId);
            }

            if(categories.size() > 0){
                for (Category c: categories) {
                    CategoryResData cr = new CategoryResData(c.getCategoryName(), c.getDescription());
                    List<Category> subcategories;

                    if(adminId.equals(currentUserId) ){
                        subcategories = categoryRepo.findByParentId(c.getCategoryId());
                    } else {
                        subcategories = categoryRepo.findByParentIdAndCreatedByIn(c.getCategoryId(), List.of(adminId,currentUserId));
                    }

                    if(subcategories.size() > 0) {

                        cr.getSubcategories().addAll(
                                subcategories.stream()
                                        .map(sub -> CategoryResData.builder()
                                                .categoryName(sub.getCategoryName())
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
