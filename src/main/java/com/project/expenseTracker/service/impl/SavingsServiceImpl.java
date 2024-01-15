package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.model.Savings;
import com.project.expenseTracker.repository.SavingsRepository;
import com.project.expenseTracker.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class SavingsServiceImpl implements SavingsService {

    @Autowired
    SavingsRepository savingsRepo;


    @Override
    public String addSavings(SavingsReqData reqData, Long currentUserId) {
        try {
            Savings newSavings = new Savings(reqData.getSavingsAmount(),reqData.getMonth(), reqData.getYear(), currentUserId);

            savingsRepo.save(newSavings);

            return ResponseMessageConstants.SAVE_SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Savings getMonthlySavings(Long currentUserId, String month, String year) {
        try {
            Integer reqMonth = month.equals("null") ? LocalDate.now().getMonthValue() : Integer.parseInt(month);
            Integer reqYear = year.equals("null") ? LocalDate.now().getYear() : Integer.parseInt(year);

            Savings savings = savingsRepo.findByUserIdAndMonthAndYear(currentUserId, reqMonth, reqYear);

            if (Objects.nonNull(savings)){
                return savings;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
