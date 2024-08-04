package com.iishanto.kikhabo.domain.usercase.family.command.in;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class AddFamilyMemberCommand{
    List<Long> familyMemberIds;
}
