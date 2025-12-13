package com.project.expenseTracker.mapper;

import java.util.List;

public interface EntityMapper<E, D> {
    D mapToDto(E entity);
    E mapToEntity(D dto);
    List<D> mapToDtoList(List<E> entityList);
    List<E> mapToEntityList(List<D> dtoList);
}
