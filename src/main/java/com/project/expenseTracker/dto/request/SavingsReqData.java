package com.project.expenseTracker.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsReqData {

    @NotEmpty(message = "Amount can not be empty.")
    private BigDecimal savingsAmount;

    @NotEmpty(message = "Month can not be empty.")
    private Integer month;

    @NotEmpty(message = "Year can not be empty.")
    private Integer year;
}
