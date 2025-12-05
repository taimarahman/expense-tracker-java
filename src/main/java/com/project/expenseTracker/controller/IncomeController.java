package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeDetailsData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebAPIUrlConstants.INCOME_API)
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Object> saveUpdateIncome(@Valid @RequestBody IncomeReqData reqData, HttpSession session) {

        String successMsg = incomeService.saveUpdateMonthlyIncome(reqData, session);
        return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getMonthlyDetails(@RequestParam(name = "month", required = false) Integer month,
                                                    @RequestParam(name = "year", required = false) Integer year,
                                                    HttpSession session) {

        return ResponseEntity.ok(incomeService.getMonthlyDetails(getCurrentUserId(session), month, year));

    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getIncomeDetails(@PathVariable Long incomeId, HttpSession session) {

        IncomeDetailsData resData = incomeService.getIncomeDetails(getCurrentUserId(session), incomeId);
        return ResponseHandler.generateResponse(resData, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_YEARLY_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getYearlyDetails(@PathVariable Integer year, HttpSession session) {

        List<IncomeResData> resData = incomeService.getYearlyIncomeDetails(getCurrentUserId(session), year);
        return ResponseHandler.generateResponse(resData, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
    }

    @DeleteMapping(value = WebAPIUrlConstants.INCOME_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> deleteIncome(@PathVariable Long incomeId, HttpSession session) {

        return ResponseEntity.ok(incomeService.deleteIncome(getCurrentUserId(session), incomeId));
    }
}
