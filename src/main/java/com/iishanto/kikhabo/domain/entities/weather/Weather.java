package com.iishanto.kikhabo.domain.entities.weather;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Weather extends SchemaEntity {
    private String locationName;
    private String season;
    private String date;
    private float temperature;
    private float feelsLike;
    private float tempMin;
    private float tempMax;
    private float pressure;
    private float humidity;
    private int visibility;
    private float windSpeed;
    private float windDirection;
    private float windGust;
    private int cloudiness;
    private long timestamp;
    private long sunrise;
    private long sunset;
    private String country;
}
