package com.iishanto.kikhabo.domain.usercase.analytics;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.MonthlyEnergyData;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetMonthlyEnergyUseCase implements UseCase<List<MonthlyEnergyData>, Integer> {
    AnalyticsDataSource analyticsDataSource;

    @Override
    public List<MonthlyEnergyData> execute(Integer months) throws Exception {
        if (months == null || months <= 0) {
            months = 6; // Default to 6 months
        }
        return analyticsDataSource.getMonthlyEnergyConsumption(months);
    }

    public List<MonthlyEnergyData> execute(LocalDate startDate, LocalDate endDate) {
        return analyticsDataSource.getMonthlyEnergyConsumption(startDate, endDate);
    }
}
