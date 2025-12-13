package com.project.expenseTracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record IncomeDto(

        Long incomeId,

        @NotNull(message = "Amount is required.")
        BigDecimal amount,

        @NotBlank(message = "Income source is required.")
        String source,

        @NotNull(message = "Month is required.")
        @Min(1) @Max(12)
        Integer month,

        @NotNull(message = "Year is required.")
        Integer year
) {}
