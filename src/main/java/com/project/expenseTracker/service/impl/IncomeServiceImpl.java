package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeDetailsData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.model.Income;
import com.project.expenseTracker.repository.IncomeRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addMonthlyIncome(IncomeReqData reqData, HttpSession session) {

        Long currentUserId = (Long) session.getAttribute("currentUserId");

        if (currentUserId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        if (!userRepo.existsById(currentUserId)) {
            throw new ResourceNotFoundException("User not found.");
        }

        // update
        if (reqData.getIncomeId() != null) {
            Income income = incomeRepo.findById(reqData.getIncomeId()).orElseThrow(() -> new ResourceNotFoundException("Income not found"));

            if (!income.getMonth().equals(reqData.getMonth()) || !income.getYear().equals(reqData.getYear())) {
                throw new ForbiddenException("You can not change the month or year of an existing income.");
            }

            income.setAmount(reqData.getAmount());
            income.setSource(reqData.getSource());

            incomeRepo.save(income);
            return "Income updated successfully!";
        }

        // create
        Income newIncome = Income.builder().amount(reqData.getAmount()).source(reqData.getSource()).month(reqData.getMonth()).year(reqData.getYear()).userId(currentUserId).build();

        incomeRepo.save(newIncome);
        return "Income saved successfully!";
    }

    @Override
    public List<IncomeResData> getMonthlyDetails(Long currentUserId, Integer reqMonth, Integer reqYear) {
        List<Income> monthlyList = incomeRepo.findAllByUserIdAndMonthAndYear(currentUserId, reqMonth, reqYear);

        List<IncomeResData> detailsList = new ArrayList<>();

        if (!monthlyList.isEmpty()) {
            detailsList = monthlyList.stream().map(item -> IncomeResData.builder()
                    .incomeId(item.getIncomeId())
                    .month(item.getMonth())
                    .year(item.getYear())
                    .source(item.getSource())
                    .amount(item.getAmount())
                    .build()).toList();
        }

        return detailsList;
    }

    @Override
    public IncomeResData getIncomeDetails(Long currentUserId, Long incomeId) {

        Income income = incomeRepo.findById(incomeId).orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        if (!income.getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to view this income");
        }

        return IncomeResData.builder().incomeId(income.getIncomeId()).amount(income.getAmount()).source(income.getSource()).month(income.getMonth()).year(income.getYear()).build();

    }

    @Override
    public List<IncomeResData> getIncomeDetails(Long currentUserId) {
        try {
            List<Map<String, Object>> rows = incomeRepo.getAllMonthlyIncomeSummaries(currentUserId);

            if (rows.size() > 0) {
                List<IncomeResData> allMonthlySummery = rows.stream().map(row -> IncomeResData.builder().month((Integer) row.get("month")).year((Integer) row.get("year")).amount((BigDecimal) row.get("totalIncome")).build()).collect(Collectors.toList());

                allMonthlySummery.forEach(summary -> {
                    List<Income> monthlyList = incomeRepo.findAllByUserIdAndMonthAndYear(currentUserId, summary.getMonth(), summary.getYear());

                    if (Objects.nonNull(monthlyList) && monthlyList.size() > 0) {
                        List<IncomeDetailsData> detailsList = monthlyList.stream().map(item -> IncomeDetailsData.builder().incomeSource(item.getSource()).incomeAmount(item.getAmount()).build()).collect(Collectors.toList());

                        summary.setDetails(detailsList);
                    }
                });

                return allMonthlySummery;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
