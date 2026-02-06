package com.iishanto.kikhabo.domain.entities.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyCostData {
    private String weekStart; // Format: "2026-02-01"
    private String weekEnd; // Format: "2026-02-07"
    private Float costPercentage; // Cost as percentage of budget
}
