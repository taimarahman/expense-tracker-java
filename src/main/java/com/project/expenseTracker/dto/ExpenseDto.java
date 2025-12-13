package com.project.expenseTracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ExpenseDto(
        Long expenseId,

        @NotNull(message = "Amount is required.")
        @Min(value = 1, message = "Amount should be greater than 0.")
        BigDecimal amount,

        @NotNull(message = "Date is required.")
        LocalDate date,

        @NotNull(message = "Date is required.")
        LocalTime time,

        @NotNull(message = "Category can not be empty.")
        Long categoryId,

        String categoryName,

        String description
) {
}
