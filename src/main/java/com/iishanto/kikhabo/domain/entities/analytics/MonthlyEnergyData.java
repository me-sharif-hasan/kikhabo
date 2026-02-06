package com.iishanto.kikhabo.domain.entities.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyEnergyData {
    private String month; // Format: "February" or "02"
    private Integer year; // Format: 2026
    private Float totalEnergy; // Sum of energy for the month
}
