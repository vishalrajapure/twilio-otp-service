package com.vishalrajapure.twilio.resource;

import com.vishalrajapure.twilio.dto.PasswordResetRequestDto;
import com.vishalrajapure.twilio.service.TwilioOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOtpHandler {
    @Autowired
    private TwilioOtpService twilioOtpService;

    public Mono<ServerResponse> sendOtp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto -> twilioOtpService.sendOtpForPasswordReset(passwordResetRequestDto))
                .flatMap(passwordResponseDto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(passwordResponseDto)));
    }

    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto -> twilioOtpService.validateOtp(passwordResetRequestDto))
                .flatMap(passwordResponseDto -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(passwordResponseDto));
    }

}
