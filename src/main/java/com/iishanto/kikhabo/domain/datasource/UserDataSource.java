package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.User;

import java.util.List;

public interface UserDataSource {
    User register(User user) throws UserRegistrationFailureException;
    Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException;
    User getAuthenticatedUser();
    String getAuthUserEmail();
    User getUser(String email);

    User updateAuthenticatedUser(User user);
}
