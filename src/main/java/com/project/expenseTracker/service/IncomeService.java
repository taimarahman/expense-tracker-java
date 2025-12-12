package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IncomeService {
    ApiResponse saveUpdateMonthlyIncome(IncomeReqData reqData, HttpSession session);

    ApiResponse getMonthlyDetails(Long currentUserId, Integer reqMonth, Integer reqYear);

    ApiResponse getIncomeDetails(Long currentUserId, Long incomeId);

    List<IncomeResData> getYearlyIncomeDetails(Long currentUserId, Integer year);

    ApiResponse deleteIncome(Long currentUserId, Long incomeId);
}
