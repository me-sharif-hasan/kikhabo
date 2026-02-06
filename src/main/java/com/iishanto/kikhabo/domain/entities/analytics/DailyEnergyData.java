package com.iishanto.kikhabo.domain.entities.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyEnergyData {
    private String date; // Format: "2026-02-01"
    private Float totalEnergy; // Sum of energy for the day
}
