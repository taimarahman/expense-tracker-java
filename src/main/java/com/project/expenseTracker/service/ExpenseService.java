package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.ExpenseInfoRequest;

public interface ExpenseService {
    String addExpense(ExpenseInfoRequest reqData, Long currentUserId);

    void deleteExpense(Long id, Long currentUserId);
}
