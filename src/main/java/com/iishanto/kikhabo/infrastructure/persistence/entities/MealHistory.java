package com.iishanto.kikhabo.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
public class MealHistory {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private JpaUser user;
    private Long timestamp;
}
