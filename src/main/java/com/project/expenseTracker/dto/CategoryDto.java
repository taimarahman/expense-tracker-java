package com.project.expenseTracker.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        Long categoryId,

        @NotBlank(message = "Category name is required.")
        String name,

        @NotBlank(message = "Category key is required.")
        String key,

        String description,

        String icon,

        Long parentId,

        Boolean isActive
) {
}
