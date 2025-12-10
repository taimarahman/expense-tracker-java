package com.project.expenseTracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDto {

    private Long expenseId;

    @NotBlank(message = "Amount is required.")
    @Min(value = 1, message = "Amount should be greater than 0.")
    private BigDecimal amount;

    @NotBlank(message = "Date is required.")
    private LocalDate date;

    @NotBlank(message = "Date is required.")
    private LocalTime time;

    @NotBlank(message = "Category can not be empty.")
    private Long categoryId;

    private String categoryName;

    private String description;
}
