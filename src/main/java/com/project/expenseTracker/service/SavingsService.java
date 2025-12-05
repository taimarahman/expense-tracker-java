package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.dto.response.ResponseBaseData;

public interface SavingsService {
    ResponseBaseData saveUpdateSavings(SavingsReqData reqData, Long currentUserId);

    ResponseBaseData getSavingsDetails(Long currentUserId, Long savingsId);

    ResponseBaseData getSavingsDetails(Long currentUserId, Integer month, Integer year);
}
