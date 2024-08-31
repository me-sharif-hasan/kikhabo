package com.iishanto.kikhabo.domain.usercase.family;

import com.iishanto.kikhabo.common.exception.family.FamilyMemberCreationException;
import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.FamilyDataSource;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import com.iishanto.kikhabo.domain.usercase.family.command.in.AddFamilyMemberCommand;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddFamilyUseCase implements UseCase<String,AddFamilyMemberCommand> {
    FamilyDataSource familyDataSource;

    @Transactional
    @Override
    public String execute(AddFamilyMemberCommand addFamilyMemberCommand) throws Exception, GlobalServerException {
        for (int i = 0; i < addFamilyMemberCommand.getFamilyMemberIds().size(); i++) {
            familyDataSource.addFamilyMember(addFamilyMemberCommand.getFamilyMemberIds().get(i));
        }
        return "Successfully added %d family members".formatted(addFamilyMemberCommand.getFamilyMemberIds().size());
    }
}
