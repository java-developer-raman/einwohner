package com.sharma.web.controller.advice;

import com.sharma.data.exception.DataNotFoundException;
import com.sharma.data.resource.FehlerCode;
import com.sharma.data.resource.FehlerInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class FehlerControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public FehlerInfo handleValidationExceptions(MethodArgumentNotValidException exception) {
        return FehlerInfo.builder().fehlerCode(FehlerCode.BAD_REQUEST).fehlerText(exception.getMessage()).zeit(new Date()).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public FehlerInfo handleValidationExceptions(Exception exception) {
        return FehlerInfo.builder().fehlerCode(FehlerCode.INTERNAL_SERVER_ERROR).fehlerText(exception.getMessage()).zeit(new Date()).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public FehlerInfo handleValidationExceptions(DataNotFoundException exception) {
        return FehlerInfo.builder().fehlerCode(FehlerCode.NOT_FOUND).fehlerText(exception.getReasonText()).zeit(new Date()).build();
    }

}
