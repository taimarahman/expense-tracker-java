package com.project.expenseTracker.dto.response;

import com.project.expenseTracker.model.Category;
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
public class CategoryResponse {
    private String categoryName;
    private String description;
    private List<CategoryResponse> subcategories = new ArrayList<>();

    public CategoryResponse(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}
