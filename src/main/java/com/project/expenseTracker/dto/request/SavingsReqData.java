package com.project.expenseTracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsReqData {

    private Long savingsId;

    @NotNull(message = "Amount can not be empty.")
    private BigDecimal amount;

    @NotBlank(message = "Amount can not be empty.")
    private String title;

    @NotNull(message = "Month can not be empty.")
    private Integer month;

    @NotNull(message = "Year can not be empty.")
    private Integer year;
}
