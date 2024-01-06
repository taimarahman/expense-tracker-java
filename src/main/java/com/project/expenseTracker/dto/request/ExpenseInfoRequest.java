package com.project.expenseTracker.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseInfoRequest {
    private double amount;
    private LocalDate date;
    private Long expenseCategory;
    private String shop;
    private String location;
    private String description;
}
