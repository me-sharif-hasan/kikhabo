package com.iishanto.kikhabo.web.dto.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SystemConfigRequest {

    @NotBlank(message = "configGroup is required")
    private String configGroup;

    @NotBlank(message = "configValue is required")
    private String configValue;

    @Min(value = 1, message = "priority must be >= 1")
    private int priority = 1;

    private boolean enabled = true;

    private String description;
}
