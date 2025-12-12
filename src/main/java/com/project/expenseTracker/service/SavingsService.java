package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.dto.response.ApiResponse;

public interface SavingsService {
    ApiResponse saveUpdateSavings(SavingsReqData reqData, Long currentUserId);

    ApiResponse getSavingsDetails(Long currentUserId, Long savingsId);

    ApiResponse getSavingsDetails(Long currentUserId, Integer month, Integer year);

    ApiResponse deleteSavings(Long currentUserId, Long id);

    ApiResponse getYearlyDetails(Long currentUserId, Integer year);
}
