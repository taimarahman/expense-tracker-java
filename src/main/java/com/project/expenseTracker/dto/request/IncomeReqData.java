package com.project.expenseTracker.dto.request;

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

    private BigDecimal incomeAmount;

    private String incomeSource;

    private Integer month;

    private Integer year;
}
