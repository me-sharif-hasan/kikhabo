package com.iishanto.kikhabo.domain.usercase.family.command.in;

import java.util.List;

public record DeleteFamilyMembersCommand(
        List <Long> familyMemberIds
) {
}
