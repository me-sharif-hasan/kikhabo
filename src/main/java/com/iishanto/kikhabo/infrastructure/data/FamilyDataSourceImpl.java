package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.common.exception.family.FamilyMemberCreationException;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.FamilyRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class FamilyDataSourceImpl implements FamilyDataSource {
    UserRepository userRepository;
    UserDataSource userDataSource;
    FamilyRepository familyRepository;
    Logger logger;
    @Override
    public void addFamilyMember(Long userId) throws FamilyMemberCreationException {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        String authUserEmail=userDataSource.getAuthUserEmail();
        UserEntity currentUser=userRepository.findByEmail(authUserEmail);
        if (userEntity != null&&currentUser!=null) {
            if (!Objects.equals(userEntity.getId(), currentUser.getId())) {
                logger.info("-> target: {} current: {}", userEntity.getId(), currentUser.getId());
                familyRepository.addFamily(currentUser.getId(), userEntity.getId());
            }else{
                throw new FamilyMemberCreationException("You can not set yourself as your family");
            }
        }else{
            throw new FamilyMemberCreationException("Invalid user");
        }
    }

    @Override
    public List<User> getFamilyMembersOfCurrentUser() {
        String email=userDataSource.getAuthUserEmail();
        UserEntity user=userRepository.findByEmail(email);
        List <UserEntity> members=user.getFamilyMembers();
        return members.stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public void deleteFamilyMembers(List<Long> familyMemberIds) {
        String email=userDataSource.getAuthUserEmail();
        UserEntity user=userRepository.findByEmail(email);
        List <UserEntity> members=user.getFamilyMembers().stream().filter(userEntity -> !familyMemberIds.contains(userEntity.getId())).toList();
        user.setFamilyMembers(members);
        userRepository.save(user);
    }
}
