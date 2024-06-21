package com.iishanto.captionmaker.web.controller;

import com.iishanto.captionmaker.common.annotations.RestApiControllerWithApiEndpoint;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiControllerWithApiEndpoint
public class ImageController {
    @GetMapping
    public String index(){
        return "HELLO WORLD";
    }
}
