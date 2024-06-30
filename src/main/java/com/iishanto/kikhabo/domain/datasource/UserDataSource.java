package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.entities.people.User;

public interface UserDataSource {
    User register(User user) throws UserRegistrationFailureException;
}
