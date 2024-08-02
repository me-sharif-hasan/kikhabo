package com.iishanto.kikhabo.infrastructure.model;

import com.iishanto.kikhabo.domain.entities.people.Preference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    float spicyRating=5;
    float saltTasteRating=5;
    float budgetRating=5;
    boolean hasDiabetics=false;
    boolean isPregnant=false;
    String specialNotes="";

    @OneToOne
    UserEntity user;

    public static PreferenceEntity fromDomain(Preference preference){
        return new PreferenceEntityBuilder()
                .budgetRating(preference.getBudgetRating())
                .isPregnant(preference.isPregnant())
                .spicyRating(preference.getSpicyRating())
                .hasDiabetics(preference.isHasDiabetics())
                .specialNotes(preference.getSpecialNotes())
                .saltTasteRating(preference.getSaltTasteRating())
                .id(preference.getId())
                .build();
    }

    public Preference toDomain() {
        Preference preference=new Preference();
        preference.setBudgetRating(budgetRating);
        preference.setSpicyRating(spicyRating);
        preference.setHasDiabetics(hasDiabetics);
        preference.setSpicyRating(spicyRating);
        preference.setPregnant(isPregnant);
        preference.setSpecialNotes(specialNotes);
        preference.setSaltTasteRating(saltTasteRating);
        preference.setId(id);
        return preference;
    }
}
