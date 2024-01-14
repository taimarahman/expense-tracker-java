package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseInfoReqData {

    @NotEmpty(message = "Amount can not be empty.")
    private BigDecimal amount;

    @NotEmpty(message = "Date can not be empty.")
    private LocalDate date;

    @NotEmpty(message = "Category can not be empty.")
    private Long expenseCategory;

    private String shop;

    private String location;

    private String description;
}
