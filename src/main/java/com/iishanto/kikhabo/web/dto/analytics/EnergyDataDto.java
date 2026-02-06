package com.iishanto.kikhabo.web.dto.analytics;

import com.iishanto.kikhabo.domain.entities.analytics.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDataDto {
    private String period; // Date, week range, or month
    private Float totalEnergy; // Sum of energy

    // Factory methods for conversion from domain entities
    public static EnergyDataDto fromDaily(DailyEnergyData data) {
        return EnergyDataDto.builder()
                .period(data.getDate())
                .totalEnergy(data.getTotalEnergy())
                .build();
    }

    public static EnergyDataDto fromWeekly(WeeklyEnergyData data) {
        return EnergyDataDto.builder()
                .period(data.getWeekStart() + " to " + data.getWeekEnd())
                .totalEnergy(data.getTotalEnergy())
                .build();
    }

    public static EnergyDataDto fromMonthly(MonthlyEnergyData data) {
        return EnergyDataDto.builder()
                .period(data.getYear() + "-" + data.getMonth())
                .totalEnergy(data.getTotalEnergy())
                .build();
    }

    public static List<EnergyDataDto> fromDailyList(List<DailyEnergyData> dataList) {
        return dataList.stream().map(EnergyDataDto::fromDaily).collect(Collectors.toList());
    }

    public static List<EnergyDataDto> fromWeeklyList(List<WeeklyEnergyData> dataList) {
        return dataList.stream().map(EnergyDataDto::fromWeekly).collect(Collectors.toList());
    }

    public static List<EnergyDataDto> fromMonthlyList(List<MonthlyEnergyData> dataList) {
        return dataList.stream().map(EnergyDataDto::fromMonthly).collect(Collectors.toList());
    }
}
