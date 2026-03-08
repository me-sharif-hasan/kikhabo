package com.iishanto.kikhabo.domain.usercase.notification;

import com.iishanto.kikhabo.domain.datasource.FcmTokenDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RemoveFcmTokenUseCase implements UseCase<Void, String> {

    UserDataSource userDataSource;
    FcmTokenDataSource fcmTokenDataSource;

    @Override
    public Void execute(String token) throws Exception {
        User user = userDataSource.getAuthenticatedUser();
        fcmTokenDataSource.removeToken(user.getId(), token);
        return null;
    }
}
