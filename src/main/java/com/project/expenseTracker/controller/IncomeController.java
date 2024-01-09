package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.IncomeResData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.model.Income;
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
    public ResponseEntity<Object> addMonthlyIncome(@RequestBody IncomeReqData reqData, HttpSession session) {
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

    @PostMapping(value = WebAPIUrlConstants.INCOME_UPDATE_API, produces = "application/json")
    public ResponseEntity<Object> updateMonthlyIncome(@RequestBody Income reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){
                reqData.setMonth(reqData.getMonth() == null ? LocalDate.now().getMonthValue() : reqData.getMonth());
                reqData.setYear(reqData.getYear() == null ?  LocalDate.now().getYear() : reqData.getYear());

                String successMsg = incomeService.updateMonthlyIncome(currentUserId, reqData);

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

    @GetMapping(value = WebAPIUrlConstants.INCOME_MONTHLY_DETAILS_API, produces = "application/json")
    public ResponseEntity<Object> getMonthlyDetails(@RequestParam(name="month", required = false) String month, @RequestParam(name="year", required = false) String year, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){

                IncomeResData resData = incomeService.getMonthlyDetails(currentUserId, month, year);

                if(Objects.nonNull(resData) ){
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
