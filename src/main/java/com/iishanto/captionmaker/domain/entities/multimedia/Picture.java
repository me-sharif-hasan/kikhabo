package com.iishanto.captionmaker.domain.entities.multimedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picture {
    private String base64String;
    private String mimetype;
}
