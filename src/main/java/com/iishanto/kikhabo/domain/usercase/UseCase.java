package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;

public interface UseCase <RT,AT>{
    RT execute(AT arguments) throws Exception, GlobalServerException;
}
