package com.iishanto.captionmaker.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import com.iishanto.captionmaker.domain.usercase.GetPictureTitleAndDescriptionWithTagUseCase;
import com.iishanto.captionmaker.web.dto.PictureDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api/v1/generate")
@AllArgsConstructor
@Validated
public class ImageController {
    GetPictureTitleAndDescriptionWithTagUseCase getPictureTitleAndDescriptionWithTagUseCase;
    Logger logger;
    @PostMapping
    public PromptResponse index(@Valid @RequestBody List<PictureDto> pictureDtoList) throws JsonProcessingException {
        return getPictureTitleAndDescriptionWithTagUseCase.execute(PictureDto.pictureDtoListToPictureDomainList(pictureDtoList));
    }
}
