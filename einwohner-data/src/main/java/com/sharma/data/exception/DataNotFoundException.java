package com.sharma.data.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataNotFoundException extends RuntimeException {
    private String reasonText;
}
