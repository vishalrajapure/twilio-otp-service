package com.vishalrajapure.twilio.service;

import com.twilio.rest.api.v2010.account.Message;
import com.vishalrajapure.twilio.config.TwilioConfig;
import com.vishalrajapure.twilio.dto.OtpStatus;
import com.vishalrajapure.twilio.dto.PasswordResetRequestDto;
import com.vishalrajapure.twilio.dto.PasswordResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOtpService {

    @Autowired
    private TwilioConfig twilioConfig;

    Map<String, String> otpMap = new HashMap<>();

    public Mono<PasswordResponseDto> sendOtpForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
        PasswordResponseDto passwordResponseDto = null;
        try {
            String otp = generateOtp();
            String otpMessage = "Dear Customer, Your OTP is : ##" + otp + "##";
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(passwordResetRequestDto.getPhoneNumber()),
                    new com.twilio.type.PhoneNumber(twilioConfig.getTrial_number()),
                    otpMessage)
                    .create();
            otpMap.put(passwordResetRequestDto.getUsername(), otp);
            passwordResponseDto = new PasswordResponseDto(otpMessage, OtpStatus.DELIVERED);
        } catch (Exception exception) {
            passwordResponseDto = new PasswordResponseDto("otp does not sent successfully", OtpStatus.FAILED);
        }
        return Mono.just(passwordResponseDto);
    }

    //generate 6 digit otp
    private String generateOtp() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(99999));
    }

    public Mono<String> validateOtp(PasswordResetRequestDto passwordResetRequestDto) {
        if (passwordResetRequestDto.getOneTimePassword().equals(otpMap.get(passwordResetRequestDto.getUsername()))) {
            return Mono.just("OTP verification successful");
        } else
            return Mono.error(new IllegalArgumentException("Invalid otp, please retry!!!"));
    }
}
