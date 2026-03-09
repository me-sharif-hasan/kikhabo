package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.SocialAuthRequest;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SocialLoginUseCase implements UseCase<Credentials, SocialAuthRequest> {
    UserDataSource userDataSource;

    @Override
    public Credentials execute(SocialAuthRequest request) throws Exception {
        return userDataSource.socialLogin(request);
    }
}
