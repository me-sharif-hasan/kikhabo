package com.iishanto.kikhabo.web.dto.config;

import com.iishanto.kikhabo.infrastructure.model.SystemConfigEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemConfigResponse {

    private Long id;
    private String configGroup;
    private String configValueMasked;   // last 6 chars only — never expose full key
    private int priority;
    private boolean enabled;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SystemConfigResponse from(SystemConfigEntity entity) {
        SystemConfigResponse r = new SystemConfigResponse();
        r.id = entity.getId();
        r.configGroup = entity.getConfigGroup();
        r.configValueMasked = mask(entity.getConfigValue());
        r.priority = entity.getPriority();
        r.enabled = entity.isEnabled();
        r.description = entity.getDescription();
        r.createdAt = entity.getCreatedAt();
        r.updatedAt = entity.getUpdatedAt();
        return r;
    }

    private static String mask(String value) {
        if (value == null || value.length() <= 6) return "******";
        return "******" + value.substring(value.length() - 6);
    }
}
