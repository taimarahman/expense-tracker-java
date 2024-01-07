package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.ExpenseInfoRequest;
import com.project.expenseTracker.model.Expense;

import java.util.List;

public interface ExpenseService {
    String addExpense(ExpenseInfoRequest reqData, Long currentUserId);

    void deleteExpense(Long id, Long currentUserId);

    Expense updateExpense(Expense reqData, Long currentUserId);

    List<Expense> getExpenseList(Long currentUserId);

    List<Expense> getMonthlyExpenseList(Long currentUserId, Integer reqMonth, Integer reqYear);
}
