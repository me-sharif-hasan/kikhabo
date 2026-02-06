package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.*;
import com.iishanto.kikhabo.infrastructure.repositories.database.MealHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AnalyticsDataSourceImpl implements AnalyticsDataSource {
        MealHistoryRepository mealHistoryRepository;
        UserDataSource userDataSource;

        // ===== Energy APIs - Default Periods =====

        @Override
        public List<DailyEnergyData> getDailyEnergyConsumption(int days) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusDays(days - 1);
                return getDailyEnergyConsumption(startDate, endDate);
        }

        @Override
        public List<WeeklyEnergyData> getWeeklyEnergyConsumption(int weeks) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusWeeks(weeks);
                return getWeeklyEnergyConsumption(startDate, endDate);
        }

        @Override
        public List<MonthlyEnergyData> getMonthlyEnergyConsumption(int months) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusMonths(months);
                return getMonthlyEnergyConsumption(startDate, endDate);
        }

        // ===== Energy APIs - Custom Date Ranges =====

        @Override
        public List<DailyEnergyData> getDailyEnergyConsumption(LocalDate startDate, LocalDate endDate) {
                Long userId = userDataSource.getAuthenticatedUser().getId();
                Long startTimestamp = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                Long endTimestamp = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli();

                List<Map<String, Object>> results = mealHistoryRepository.getDailyEnergyStats(userId, startTimestamp,
                                endTimestamp);

                return results.stream()
                                .map(row -> {
                                        Object energyObj = row.get("totalEnergy");
                                        Float totalEnergy = energyObj instanceof Number
                                                        ? ((Number) energyObj).floatValue()
                                                        : 0.0f;
                                        return DailyEnergyData.builder()
                                                        .date(row.get("date").toString())
                                                        .totalEnergy(totalEnergy)
                                                        .build();
                                })
                                .collect(Collectors.toList());
        }

        @Override
        public List<WeeklyEnergyData> getWeeklyEnergyConsumption(LocalDate startDate, LocalDate endDate) {
                List<DailyEnergyData> dailyData = getDailyEnergyConsumption(startDate, endDate);
                return aggregateToWeekly(dailyData);
        }

        @Override
        public List<MonthlyEnergyData> getMonthlyEnergyConsumption(LocalDate startDate, LocalDate endDate) {
                List<DailyEnergyData> dailyData = getDailyEnergyConsumption(startDate, endDate);
                return aggregateToMonthly(dailyData);
        }

        // ===== Cost APIs - Default Periods =====

        @Override
        public List<DailyCostData> getDailyCost(int days) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusDays(days - 1);
                return getDailyCost(startDate, endDate);
        }

        @Override
        public List<WeeklyCostData> getWeeklyCost(int weeks) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusWeeks(weeks);
                return getWeeklyCost(startDate, endDate);
        }

        @Override
        public List<MonthlyCostData> getMonthlyCost(int months) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusMonths(months);
                return getMonthlyCost(startDate, endDate);
        }

        // ===== Cost APIs - Custom Date Ranges =====

        @Override
        public List<DailyCostData> getDailyCost(LocalDate startDate, LocalDate endDate) {
                Long userId = userDataSource.getAuthenticatedUser().getId();
                Long startTimestamp = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                Long endTimestamp = endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli();

                List<Map<String, Object>> results = mealHistoryRepository.getDailyCostStats(userId, startTimestamp,
                                endTimestamp);

                return results.stream()
                                .map(row -> {
                                        String date = row.get("date").toString();
                                        Object costObj = row.get("totalCost");
                                        Float totalCost = costObj instanceof Number ? ((Number) costObj).floatValue()
                                                        : 0.0f;
                                        Float costPercentage = (totalCost / 10.0f) * 100.0f; // Budget is 10 per day

                                        return DailyCostData.builder()
                                                        .date(date)
                                                        .costPercentage(costPercentage)
                                                        .build();
                                })
                                .collect(Collectors.toList());
        }

        @Override
        public List<WeeklyCostData> getWeeklyCost(LocalDate startDate, LocalDate endDate) {
                List<DailyCostData> dailyData = getDailyCost(startDate, endDate);
                return aggregateCostToWeekly(dailyData, startDate, endDate);
        }

        @Override
        public List<MonthlyCostData> getMonthlyCost(LocalDate startDate, LocalDate endDate) {
                List<DailyCostData> dailyData = getDailyCost(startDate, endDate);
                return aggregateCostToMonthly(dailyData, startDate, endDate);
        }

        // ===== Helper Methods =====

        private List<WeeklyEnergyData> aggregateToWeekly(List<DailyEnergyData> dailyData) {
                Map<String, List<DailyEnergyData>> weeklyGroups = dailyData.stream()
                                .collect(Collectors.groupingBy(data -> {
                                        LocalDate date = LocalDate.parse(data.getDate());
                                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                        int weekOfYear = date.get(weekFields.weekOfWeekBasedYear());
                                        int year = date.get(weekFields.weekBasedYear());
                                        return year + "-W" + weekOfYear;
                                }));

                return weeklyGroups.entrySet().stream()
                                .map(entry -> {
                                        List<DailyEnergyData> weekData = entry.getValue();
                                        LocalDate weekStart = weekData.stream()
                                                        .map(d -> LocalDate.parse(d.getDate()))
                                                        .min(LocalDate::compareTo)
                                                        .orElse(LocalDate.now());
                                        LocalDate weekEnd = weekData.stream()
                                                        .map(d -> LocalDate.parse(d.getDate()))
                                                        .max(LocalDate::compareTo)
                                                        .orElse(LocalDate.now());
                                        Float totalEnergy = weekData.stream()
                                                        .map(DailyEnergyData::getTotalEnergy)
                                                        .reduce(0.0f, Float::sum);

                                        return WeeklyEnergyData.builder()
                                                        .weekStart(weekStart.toString())
                                                        .weekEnd(weekEnd.toString())
                                                        .totalEnergy(totalEnergy)
                                                        .build();
                                })
                                .sorted(Comparator.comparing(WeeklyEnergyData::getWeekStart))
                                .collect(Collectors.toList());
        }

        private List<MonthlyEnergyData> aggregateToMonthly(List<DailyEnergyData> dailyData) {
                Map<String, List<DailyEnergyData>> monthlyGroups = dailyData.stream()
                                .collect(Collectors.groupingBy(data -> {
                                        LocalDate date = LocalDate.parse(data.getDate());
                                        return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                                }));

                return monthlyGroups.entrySet().stream()
                                .map(entry -> {
                                        String[] parts = entry.getKey().split("-");
                                        Integer year = Integer.parseInt(parts[0]);
                                        String month = parts[1];
                                        Float totalEnergy = entry.getValue().stream()
                                                        .map(DailyEnergyData::getTotalEnergy)
                                                        .reduce(0.0f, Float::sum);

                                        return MonthlyEnergyData.builder()
                                                        .month(month)
                                                        .year(year)
                                                        .totalEnergy(totalEnergy)
                                                        .build();
                                })
                                .sorted(Comparator.comparing((MonthlyEnergyData d) -> d.getYear())
                                                .thenComparing(MonthlyEnergyData::getMonth))
                                .collect(Collectors.toList());
        }

        private List<WeeklyCostData> aggregateCostToWeekly(List<DailyCostData> dailyData, LocalDate startDate,
                        LocalDate endDate) {
                long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

                Map<String, List<DailyCostData>> weeklyGroups = dailyData.stream()
                                .collect(Collectors.groupingBy(data -> {
                                        LocalDate date = LocalDate.parse(data.getDate());
                                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                        int weekOfYear = date.get(weekFields.weekOfWeekBasedYear());
                                        int year = date.get(weekFields.weekBasedYear());
                                        return year + "-W" + weekOfYear;
                                }));

                return weeklyGroups.entrySet().stream()
                                .map(entry -> {
                                        List<DailyCostData> weekData = entry.getValue();
                                        LocalDate weekStart = weekData.stream()
                                                        .map(d -> LocalDate.parse(d.getDate()))
                                                        .min(LocalDate::compareTo)
                                                        .orElse(LocalDate.now());
                                        LocalDate weekEnd = weekData.stream()
                                                        .map(d -> LocalDate.parse(d.getDate()))
                                                        .max(LocalDate::compareTo)
                                                        .orElse(LocalDate.now());

                                        // Recalculate percentage based on week days
                                        long weekDays = ChronoUnit.DAYS.between(weekStart, weekEnd) + 1;
                                        Float totalCost = weekData.stream()
                                                        .map(d -> d.getCostPercentage() * 10.0f / 100.0f) // Convert
                                                                                                          // back to raw
                                                                                                          // cost
                                                        .reduce(0.0f, Float::sum);
                                        Float costPercentage = (totalCost / (10.0f * weekDays)) * 100.0f;

                                        return WeeklyCostData.builder()
                                                        .weekStart(weekStart.toString())
                                                        .weekEnd(weekEnd.toString())
                                                        .costPercentage(costPercentage)
                                                        .build();
                                })
                                .sorted(Comparator.comparing(WeeklyCostData::getWeekStart))
                                .collect(Collectors.toList());
        }

        private List<MonthlyCostData> aggregateCostToMonthly(List<DailyCostData> dailyData, LocalDate startDate,
                        LocalDate endDate) {
                Map<String, List<DailyCostData>> monthlyGroups = dailyData.stream()
                                .collect(Collectors.groupingBy(data -> {
                                        LocalDate date = LocalDate.parse(data.getDate());
                                        return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                                }));

                return monthlyGroups.entrySet().stream()
                                .map(entry -> {
                                        String[] parts = entry.getKey().split("-");
                                        Integer year = Integer.parseInt(parts[0]);
                                        String month = parts[1];

                                        // Calculate days in this month within the range
                                        LocalDate monthStart = LocalDate.of(year, Integer.parseInt(month), 1);
                                        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);
                                        LocalDate effectiveStart = monthStart.isBefore(startDate) ? startDate
                                                        : monthStart;
                                        LocalDate effectiveEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;
                                        long monthDays = ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;

                                        Float totalCost = entry.getValue().stream()
                                                        .map(d -> d.getCostPercentage() * 10.0f / 100.0f) // Convert
                                                                                                          // back to raw
                                                                                                          // cost
                                                        .reduce(0.0f, Float::sum);
                                        Float costPercentage = (totalCost / (10.0f * monthDays)) * 100.0f;

                                        return MonthlyCostData.builder()
                                                        .month(month)
                                                        .year(year)
                                                        .costPercentage(costPercentage)
                                                        .build();
                                })
                                .sorted(Comparator.comparing((MonthlyCostData d) -> d.getYear())
                                                .thenComparing(MonthlyCostData::getMonth))
                                .collect(Collectors.toList());
        }
}
