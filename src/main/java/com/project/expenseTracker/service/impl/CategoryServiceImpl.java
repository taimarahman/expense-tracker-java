package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.dto.request.CategoryRequest;
import com.project.expenseTracker.model.Category;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//            Category categoryInfo = convertToEntity(categoryReqData);
//            System.out.println(categoryInfo);
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

                for (CategoryRequest sub: reqData
                     ) {
                    Category subCategory = new Category(sub.getCategoryName(), sub.getDescription(), parentCategory);
                    parentCategory.getSubcategories().add(subCategory);
                }

                categoryRepo.save(parentCategory);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Category> getIdWiseCategoryDetails(String categoryId) {
        return categoryRepo.findById(Integer.valueOf(categoryId));
    }

    private Category convertToEntity(CategoryRequest categoryReqData) {
        return modelMapper.map(categoryReqData, Category.class);
    }
}
