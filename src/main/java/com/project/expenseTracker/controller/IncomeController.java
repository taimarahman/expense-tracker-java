package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.IncomeRequest;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(WebAPIUrlConstants.INCOME_API)
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping(value = WebAPIUrlConstants.INCOME_ADD_API, produces = "application/json")
    public ResponseEntity<Object> addMonthlyIncome(@RequestBody IncomeRequest reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){
                reqData.setMonth(reqData.getMonth() == null ? LocalDate.now().getMonthValue() : reqData.getMonth());
                reqData.setYear(reqData.getYear() == null ?  LocalDate.now().getYear() : reqData.getYear());

                String successMsg = incomeService.addMonthlyIncome(currentUserId, reqData);

                if(Objects.nonNull(successMsg) && !successMsg.isEmpty()){
                    return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }
}
