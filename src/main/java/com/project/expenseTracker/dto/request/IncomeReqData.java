package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeReqData {

    private Long incomeId;

    @NotNull(message = "Amount is required.")
    private BigDecimal amount;

    @NotBlank(message = "Income source is required.")
    private String source;

    @NotNull(message = "Month is required.")
    @Min(1) @Max(12)
    private Integer month;

    @NotNull(message = "Year is required.")
    private Integer year;
}
