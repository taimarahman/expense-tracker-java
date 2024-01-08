package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.IncomeRequest;
import com.project.expenseTracker.model.Income;
import com.project.expenseTracker.repository.IncomeRepository;
import com.project.expenseTracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepo;
    @Override
    public String addMonthlyIncome(Long currentUserId, IncomeRequest reqData) {
        try {
            Income newIncome = new Income(reqData.getIncomeAmount(), reqData.getIncomeSource(), reqData.getMonth(), reqData.getYear(), currentUserId);

            incomeRepo.save(newIncome);

            return ResponseMessageConstants.SAVE_SUCCESS;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
