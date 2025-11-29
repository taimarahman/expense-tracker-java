package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Income;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    @PostMapping(produces = "application/json")
    public ResponseEntity<Object> addMonthlyIncome(@Valid @RequestBody IncomeReqData reqData, HttpSession session) {

        String successMsg = incomeService.addMonthlyIncome(reqData, session);
        return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getMonthlyDetails(@RequestParam(name = "month", required = false) Integer month, @RequestParam(name = "year", required = false) Integer year, HttpSession session) {
        Long currentUserId = (Long) session.getAttribute("currentUserId");
        if (currentUserId == null) {
            return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
        }

        List<IncomeResData> resData = incomeService.getMonthlyDetails(currentUserId, month, year);
        return ResponseHandler.generateResponse(resData, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);

    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getIncomeDetails(@PathVariable Long incomeId, HttpSession session) {

        Long currentUserId = (Long) session.getAttribute("currentUserId");
        if (currentUserId == null) {
            return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
        }

        IncomeResData resData = incomeService.getIncomeDetails(currentUserId, incomeId);
        return ResponseHandler.generateResponse(resData, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_OVERAll_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getOverallDetails(HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if (Objects.nonNull(currentUserId)) {

                List<IncomeResData> resData = incomeService.getIncomeDetails(currentUserId);

                if (Objects.nonNull(resData) && resData.size() > 0) {
                    return ResponseHandler.generateResponse(resData, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
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
