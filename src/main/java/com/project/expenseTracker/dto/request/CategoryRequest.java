package com.project.expenseTracker.dto.request;

import com.project.expenseTracker.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private String categoryName;
    private String description;
}
