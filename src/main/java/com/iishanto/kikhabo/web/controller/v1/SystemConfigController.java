package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.infrastructure.model.SystemConfigEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.SystemConfigRepository;
import com.iishanto.kikhabo.web.dto.config.SystemConfigRequest;
import com.iishanto.kikhabo.web.dto.config.SystemConfigResponse;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/system-config")
@Tag(name = "System Config (Admin)", description = "Manage dynamic configuration entries. Protected by X-Api-Key header.")
public class SystemConfigController {

    private final SystemConfigRepository configRepository;

    public SystemConfigController(SystemConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @GetMapping
    @Operation(summary = "List all configs in a group",
               description = "Pass ?group=gemini_text_key or any other group name. Returns all entries including disabled ones.")
    public ResponseEntity<SuccessResponse<List<SystemConfigResponse>>> list(
            @RequestParam String group) {

        List<SystemConfigResponse> items = configRepository
                .findByConfigGroupOrderByPriorityAsc(group)
                .stream()
                .map(SystemConfigResponse::from)
                .toList();

        return ResponseEntity.ok(new SuccessResponse<>("success", "Configs fetched", items));
    }

    @PostMapping
    @Operation(summary = "Add a new config entry",
               description = "Predefined groups: `gemini_text_key`, `gemini_vision_key`. Any group name is valid for future use.")
    public ResponseEntity<SuccessResponse<SystemConfigResponse>> add(
            @Valid @RequestBody SystemConfigRequest request) {

        if (configRepository.existsByConfigGroupAndConfigValue(request.getConfigGroup(), request.getConfigValue())) {
            return ResponseEntity.badRequest()
                    .body(new SuccessResponse<>("error", "This value already exists in group: " + request.getConfigGroup(), null));
        }

        SystemConfigEntity entity = SystemConfigEntity.builder()
                .configGroup(request.getConfigGroup())
                .configValue(request.getConfigValue())
                .priority(request.getPriority())
                .enabled(request.isEnabled())
                .description(request.getDescription())
                .build();

        SystemConfigEntity saved = configRepository.save(entity);
        return ResponseEntity.ok(new SuccessResponse<>("success", "Config added", SystemConfigResponse.from(saved)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update priority, enabled flag or description",
               description = "configGroup and configValue are immutable after creation. To change a value, delete and re-add.")
    public ResponseEntity<SuccessResponse<SystemConfigResponse>> update(
            @PathVariable Long id,
            @RequestBody SystemConfigRequest request) {

        return configRepository.findById(id).map(entity -> {
            entity.setPriority(request.getPriority());
            entity.setEnabled(request.isEnabled());
            entity.setDescription(request.getDescription());
            SystemConfigEntity saved = configRepository.save(entity);
            return ResponseEntity.ok(new SuccessResponse<>("success", "Config updated", SystemConfigResponse.from(saved)));
        }).orElse(ResponseEntity.status(404)
                .body(new SuccessResponse<>("error", "Config not found", null)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a config entry by ID")
    public ResponseEntity<SuccessResponse<Map<String, Long>>> delete(@PathVariable Long id) {
        if (!configRepository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(new SuccessResponse<>("error", "Config not found", null));
        }
        configRepository.deleteById(id);
        return ResponseEntity.ok(new SuccessResponse<>("success", "Config deleted", Map.of("id", id)));
    }
}
