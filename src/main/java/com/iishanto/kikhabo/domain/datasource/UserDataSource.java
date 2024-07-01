package com.iishanto.kikhabo.domain.datasource;

import ch.qos.logback.core.joran.sanity.Pair;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;

public interface UserDataSource {
    User register(User user) throws UserRegistrationFailureException;
    Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException;
}
