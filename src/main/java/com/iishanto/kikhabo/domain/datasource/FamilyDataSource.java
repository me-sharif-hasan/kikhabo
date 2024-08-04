package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.common.exception.family.FamilyMemberCreationException;

public interface FamilyDataSource {
    void addFamilyMember(Long userId) throws FamilyMemberCreationException;
}
