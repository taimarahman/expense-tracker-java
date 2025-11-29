package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.model.Income;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IncomeService {
    String addMonthlyIncome(IncomeReqData reqData, HttpSession session);

    String updateMonthlyIncome(Long currentUserId, Income reqData);

    IncomeResData getMonthlyDetails(Long currentUserId, String reqMonth, String reqYear);

    List<IncomeResData> getIncomeDetails(Long currentUserId);
}
