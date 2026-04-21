package com.iishanto.kikhabo.infrastructure.services.notification;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends a 6-digit OTP email to the given address for email verification.
     *
     * @param to  recipient email
     * @param otp 6-digit code
     */
    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Kikhabo – Your Verification Code");
        message.setText(
                "Hello,\n\n" +
                "Your Kikhabo verification code is:\n\n" +
                "    " + otp + "\n\n" +
                "This code is valid for 10 minutes. Please do not share it with anyone.\n\n" +
                "If you did not create a Kikhabo account, you can safely ignore this email.\n\n" +
                "– The Kikhabo Team"
        );
        mailSender.send(message);
    }
}
