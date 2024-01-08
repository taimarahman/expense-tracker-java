package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.IncomeRequest;

public interface IncomeService {
    String addMonthlyIncome(Long currentUserId, IncomeRequest reqData);
}
