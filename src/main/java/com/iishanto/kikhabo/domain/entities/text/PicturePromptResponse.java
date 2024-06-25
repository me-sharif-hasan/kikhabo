package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class PicturePromptResponse extends SchemaEntity {
    private String status;
    private String error;
    private int count;
    private List<ImageData> images;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ImageData extends SchemaEntity {
        String id;
        String title;
        String description;
        List<String> tags;
    }

}
