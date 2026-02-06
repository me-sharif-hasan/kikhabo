package com.iishanto.kikhabo.domain.usercase.analytics;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.WeeklyEnergyData;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetWeeklyEnergyUseCase implements UseCase<List<WeeklyEnergyData>, Integer> {
    AnalyticsDataSource analyticsDataSource;

    @Override
    public List<WeeklyEnergyData> execute(Integer weeks) throws Exception {
        if (weeks == null || weeks <= 0) {
            weeks = 4; // Default to 4 weeks
        }
        return analyticsDataSource.getWeeklyEnergyConsumption(weeks);
    }

    public List<WeeklyEnergyData> execute(LocalDate startDate, LocalDate endDate) {
        return analyticsDataSource.getWeeklyEnergyConsumption(startDate, endDate);
    }
}
