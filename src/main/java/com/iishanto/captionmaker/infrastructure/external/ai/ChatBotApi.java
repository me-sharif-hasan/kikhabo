package com.iishanto.captionmaker.infrastructure.external.ai;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ChatBotApi {
    public String request(String prompt);
}
