package com.iishanto.kikhabo.domain.entities.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyCostData {
    private String date; // Format: "2026-02-01"
    private Float costPercentage; // Cost as percentage of budget
}
