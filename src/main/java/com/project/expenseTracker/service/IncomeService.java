package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeDetailsData;
import com.project.expenseTracker.dto.response.IncomeResData;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IncomeService {
    String saveUpdateMonthlyIncome(IncomeReqData reqData, HttpSession session);

    List<IncomeDetailsData> getMonthlyDetails(Long currentUserId, Integer reqMonth, Integer reqYear);

    IncomeDetailsData getIncomeDetails(Long currentUserId, Long incomeId);

    List<IncomeResData> getYearlyIncomeDetails(Long currentUserId, Integer year);
}
