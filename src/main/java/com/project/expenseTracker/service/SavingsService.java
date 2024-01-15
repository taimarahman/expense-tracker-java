package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.model.Savings;

public interface SavingsService {
    String addSavings(SavingsReqData reqData, Long currentUserId);

    Savings getMonthlySavings(Long currentUserId, String month, String year);
}
