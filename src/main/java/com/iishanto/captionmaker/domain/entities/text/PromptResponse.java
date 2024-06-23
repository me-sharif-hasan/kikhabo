package com.iishanto.captionmaker.domain.entities.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromptResponse {
    private String status;
    private String error;
    private int count;
    private List<ImageData> images;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ImageData {
        String id;
        String title;
        String description;
        List<String> tags;
    }

}
