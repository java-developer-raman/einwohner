package com.sharma.web.controller.advice;

import com.sharma.data.resource.FehlerCode;
import com.sharma.data.resource.FehlerInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FehlerControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public FehlerInfo handleValidationExceptions(MethodArgumentNotValidException exception) {
        return FehlerInfo.builder().fehlerCode(FehlerCode.BAD_REQUEST).fehlerText(exception.getMessage()).build();
    }
}
