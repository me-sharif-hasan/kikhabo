package com.iishanto.kikhabo.domain.entities.meal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meal extends SchemaEntity{
    String mealName;
    Float totalEnergy;
    String note;
    String ingredients;
    List<Grocery> groceries;
}
