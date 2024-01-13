package com.project.expenseTracker.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsReqData {

    @NonNull
    private BigDecimal savingsAmount;

    @NonNull
    private Integer month;

    @NonNull
    private Integer year;
}
