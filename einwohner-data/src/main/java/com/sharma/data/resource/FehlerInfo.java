package com.sharma.data.resource;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@Builder
public class FehlerInfo {
    private FehlerCode fehlerCode;
    private String fehlerText;
    private Date zeit;
}
