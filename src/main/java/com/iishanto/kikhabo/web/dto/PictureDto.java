package com.iishanto.kikhabo.web.dto;

import com.iishanto.kikhabo.domain.entities.multimedia.Picture;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PictureDto {
    @NotNull
    private String base64String;
    private String mimeType;
    public static List<Picture> pictureDtoListToPictureDomainList(List<PictureDto> pictures){
        return pictures.stream().map(pictureDto -> new Picture(pictureDto.getBase64String(),pictureDto.getMimeType())).toList();
    }
}