package com.iishanto.kikhabo.domain.usercase.family.command.out;

import com.iishanto.kikhabo.domain.entities.people.User;

import java.util.List;

public record GetFamilyMembersDto(
        List <User> members
){}