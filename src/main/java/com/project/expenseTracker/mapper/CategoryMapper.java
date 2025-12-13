package com.project.expenseTracker.mapper;


import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper{

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapToDto(Category category);

    @Mapping(target = "user", ignore = true)
    Category mapToEntity(CategoryDto categoryDto);

    List<CategoryDto> mapToDtoList(List<Category> categoryList);
}
