package com.iishanto.kikhabo.domain.entities.meal;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grocery extends SchemaEntity{
    String name;
    String priceRatingOutOf10;
    String amountInGm;
}