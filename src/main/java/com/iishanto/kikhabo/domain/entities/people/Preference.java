package com.iishanto.kikhabo.domain.entities.people;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Preference {
    private Long id;
    private float spicyRating=5;
    private float saltTasteRating=5;
    private float budgetRating=5;
    private boolean hasDiabetics=false;
    private boolean isPregnant=false;
    private String specialNotes="";
}