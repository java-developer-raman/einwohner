package com.sharma.core.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String vorName;
    private String nachName;
    private String email;
    private String telefonNummer;
    private String handyNummer;
}
