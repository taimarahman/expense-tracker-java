package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeDetailsData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.model.Income;
import com.project.expenseTracker.repository.IncomeRepository;
import com.project.expenseTracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepo;
    @Override
    public String addMonthlyIncome(Long currentUserId, IncomeReqData reqData) {
        try {
            Income newIncome = new Income(reqData.getIncomeAmount(), reqData.getIncomeSource(), reqData.getMonth(), reqData.getYear(), currentUserId);

            incomeRepo.save(newIncome);

            return ResponseMessageConstants.SAVE_SUCCESS;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateMonthlyIncome(Long currentUserId, Income reqData) {
        try{
            Optional<Income> income = incomeRepo.findById(reqData.getIncomeId());

            if(income.isPresent()){
                Income updatedIncome = income.get();

                if(updatedIncome.getUserId().equals(currentUserId)){
                    incomeRepo.save(reqData);

                    return ResponseMessageConstants.UPDATE_SUCCESS;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public IncomeResData getMonthlyDetails(Long currentUserId, String month, String year) {
        try{
            Integer reqMonth = month.equals("null") ? LocalDate.now().getMonthValue() : Integer.parseInt(month);
            Integer reqYear = year.equals("null") ? LocalDate.now().getYear() : Integer.parseInt(year);

            List<Income> monthlyList = incomeRepo.findAllByUserIdAndMonthAndYear(currentUserId, reqMonth, reqYear);

            if(Objects.nonNull(monthlyList)){
                List<IncomeDetailsData> detailsList = monthlyList.stream().map(item -> IncomeDetailsData.builder()
                        .incomeSource(item.getIncomeSource())
                        .incomeAmount(item.getIncomeAmount())
                        .build())
                        .collect(Collectors.toList());

                BigDecimal totalIncome = monthlyList.stream().map(Income::getIncomeAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                return IncomeResData.builder()
                        .month(reqMonth)
                        .year(reqYear)
                        .totalIncome(totalIncome)
                        .details(detailsList)
                        .build();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IncomeResData> getIncomeDetails(Long currentUserId) {
        try{
            List<Map<String, Object>> rows = incomeRepo.getAllMonthlyIncomeSummaries(currentUserId);

            if(rows.size() > 0 ){
                List<IncomeResData> allMonthlySummery = rows.stream().map(
                        row -> IncomeResData.builder()
                                    .month((Integer) row.get("month"))
                                    .year((Integer) row.get("year"))
                                    .totalIncome((BigDecimal) row.get("totalIncome"))
                                    .build()
                        )
                        .collect(Collectors.toList());

                allMonthlySummery.forEach(summary -> {
                    List<Income> monthlyList = incomeRepo.findAllByUserIdAndMonthAndYear(
                            currentUserId, summary.getMonth(), summary.getYear());

                    if (Objects.nonNull(monthlyList) && monthlyList.size() > 0) {
                        List<IncomeDetailsData> detailsList = monthlyList.stream()
                                .map(item -> IncomeDetailsData.builder()
                                        .incomeSource(item.getIncomeSource())
                                        .incomeAmount(item.getIncomeAmount())
                                        .build())
                                .collect(Collectors.toList());

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
