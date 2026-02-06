package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.analytics.*;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsDataSource {
    // Energy APIs - Default periods
    List<DailyEnergyData> getDailyEnergyConsumption(int days);

    List<WeeklyEnergyData> getWeeklyEnergyConsumption(int weeks);

    List<MonthlyEnergyData> getMonthlyEnergyConsumption(int months);

    // Energy APIs - Custom date ranges
    List<DailyEnergyData> getDailyEnergyConsumption(LocalDate startDate, LocalDate endDate);

    List<WeeklyEnergyData> getWeeklyEnergyConsumption(LocalDate startDate, LocalDate endDate);

    List<MonthlyEnergyData> getMonthlyEnergyConsumption(LocalDate startDate, LocalDate endDate);

    // Cost APIs - Default periods
    List<DailyCostData> getDailyCost(int days);

    List<WeeklyCostData> getWeeklyCost(int weeks);

    List<MonthlyCostData> getMonthlyCost(int months);

    // Cost APIs - Custom date ranges
    List<DailyCostData> getDailyCost(LocalDate startDate, LocalDate endDate);

    List<WeeklyCostData> getWeeklyCost(LocalDate startDate, LocalDate endDate);

    List<MonthlyCostData> getMonthlyCost(LocalDate startDate, LocalDate endDate);
}
