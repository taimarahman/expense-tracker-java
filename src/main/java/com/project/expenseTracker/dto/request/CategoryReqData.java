package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryReqData {

    @NotEmpty(message = "Category name can not be empty.")
    private String categoryName;

    private String description;
}
