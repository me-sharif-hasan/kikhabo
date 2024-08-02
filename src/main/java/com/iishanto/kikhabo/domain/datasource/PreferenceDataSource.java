package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.people.Preference;
import org.springframework.stereotype.Component;

@Component
public interface PreferenceDataSource {
    public void savePreference(Preference preference);
    public Preference getPreference();
}
