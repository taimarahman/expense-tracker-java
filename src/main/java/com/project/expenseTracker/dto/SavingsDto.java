package com.project.expenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SavingsDto(
        Long savingsId,

        @NotNull(message = "Amount can not be empty.")
        BigDecimal amount,

        @NotBlank(message = "Amount can not be empty.")
        String title,

        @NotNull(message = "Month can not be empty.")
        Integer month,

        @NotNull(message = "Year can not be empty.")
        Integer year
) {}
