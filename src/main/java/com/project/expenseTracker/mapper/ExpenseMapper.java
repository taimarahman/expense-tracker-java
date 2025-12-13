package com.project.expenseTracker.mapper;


import com.project.expenseTracker.dto.CategoryDto;
import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.entity.Category;
import com.project.expenseTracker.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.name")
    ExpenseDto mapToDto(Expense expense);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    Expense mapToEntity(ExpenseDto expenseDto);

    List<ExpenseDto> mapToDtoList(List<Expense> expenseList);
}
