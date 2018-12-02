package com.sharma.data.resource;

import com.sharma.data.validator.annotation.Email;
import com.sharma.data.validator.annotation.PhoneNummer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    @NotEmpty
    private String vorName;
    @NotEmpty
    private String nachName;
    @Email
    private String email;
    @PhoneNummer
    private String telefonNummer;
    @PhoneNummer
    private String handyNummer;
}
