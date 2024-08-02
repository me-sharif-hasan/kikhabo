package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.PreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<PreferenceEntity,Long> {
}
