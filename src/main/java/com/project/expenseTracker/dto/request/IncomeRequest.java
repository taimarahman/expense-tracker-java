package com.project.expenseTracker.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeRequest {

    private BigDecimal incomeAmount;

    private String incomeSource;

    private Integer month;

    private Integer year;
}
