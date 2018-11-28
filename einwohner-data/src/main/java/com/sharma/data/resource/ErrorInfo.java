package com.sharma.data.resource;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@Builder
public class ErrorInfo {
    private ErrorCode errorCode;
    private String errorMessage;
    private Date timeStamp;
}
