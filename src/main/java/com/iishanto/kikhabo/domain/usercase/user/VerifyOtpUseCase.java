package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.user.EmailVerificationException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VerifyOtpUseCase {

    UserDataSource userDataSource;

    /**
     * @param email user's email
     * @param otp   the 6-digit code submitted by the user
     * @return true on success
     */
    public boolean execute(String email, String otp) throws EmailVerificationException {
        return userDataSource.verifyOtp(email, otp);
    }
}
