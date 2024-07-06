package com.iishanto.kikhabo.domain.entities.multimedia;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picture extends SchemaEntity {
    private String base64String;
    private String mimetype;
}
