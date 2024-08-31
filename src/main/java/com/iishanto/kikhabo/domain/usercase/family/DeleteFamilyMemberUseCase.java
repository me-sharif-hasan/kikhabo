package com.iishanto.kikhabo.domain.usercase.family;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import com.iishanto.kikhabo.domain.usercase.family.command.in.DeleteFamilyMembersCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteFamilyMemberUseCase implements UseCase<Void,DeleteFamilyMembersCommand> {
    FamilyDataSource familyDataSource;
    @Override
    public Void execute(DeleteFamilyMembersCommand deleteFamilyMembersCommand) throws Exception, GlobalServerException {
        familyDataSource.deleteFamilyMembers(deleteFamilyMembersCommand.familyMemberIds());
        return null;
    }
}
