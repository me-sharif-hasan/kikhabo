package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.user.EmailVerificationException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResendOtpUseCase {

    UserDataSource userDataSource;

    /**
     * Resends the verification OTP to the given email, respecting the 2-minute
     * cooldown enforced in the datasource layer.
     *
     * @param email user's email address
     */
    public void execute(String email) throws EmailVerificationException {
        userDataSource.sendVerificationOtp(email);
    }
}
