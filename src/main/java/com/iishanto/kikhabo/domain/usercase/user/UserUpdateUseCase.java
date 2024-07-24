package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserUpdateUseCase implements UseCase <User,User> {
    UserDataSource userDataSource;
    @Override
    public User execute(User user) throws Exception, GlobalServerException {
        return userDataSource.updateAuthenticatedUser(user);
    }
}
