package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.entity.Expense;

import java.util.List;

public interface ExpenseService {
    ApiResponse saveUpdateExpense(ExpenseDto reqData, Long currentUserId);

    ApiResponse deleteExpense(Long expenseId, Long currentUserId);

    List<Expense> getExpenseList(Long currentUserId);

    List<ExpenseDto> getMonthlyExpenseList(Long currentUserId, Integer reqMonth, Integer reqYear);

    ApiResponse getExpenseById(Long expenseId, Long currentUserId);
}
