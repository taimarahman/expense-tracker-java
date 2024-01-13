package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.SavingsReqData;

public interface SavingsService {
    String addSavings(SavingsReqData reqData, Long currentUserId);
}
