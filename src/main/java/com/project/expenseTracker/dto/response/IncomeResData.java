package com.project.expenseTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResData {

    private Long incomeId;
    private Integer month;
    private Integer year;
    private BigDecimal amount;
    private String source;
    private List<IncomeDetailsData> details;

}
