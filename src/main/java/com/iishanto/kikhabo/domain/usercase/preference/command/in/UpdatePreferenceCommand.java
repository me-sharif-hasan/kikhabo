package com.iishanto.kikhabo.domain.usercase.preference.command.in;

public record UpdatePreferenceCommand(
        Float spicyRating,
        Float budgetRating,
        Float SaltTasteRating,
        Boolean hasDiabetics,
        Boolean isPregnant,
        String specialNotes
) {
}
