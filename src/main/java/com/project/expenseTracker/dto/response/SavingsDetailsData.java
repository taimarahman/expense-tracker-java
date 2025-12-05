package com.project.expenseTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsDetailsData {

    private Long savingsId;
    private String title;
    private Integer month;
    private Integer year;
    private BigDecimal amount;
}
