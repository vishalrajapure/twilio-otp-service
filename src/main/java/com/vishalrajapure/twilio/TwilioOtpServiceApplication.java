package com.vishalrajapure.twilio;

import com.twilio.Twilio;
import com.vishalrajapure.twilio.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TwilioOtpServiceApplication {
    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccount_sid(), twilioConfig.getAuth_token());
    }

    public static void main(String[] args) {
        SpringApplication.run(TwilioOtpServiceApplication.class, args);
    }

}
