package com.iishanto.kikhabo.domain.entities.weather;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weather extends SchemaEntity {
    private String locationName;
    private String season;
    private String date;
    private float temperature;
    private float humidity;
    private float pressure;
    private float windSpeed;
    private float windDirection;
}
