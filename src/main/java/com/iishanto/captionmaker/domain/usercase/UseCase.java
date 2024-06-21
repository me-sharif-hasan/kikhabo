package com.iishanto.captionmaker.domain.usercase;

public interface UseCase <AT,RT>{
    RT execute(AT arguments);
}
