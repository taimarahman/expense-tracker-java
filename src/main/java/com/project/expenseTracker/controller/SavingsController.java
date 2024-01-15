package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Savings;
import com.project.expenseTracker.service.SavingsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(WebAPIUrlConstants.SAVINGS_API)
public class SavingsController {

    @Autowired
    SavingsService savingsService;

    @RequestMapping(value = WebAPIUrlConstants.SAVINGS_ADD_API, produces = "aaplication/json")
    public ResponseEntity<Object> addSavings(@RequestBody SavingsReqData reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");
            if (Objects.nonNull(currentUserId)) {
                    String successMsg = savingsService.addSavings(reqData, currentUserId);
                if (Objects.nonNull(successMsg) && !successMsg.isEmpty()) {
                    return ResponseHandler.generateResponse(successMsg, HttpStatus.CREATED);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);


        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }


    @RequestMapping(value = WebAPIUrlConstants.SAVINGS_MONTHLY_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getMonthlySavings(@RequestParam(name = "month", required = false, defaultValue = "-1") String month, @RequestParam(name = "year", required = false) String year, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");
            if (Objects.nonNull(currentUserId)) {
                Savings monthlySavings = savingsService.getMonthlySavings(currentUserId, month, year);
                if (Objects.nonNull(monthlySavings)) {
                    return ResponseHandler.generateResponse(monthlySavings, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
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
