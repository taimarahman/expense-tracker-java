package com.project.expenseTracker.dto.response;

import com.project.expenseTracker.dto.IncomeDto;
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

    private Integer month;
    private Integer year;
    private BigDecimal totalAmount;
    private List<IncomeDto> details;

}
