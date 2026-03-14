package com.iishanto.kikhabo.infrastructure.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generic dynamic configuration store.
 *
 * Each row is one config entry belonging to a named group.
 * Examples:
 *   configGroup = "gemini_text_key"   → Gemini text API keys
 *   configGroup = "gemini_vision_key" → Gemini vision API keys
 *   configGroup = "s3_bucket"         → S3 config values
 *   configGroup = "feature_flag"      → boolean feature toggles
 *
 * Keys within the same group are tried in ascending priority order (1 = first).
 * configValue is stored as plain text — use description to label each entry.
 */
@Entity
@Table(
    name = "system_config",
    indexes = @Index(name = "idx_system_config_group", columnList = "configGroup")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Logical group name — groups related configs together (e.g. gemini_text_key). */
    @Column(nullable = false)
    private String configGroup;

    /** The actual config value (API key, URL, JSON blob, flag, etc.). */
    @Column(nullable = false, length = 2000)
    private String configValue;

    /** Lower value = higher priority. Tried first within the same group. */
    @Builder.Default
    private int priority = 1;

    /** Disabled entries are skipped entirely. */
    @Builder.Default
    private boolean enabled = true;

    /** Human-readable label (e.g. "Primary key", "Backup key - project B"). */
    private String description;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
