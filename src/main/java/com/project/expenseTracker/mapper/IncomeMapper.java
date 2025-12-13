package com.project.expenseTracker.mapper;


import com.project.expenseTracker.dto.IncomeDto;
import com.project.expenseTracker.entity.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);

    IncomeDto mapToDto(Income income);

    @Mapping(target = "user", ignore = true)
    Income mapToEntity(IncomeDto incomeDto);

    List<IncomeDto> mapToDtoList(List<Income> incomeList);
}
