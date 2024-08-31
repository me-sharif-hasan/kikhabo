package com.iishanto.kikhabo.domain.usercase.family.command;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import com.iishanto.kikhabo.domain.usercase.family.command.out.GetFamilyMembersDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetFamilyMembersUseCase implements UseCase<GetFamilyMembersDto,Void> {
    FamilyDataSource familyDataSource;
    @Override
    public GetFamilyMembersDto execute(Void args) throws Exception, GlobalServerException {
        List <User> members = familyDataSource.getFamilyMembersOfCurrentUser();
        return new GetFamilyMembersDto(members);
    }
}
