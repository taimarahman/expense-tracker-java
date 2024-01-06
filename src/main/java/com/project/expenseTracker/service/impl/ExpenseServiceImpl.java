package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.ExpenseInfoRequest;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.repository.ExpenseRepository;
import com.project.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepo;

    @Override
    public String addExpense(ExpenseInfoRequest reqData, Long currentUserId) {
        try {
            if(reqData.getAmount() > 0){
                Expense newExpense = new Expense(
                        reqData.getAmount(), reqData.getDate(), reqData.getExpenseCategory(), currentUserId,
                        reqData.getShop(), reqData.getLocation(), reqData.getDescription()
                );

                expenseRepo.save(newExpense);

                return ResponseMessageConstants.SAVE_SUCCESS;
            } else
                return "Amount must be greater than 0";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteExpense(Long id, Long currentUserId) {
        try {
            Optional<Expense> optionalExpense = expenseRepo.findById(id);

            if(optionalExpense.isPresent()){
                Expense expense = optionalExpense.get();
                if(expense.getUserId().equals(currentUserId)){
                    expenseRepo.delete(expense);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
