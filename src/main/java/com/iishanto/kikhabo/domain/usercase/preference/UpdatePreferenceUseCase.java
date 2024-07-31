package com.iishanto.kikhabo.domain.usercase.preference;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.PreferenceDataSource;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import com.iishanto.kikhabo.domain.usercase.preference.command.in.UpdatePreferenceCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdatePreferenceUseCase implements UseCase<Void, UpdatePreferenceCommand> {
    PreferenceDataSource preferenceDataSource;
    @Override
    public Void execute(UpdatePreferenceCommand updatePreferenceCommand) throws Exception, GlobalServerException {
        return null;
    }
}
