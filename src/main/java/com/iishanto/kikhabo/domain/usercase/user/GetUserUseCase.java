package com.iishanto.kikhabo.domain.usercase.user;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserUseCase implements UseCase<User,Void> {
    UserDataSource userDataSource;
    @Override
    public User execute(Void arguments) throws Exception, GlobalServerException {
        return userDataSource.getAuthenticatedUser();
    }
}
