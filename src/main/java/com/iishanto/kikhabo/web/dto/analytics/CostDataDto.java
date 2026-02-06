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
public class CostDataDto {
    private String period; // Date, week range, or month
    private Float costPercentage; // Cost as percentage of budget

    // Factory methods for conversion from domain entities
    public static CostDataDto fromDaily(DailyCostData data) {
        return CostDataDto.builder()
                .period(data.getDate())
                .costPercentage(data.getCostPercentage())
                .build();
    }

    public static CostDataDto fromWeekly(WeeklyCostData data) {
        return CostDataDto.builder()
                .period(data.getWeekStart() + " to " + data.getWeekEnd())
                .costPercentage(data.getCostPercentage())
                .build();
    }

    public static CostDataDto fromMonthly(MonthlyCostData data) {
        return CostDataDto.builder()
                .period(data.getYear() + "-" + data.getMonth())
                .costPercentage(data.getCostPercentage())
                .build();
    }

    public static List<CostDataDto> fromDailyList(List<DailyCostData> dataList) {
        return dataList.stream().map(CostDataDto::fromDaily).collect(Collectors.toList());
    }

    public static List<CostDataDto> fromWeeklyList(List<WeeklyCostData> dataList) {
        return dataList.stream().map(CostDataDto::fromWeekly).collect(Collectors.toList());
    }

    public static List<CostDataDto> fromMonthlyList(List<MonthlyCostData> dataList) {
        return dataList.stream().map(CostDataDto::fromMonthly).collect(Collectors.toList());
    }
}
