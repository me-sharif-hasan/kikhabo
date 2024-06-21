package com.iishanto.captionmaker.domain.entities.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromptResponse {
    String title;
    String description;
    List<String> tags;
}
