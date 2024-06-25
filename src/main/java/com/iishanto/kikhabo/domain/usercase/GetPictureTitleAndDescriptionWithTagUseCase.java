package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.common.exception.NoPictureProvidedException;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.multimedia.Picture;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GetPictureTitleAndDescriptionWithTagUseCase implements UseCase <PicturePromptResponse, List<Picture>> {
    ChatBotDataSource chatBotDataSource;
    @Override
    public PicturePromptResponse execute(@NonNull List<Picture> pictures) throws JsonProcessingException {
        if(pictures.isEmpty()) throw new NoPictureProvidedException();
        Prompt prompt=new Prompt();
        prompt.setPictures(pictures);
        setPromptMessage(prompt);
        return chatBotDataSource.prompt(prompt);
    }

    private void setPromptMessage(Prompt prompt){
        prompt.setMessage(
                """
                    System Seed: %d
                    Suppose you are a api server. you are provided %d images. You have to give each of them
                    * a <title> with max 20 words non empty,
                    * a <description> with max 100 words non empty,
                    * some <tags> which is a list with max size 50. each tag is maximum 3 words.
                    
                    As a api server, you must output json. You are not allowed to say any extra text other than json. The suitable json format
                    is
                    {
                         status: <success>|<error>,
                         count: <number of images you processed>,
                         error: <message when you produced an error>,
                         images: [
                            {
                                id: <serial of the image>,
                                title: <title for the image>,
                                description: <description for the image>,
                                tags: [
                                    <tag 1>,
                                    <tag 2>,
                                    ......,
                                    <tag n>
                                ]
                            },
                            ....
                         ]
                    }
                    
                    keep in mind that keys and values should be double quoted.
                    """.formatted(System.currentTimeMillis(),prompt.getPictures().size()));
    }
}
