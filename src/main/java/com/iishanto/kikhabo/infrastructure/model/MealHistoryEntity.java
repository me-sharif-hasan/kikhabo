package com.iishanto.kikhabo.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "meal_histories")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MealHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mealStatus;
    @Nullable
    private UUID groupId;
    @OneToOne
    @JoinColumn(name = "meal_id")
    private MealEntity mealEntity;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_history_id")
    private List<GroceryEntity> groceries;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    private Float rating;
    private String userNote;
    private Long timestamp;
}
