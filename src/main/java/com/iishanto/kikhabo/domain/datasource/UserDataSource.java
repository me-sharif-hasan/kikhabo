package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.common.exception.user.UserLoginFailureException;
import com.iishanto.kikhabo.common.exception.user.UserRegistrationFailureException;
import com.iishanto.kikhabo.domain.entities.people.Credentials;
import com.iishanto.kikhabo.domain.entities.people.SocialAuthRequest;
import com.iishanto.kikhabo.domain.entities.people.User;

import java.util.List;

public interface UserDataSource {
    User register(User user) throws UserRegistrationFailureException;
    Credentials login(Credentials credentials) throws UserLoginFailureException, GlobalServerException;
    Credentials socialLogin(SocialAuthRequest request) throws Exception;
    User getAuthenticatedUser();
    String getAuthUserEmail();

    User updateAuthenticatedUser(User user);

    List<User> searchUser(String keyword);

    /** Returns the distinct country values for the given set of user IDs. */
    List<String> getDistinctCountriesForUsers(List<Long> userIds);

    /** Returns only those user IDs from {@code userIds} whose country matches {@code country}. */
    List<Long> getUserIdsByCountry(String country, List<Long> userIds);
}
