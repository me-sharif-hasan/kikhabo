package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.common.exception.family.FamilyMemberCreationException;
import com.iishanto.kikhabo.domain.entities.people.User;

import java.util.List;

public interface FamilyDataSource {
    void addFamilyMember(Long userId) throws FamilyMemberCreationException;

    List<User> getFamilyMembersOfCurrentUser();

    void deleteFamilyMembers(List<Long> familyMemberIds);
}
