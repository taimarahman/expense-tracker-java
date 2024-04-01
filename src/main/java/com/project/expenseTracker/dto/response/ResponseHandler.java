package com.project.expenseTracker.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object resObj, String message, HttpStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", true);
        map.put("statusCode", status.value());
        map.put("message", message);
        map.put("data", resObj);
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", true);
        map.put("statusCode", status.value());
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", false);
        map.put("statusCode", status.value());
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}
