package com.project.expenseTracker.dto.request;

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
    private BigDecimal amount;
    private LocalDate date;
    private Long expenseCategory;
    private String shop;
    private String location;
    private String description;
}
