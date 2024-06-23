package com.iishanto.captionmaker.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.captionmaker.common.exception.NoPictureProvidedException;
import com.iishanto.captionmaker.domain.datasource.ChatBotDataSource;
import com.iishanto.captionmaker.domain.entities.multimedia.Picture;
import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetPictureTitleAndDescriptionWithTagUseCase implements UseCase <PromptResponse, List<Picture>> {
    ChatBotDataSource chatBotDataSource;

    @Override
    public PromptResponse execute(@NonNull List<Picture> pictures) throws JsonProcessingException {
        if(pictures.isEmpty()) throw new NoPictureProvidedException();
        Prompt prompt=new Prompt();
        prompt.setPictures(pictures);
        return chatBotDataSource.prompt(prompt);
    }
}
