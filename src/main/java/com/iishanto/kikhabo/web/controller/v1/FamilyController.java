package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.usercase.family.AddFamilyUseCase;
import com.iishanto.kikhabo.domain.usercase.family.DeleteFamilyMemberUseCase;
import com.iishanto.kikhabo.domain.usercase.family.command.GetFamilyMembersUseCase;
import com.iishanto.kikhabo.domain.usercase.family.command.in.AddFamilyMemberCommand;
import com.iishanto.kikhabo.domain.usercase.family.command.in.DeleteFamilyMembersCommand;
import com.iishanto.kikhabo.domain.usercase.family.command.out.GetFamilyMembersDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hibernate.type.descriptor.jdbc.JdbcTypeFamilyInformation;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Tag(name = "Family", description = "Family specific operations here.")
@RestController
@RequestMapping("api/v1/family")
public class FamilyController {
    AddFamilyUseCase addFamilyUseCase;
    GetFamilyMembersUseCase getFamilyMembersUseCase;
    DeleteFamilyMemberUseCase deleteFamilyMemberUseCase;
    Logger logger;
    @PostMapping
    public ResponseEntity<SuccessResponse<String>> addFamily(@RequestBody AddFamilyMemberCommand addFamilyMemberCommand) throws Exception {
        String response = addFamilyUseCase.execute(addFamilyMemberCommand);
        return new ResponseEntity<>(new SuccessResponse<>(response), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<GetFamilyMembersDto>> getAllFamilyMembers() throws Exception {
        GetFamilyMembersDto getFamilyMembersDto=getFamilyMembersUseCase.execute(null);
        SuccessResponse <GetFamilyMembersDto> successResponse=new SuccessResponse<>();
        successResponse.setData(getFamilyMembersDto);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<String>> deleteFamilyMember(@RequestParam DeleteFamilyMembersCommand deleteFamilyMembersCommand) throws Exception {
        deleteFamilyMemberUseCase.execute(deleteFamilyMembersCommand);
        SuccessResponse <String> successResponse=new SuccessResponse<>();
        successResponse.setMessage("Success");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
