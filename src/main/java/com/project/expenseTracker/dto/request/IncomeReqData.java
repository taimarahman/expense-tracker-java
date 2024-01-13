package com.project.expenseTracker.dto.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeReqData {

    @NotEmpty(message = "Amount can not be empty.")
    private BigDecimal incomeAmount;

    private String incomeSource;

    @NotEmpty(message = "Month can not be empty.")
    private Integer month;

    @NotEmpty(message = "Year can not be empty.")
    private Integer year;
}
