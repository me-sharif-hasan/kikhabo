package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import com.iishanto.kikhabo.domain.entities.multimedia.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prompt extends SchemaEntity {
    private String message;
    private List<Picture> pictures;
}
