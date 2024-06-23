package com.iishanto.captionmaker.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.captionmaker.common.annotations.RestApiControllerWithApiEndpoint;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import com.iishanto.captionmaker.domain.usercase.GetPictureTitleAndDescriptionWithTagUseCase;
import com.iishanto.captionmaker.domain.usercase.UseCase;
import com.iishanto.captionmaker.web.dto.PictureDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestApiControllerWithApiEndpoint
@AllArgsConstructor
@Validated
public class ImageController {
    GetPictureTitleAndDescriptionWithTagUseCase getPictureTitleAndDescriptionWithTagUseCase;
    Logger logger;
    @PostMapping
    public PromptResponse index(@Valid @RequestBody List<PictureDto> pictureDtoList) throws JsonProcessingException {
        logger.info("New Request at base api");
        return getPictureTitleAndDescriptionWithTagUseCase.execute(PictureDto.pictureDtoListToPictureDomainList(pictureDtoList));
    }
}
