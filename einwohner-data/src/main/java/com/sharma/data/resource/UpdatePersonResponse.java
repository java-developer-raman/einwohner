package com.sharma.data.resource;

import com.sharma.data.validator.annotation.Email;
import com.sharma.data.validator.annotation.PhoneNummer;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonResponse {

    @NotEmpty
    private UUID id;
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
