package com.project.expenseTracker.dto.response;

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
public class CategoryResData {
    private String categoryName;
    private String description;
    // only for response
    private List<CategoryResData> subcategories = new ArrayList<>();


}
