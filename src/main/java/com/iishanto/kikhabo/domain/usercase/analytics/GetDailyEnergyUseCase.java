package com.iishanto.kikhabo.domain.usercase.analytics;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.DailyEnergyData;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetDailyEnergyUseCase implements UseCase<List<DailyEnergyData>, Integer> {
    AnalyticsDataSource analyticsDataSource;

    @Override
    public List<DailyEnergyData> execute(Integer days) throws Exception {
        if (days == null || days <= 0) {
            days = 7; // Default to 7 days
        }
        return analyticsDataSource.getDailyEnergyConsumption(days);
    }

    public List<DailyEnergyData> execute(LocalDate startDate, LocalDate endDate) {
        return analyticsDataSource.getDailyEnergyConsumption(startDate, endDate);
    }
}
