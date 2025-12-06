package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeDetailsData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.dto.response.ResponseBaseData;
import com.project.expenseTracker.dto.response.ResponseSuccessData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.model.Income;
import com.project.expenseTracker.repository.IncomeRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public String saveUpdateMonthlyIncome(IncomeReqData reqData, HttpSession session) {

        Long currentUserId = (Long) session.getAttribute("currentUserId");

        if (currentUserId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        if (!userRepo.existsById(currentUserId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        // update
        if (reqData.getIncomeId() != null) {
            Income income = incomeRepo.findById(reqData.getIncomeId()).orElseThrow(() ->
                    new ResourceNotFoundException("Income not found"));

            if (!income.getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not authorized to update this income.");
            }
            if (!income.getMonth().equals(reqData.getMonth()) ||
                    !income.getYear().equals(reqData.getYear())) {
                throw new ForbiddenException("Month or year cannot be changed.");
            }

            income.setAmount(reqData.getAmount());
            income.setSource(reqData.getSource());

            incomeRepo.save(income);
            return "Income updated successfully!";
        }

        // create
        Income newIncome = Income.builder()
                .amount(reqData.getAmount())
                .source(reqData.getSource())
                .month(reqData.getMonth())
                .year(reqData.getYear())
                .userId(currentUserId)
                .build();

        incomeRepo.save(newIncome);
        return "Income saved successfully!";
    }

    @Override
    public ResponseSuccessData getMonthlyDetails(Long currentUserId, Integer reqMonth, Integer reqYear) {
        List<Income> monthlyList = (reqMonth != null && reqYear != null)
                ? incomeRepo.findAllByUserIdAndMonthAndYear(currentUserId, reqMonth, reqYear)
                : new ArrayList<>();

        List<IncomeDetailsData> detailsList = new ArrayList<>();

        if (!monthlyList.isEmpty()) {
            detailsList = monthlyList.stream().map(Income::toIncomeDetailsData).toList();
        }

        return new ResponseSuccessData(detailsList, ResponseMessageConstants.DATA_FOUND, HttpStatus.FOUND);
    }

    @Override
    public IncomeDetailsData getIncomeDetails(Long currentUserId, Long incomeId) {

        Income income = incomeRepo.findById(incomeId).orElseThrow(() ->
                new ResourceNotFoundException("Income not found"));

        if (!income.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to view this income");
        }

        return income.toIncomeDetailsData();

    }

    @Override
    public List<IncomeResData> getYearlyIncomeDetails(Long currentUserId, Integer year) {

        List<Map<String, Object>> rows = (year != null)
                ? incomeRepo.getYearlyIncome(currentUserId, year)
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

                    List<Income> monthlyList = incomeRepo.findAllByUserIdAndMonthAndYear(currentUserId,
                            summary.getMonth(), summary.getYear());
                    if (!monthlyList.isEmpty()) {
                        summary.setDetails(monthlyList.stream()
                                .map(Income::toIncomeDetailsData)
                                .toList());
                    }

                    return summary;
                    })
                .toList();
    }

    @Override
    public ResponseBaseData deleteIncome(Long currentUserId, Long incomeId) {
        Income income = incomeRepo.findById(incomeId).orElseThrow(
                () -> new ResourceNotFoundException("Income not found")
        );
        if (!income.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to delete this income");
        }

        incomeRepo.delete(income);

        return new ResponseBaseData<>("Income deleted successfully", HttpStatus.OK);
    }
}
