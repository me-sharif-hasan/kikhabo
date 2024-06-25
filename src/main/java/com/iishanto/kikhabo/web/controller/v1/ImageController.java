package com.iishanto.kikhabo.web.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;
import com.iishanto.kikhabo.domain.usercase.GetPictureTitleAndDescriptionWithTagUseCase;
import com.iishanto.kikhabo.web.dto.PictureDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/generate")
@AllArgsConstructor
@Validated
public class ImageController {
    GetPictureTitleAndDescriptionWithTagUseCase getPictureTitleAndDescriptionWithTagUseCase;
    Logger logger;
    @PostMapping
    public PicturePromptResponse index(@Valid @RequestBody List<PictureDto> pictureDtoList) throws JsonProcessingException {
        return getPictureTitleAndDescriptionWithTagUseCase.execute(PictureDto.pictureDtoListToPictureDomainList(pictureDtoList));
    }
}
