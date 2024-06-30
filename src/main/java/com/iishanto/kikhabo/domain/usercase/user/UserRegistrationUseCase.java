package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserRegistrationUseCase implements UseCase<User, User> {
    UserDataSource userDataSource;
    @Override
    public User execute(User user) throws UserRegistrationFailureException {
        return userDataSource.register(user);
    }
}
