package com.vishalrajapure.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDto {
    private String phoneNumber;
    private String username;
    private String oneTimePassword;
}
