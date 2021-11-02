package com.vishalrajapure.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResponseDto {
    private String message;
    private OtpStatus status;
}
