package com.iishanto.captionmaker.domain.entities.text;

import com.iishanto.captionmaker.domain.entities.multimedia.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prompt {
    private String message;
    private List<Picture> pictures;
}
