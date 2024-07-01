package com.iishanto.kikhabo.domain.usercase.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserLoginUseCase implements UseCase<Credentials, Credentials> {
    UserDataSource userDataSource;

    @Override
    public Credentials execute(Credentials credentials) throws Exception, GlobalServerException {
        return userDataSource.login(credentials);
    }
}
