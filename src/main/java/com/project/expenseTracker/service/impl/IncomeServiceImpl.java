package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.IncomeDto;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.entity.Income;
import com.project.expenseTracker.entity.User;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.repository.IncomeRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse saveUpdateMonthlyIncome(com.project.expenseTracker.dto.IncomeDto reqData, HttpSession session) {

        Long currentUserId = (Long) session.getAttribute("currentUserId");

        if (currentUserId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }

        // update
        if (reqData.getIncomeId() != null) {
            Income income = incomeRepository.findById(reqData.getIncomeId()).orElseThrow(() ->
                    new ResourceNotFoundException("Income not found"));

            if (!income.getUser().getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not authorized to update this income.");
            }
            if (!income.getMonth().equals(reqData.getMonth()) ||
                    !income.getYear().equals(reqData.getYear())) {
                throw new ForbiddenException("Month or year cannot be changed.");
            }

            income.setAmount(reqData.getAmount());
            income.setSource(reqData.getSource());

            incomeRepository.save(income);
            return SuccessResponse.of("Income updated successfully!");
        }
        // create
        User currentUser = userRepository.findById(currentUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Income newIncome = Income.builder()
                .amount(reqData.getAmount())
                .source(reqData.getSource())
                .month(reqData.getMonth())
                .year(reqData.getYear())
                .user(currentUser)
                .build();

        incomeRepository.save(newIncome);
        return SuccessResponse.of("Income saved successfully!");
    }

    @Override
    public ApiResponse getMonthlyDetails(Long currentUserId, Integer reqMonth, Integer reqYear) {
        List<Income> monthlyList = (reqMonth != null && reqYear != null)
                ? incomeRepository.findAllByUserIdAndMonthAndYear(currentUserId, reqMonth, reqYear)
                : new ArrayList<>();

        List<IncomeDto> detailsList = new ArrayList<>();

        if (!monthlyList.isEmpty()) {
            detailsList = monthlyList.stream().map(Income::toIncomeDto).toList();
        }

        return SuccessResponse.of(detailsList, ResponseMessageConstants.DATA_FOUND);
    }

    @Override
    public ApiResponse getIncomeDetails(Long currentUserId, Long incomeId) {

        Income income = incomeRepository.findById(incomeId).orElseThrow(() ->
                new ResourceNotFoundException("Income not found"));

        if (!income.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to view this income");
        }

        return SuccessResponse.of(income.toIncomeDto(), ResponseMessageConstants.DATA_FOUND);

    }

    @Override
    public List<IncomeResData> getYearlyIncomeDetails(Long currentUserId, Integer year) {

        List<Map<String, Object>> rows = (year != null)
                ? incomeRepository.getYearlyIncome(currentUserId, year)
                : new ArrayList<>();

        if (rows.isEmpty()) {
            return List.of();
        }

        return rows.stream()
                .map(row -> {
                    IncomeResData summary = IncomeResData.builder()
                            .month((Integer) row.get("month"))
                            .year((Integer) row.get("year"))
                            .totalAmount((BigDecimal) row.get("totalIncome"))
                            .build();

                    List<Income> monthlyList = incomeRepository.findAllByUserIdAndMonthAndYear(currentUserId,
                            summary.getMonth(), summary.getYear());
                    if (!monthlyList.isEmpty()) {
                        summary.setDetails(monthlyList.stream()
                                .map(Income::toIncomeDto)
                                .toList());
                    }

                    return summary;
                })
                .toList();
    }

    @Override
    public ApiResponse deleteIncome(Long currentUserId, Long incomeId) {
        Income income = incomeRepository.findById(incomeId).orElseThrow(
                () -> new ResourceNotFoundException("Income not found")
        );
        if (!income.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to delete this income");
        }

        incomeRepository.delete(income);
        return SuccessResponse.of("Income deleted successfully");
    }
}
