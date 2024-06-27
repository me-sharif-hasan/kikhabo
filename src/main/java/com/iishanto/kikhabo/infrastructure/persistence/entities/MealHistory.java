package com.iishanto.kikhabo.infrastructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MealHistory {

    @Id
    private Long id;
}
