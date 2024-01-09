package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.ExpenseInfoReqData;
import com.project.expenseTracker.model.Expense;

import java.util.List;

public interface ExpenseService {
    String addExpense(ExpenseInfoReqData reqData, Long currentUserId);

    void deleteExpense(Long id, Long currentUserId);

    String updateExpense(Expense reqData, Long currentUserId);

    List<Expense> getExpenseList(Long currentUserId);

    List<Expense> getMonthlyExpenseList(Long currentUserId, String reqMonth, String reqYear);
}
