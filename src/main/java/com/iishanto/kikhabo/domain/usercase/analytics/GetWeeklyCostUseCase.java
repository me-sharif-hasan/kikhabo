package com.iishanto.kikhabo.domain.usercase.analytics;

import com.iishanto.kikhabo.domain.datasource.AnalyticsDataSource;
import com.iishanto.kikhabo.domain.entities.analytics.WeeklyCostData;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetWeeklyCostUseCase implements UseCase<List<WeeklyCostData>, Integer> {
    AnalyticsDataSource analyticsDataSource;

    @Override
    public List<WeeklyCostData> execute(Integer weeks) throws Exception {
        if (weeks == null || weeks <= 0) {
            weeks = 4; // Default to 4 weeks
        }
        return analyticsDataSource.getWeeklyCost(weeks);
    }

    public List<WeeklyCostData> execute(LocalDate startDate, LocalDate endDate) {
        return analyticsDataSource.getWeeklyCost(startDate, endDate);
    }
}
