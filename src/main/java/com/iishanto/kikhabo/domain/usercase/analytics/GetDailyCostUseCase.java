package com.iishanto.kikhabo.domain.usercase.analytics;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.DailyCostData;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetDailyCostUseCase implements UseCase<List<DailyCostData>, Integer> {
    AnalyticsDataSource analyticsDataSource;

    @Override
    public List<DailyCostData> execute(Integer days) throws Exception {
        if (days == null || days <= 0) {
            days = 7; // Default to 7 days
        }
        return analyticsDataSource.getDailyCost(days);
    }

    public List<DailyCostData> execute(LocalDate startDate, LocalDate endDate) {
        return analyticsDataSource.getDailyCost(startDate, endDate);
    }
}
