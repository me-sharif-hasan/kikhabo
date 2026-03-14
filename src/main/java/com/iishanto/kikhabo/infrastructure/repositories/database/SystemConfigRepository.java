package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Long> {

    /** All enabled entries in a group, sorted by priority ascending (1 = first tried). */
    List<SystemConfigEntity> findByConfigGroupAndEnabledTrueOrderByPriorityAsc(String configGroup);

    /** All entries in a group (including disabled) — used by admin listing. */
    List<SystemConfigEntity> findByConfigGroupOrderByPriorityAsc(String configGroup);

    /** Check for duplicates before insert. */
    boolean existsByConfigGroupAndConfigValue(String configGroup, String configValue);
}
