package com.iishanto.kikhabo.infrastructure.model;

import com.iishanto.kikhabo.domain.entities.multimedia.Picture;
import com.iishanto.kikhabo.domain.entities.text.Prompt;

import java.util.List;

public class AiPromptModel {
    private final Prompt prompt;
    public AiPromptModel(Prompt prompt){
        this.prompt=prompt;
    }

    public String getBardPrompt(){
        return """
                {
                    "contents": [{
                        "parts":[
                            { 
                                "text": "%s"
                            },
                            {
                                "inline_data": {
                                    "mime_type":"image/jpeg",
                                    "data": "%s"
                                }
                            }
                        ]
                    }]
                }
                """.formatted(prompt.getMessage(),prompt.getPictures().getFirst().getBase64String(),prompt.getMessage());
    }
}
