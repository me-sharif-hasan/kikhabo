package com.iishanto.kikhabo.domain.usercase.preference;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.PreferenceDataSource;
import com.iishanto.kikhabo.domain.entities.people.Preference;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import com.iishanto.kikhabo.domain.usercase.preference.command.in.UpdatePreferenceCommand;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdatePreferenceUseCase implements UseCase<Preference, UpdatePreferenceCommand> {
    PreferenceDataSource preferenceDataSource;
    Logger logger;
    @Override
    public Preference execute(UpdatePreferenceCommand updatePreferenceCommand) throws Exception, GlobalServerException {
        Preference preference=new Preference();
        preference.setBudgetRating(updatePreferenceCommand.budgetRating());
        preference.setPregnant(updatePreferenceCommand.isPregnant());
        preference.setHasDiabetics(updatePreferenceCommand.hasDiabetics());
        preference.setSpecialNotes(updatePreferenceCommand.specialNotes());
        preference.setSpicyRating(updatePreferenceCommand.spicyRating());
        preference.setSaltTasteRating(updatePreferenceCommand.SaltTasteRating());
        preferenceDataSource.savePreference(preference);
        logger.info(String.valueOf(preference));
        return preference;
    }
}
