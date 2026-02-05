import 'package:freezed_annotation/freezed_annotation.dart';
import 'user.dart';

part 'family.freezed.dart';
part 'family.g.dart';

/// DTO for fetching family members.
@freezed
abstract class GetFamilyMembersDto with _$GetFamilyMembersDto {
  const factory GetFamilyMembersDto({
    required List<User> members,
  }) = _GetFamilyMembersDto;

  factory GetFamilyMembersDto.fromJson(Map<String, dynamic> json) => _$GetFamilyMembersDtoFromJson(json);
}

/// Command to add a family member.
@freezed
abstract class AddFamilyMemberCommand with _$AddFamilyMemberCommand {
  const factory AddFamilyMemberCommand({
    required List<int> familyMemberIds,
  }) = _AddFamilyMemberCommand;

  factory AddFamilyMemberCommand.fromJson(Map<String, dynamic> json) => _$AddFamilyMemberCommandFromJson(json);
}
