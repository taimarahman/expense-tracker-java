package com.project.expenseTracker.service;

import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.model.Income;

public interface IncomeService {
    String addMonthlyIncome(Long currentUserId, IncomeReqData reqData);

    String updateMonthlyIncome(Long currentUserId, Income reqData);

    IncomeResData getMonthlyDetails(Long currentUserId, String reqMonth, String reqYear);
}
