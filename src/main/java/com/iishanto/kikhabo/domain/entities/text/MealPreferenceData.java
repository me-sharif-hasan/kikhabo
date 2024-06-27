package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealPreferenceData extends SchemaEntity {
    Float spicyRating;
    Float saltRating;
    Float dayCount;
    Float priceRating;
    Integer totalMealCount;
}
