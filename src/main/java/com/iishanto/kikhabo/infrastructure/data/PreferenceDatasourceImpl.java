package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.PreferenceDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.Preference;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.PreferenceEntity;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.PreferenceRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PreferenceDatasourceImpl implements PreferenceDataSource {
    PreferenceRepository preferenceRepository;
    UserDataSource userDataSource;
    UserRepository userRepository;
    Logger logger;
    @Override
    public void savePreference(Preference preference) {
        PreferenceEntity preferenceEntity=PreferenceEntity.fromDomain(preference);
        preferenceEntity = preferenceRepository.save(preferenceEntity);
        UserEntity userEntity=getAuthenticatedUserEntity();
        logger.info(preferenceEntity.getSpecialNotes());
        userEntity.setPreference(preferenceEntity);
        userRepository.save(userEntity);
    }

    @Override
    public Preference getPreference() throws NullPointerException {
        UserEntity userEntity=getAuthenticatedUserEntity();
        PreferenceEntity preferenceEntity=userEntity.getPreference();
        return new Preference();
    }

    private UserEntity getAuthenticatedUserEntity() {
        User user=userDataSource.getAuthenticatedUser();
        return userRepository.findByEmail(user.getEmail());
    }
}
