package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface UseCase <RT,AT>{
    RT execute(AT arguments) throws JsonProcessingException;
}
