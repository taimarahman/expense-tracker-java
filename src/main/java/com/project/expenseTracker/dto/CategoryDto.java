package com.project.expenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long categoryId;

    @NotBlank(message = "Category name is required.")
    private String name;

    @NotBlank(message = "Category key is required.")
    private String key;
    private String description;
    private String icon;
    private Long parentId;
    private Boolean isActive;
}
