package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.entity.Category;
import com.project.expenseTracker.entity.Expense;
import com.project.expenseTracker.entity.User;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.repository.CategoryRepository;
import com.project.expenseTracker.repository.ExpenseRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse saveUpdateExpense(ExpenseDto reqData, Long currentUserId) {
        if (!userRepository.existsById(currentUserId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        Category category = categoryRepository.findById(reqData.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));

        // update
        if (reqData.getExpenseId() != null) {
            Expense expense = expenseRepository.findById(reqData.getExpenseId()).orElseThrow(
                    () -> new ResourceNotFoundException("Expense not found")
            );

            if (!expense.getUser().getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not authorized to update this expense.");
            }

            expense.setAmount(reqData.getAmount());
            expense.setCategory(category);
            expense.setDate(reqData.getDate());
            expense.setTime(reqData.getTime());
            expense.setDescription(reqData.getDescription());
            expenseRepository.save(expense);

            return SuccessResponse.of("Expense updated successfully!");
        }
        User user = userRepository.findById(currentUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Expense expense = Expense.builder()
                .amount(reqData.getAmount())
                .category(category)
                .date(reqData.getDate())
                .time(reqData.getTime())
                .description(reqData.getDescription())
                .user(user)
                .build();

        expenseRepository.save(expense);
        return SuccessResponse.of("Expense saved successfully!", HttpStatus.CREATED);
    }

    @Override
    public ApiResponse deleteExpense(Long expenseId, Long currentUserId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new ResourceNotFoundException("Expense not found")
        );

        if (!expense.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to delete this expense.");
        }
        expenseRepository.delete(expense);

        return SuccessResponse.of("Expense deleted successfully!");
    }

    @Override
    public List<Expense> getExpenseList(Long currentUserId) {
        return expenseRepository.findAllByUserId(currentUserId);
    }

    @Override
    public List<ExpenseDto> getMonthlyExpenseList(Long currentUserId, Integer month, Integer year) {

        List<Expense> expenseList = (month != null && year != null)
                ? expenseRepository.findAllByUserIdAndMonth(currentUserId, month, year)
                : List.of();

        return expenseList.stream().map(Expense::toExpenseDto).toList();

    }

    @Override
    public ApiResponse getExpenseById(Long expenseId, Long currentUserId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new ResourceNotFoundException("Expense not found")
        );
        if (!expense.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to view this expense.");
        }

        return SuccessResponse.of(expense.toExpenseDto(), ResponseMessageConstants.DATA_FOUND);
    }
}
