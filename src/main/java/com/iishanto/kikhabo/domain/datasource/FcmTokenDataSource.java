package com.iishanto.kikhabo.domain.datasource;

import java.util.List;

public interface FcmTokenDataSource {

    void registerToken(Long userId, String token, String platform);

    void removeToken(Long userId, String token);

    List<String> getTokensForUser(Long userId);

    void removeInvalidToken(String token);

    List<Long> getAllUserIdsWithTokens();
}
