package com.project.expenseTracker.mapper;


import com.project.expenseTracker.dto.SavingsDto;
import com.project.expenseTracker.entity.Savings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavingsMapper {

    SavingsMapper INSTANCE = Mappers.getMapper(SavingsMapper.class);

    SavingsDto mapToDto(Savings savings);

    @Mapping(target = "user", ignore = true)
    Savings mapToEntity(SavingsDto savingsDto);

    List<SavingsDto> mapToDtoList(List<Savings> savingsList);
}
