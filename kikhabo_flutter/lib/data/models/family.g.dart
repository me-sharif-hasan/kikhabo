// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'family.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_GetFamilyMembersDto _$GetFamilyMembersDtoFromJson(Map<String, dynamic> json) =>
    _GetFamilyMembersDto(
      members: (json['members'] as List<dynamic>)
          .map((e) => User.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$GetFamilyMembersDtoToJson(
  _GetFamilyMembersDto instance,
) => <String, dynamic>{'members': instance.members};

_AddFamilyMemberCommand _$AddFamilyMemberCommandFromJson(
  Map<String, dynamic> json,
) => _AddFamilyMemberCommand(
  familyMemberIds: (json['familyMemberIds'] as List<dynamic>)
      .map((e) => (e as num).toInt())
      .toList(),
);

Map<String, dynamic> _$AddFamilyMemberCommandToJson(
  _AddFamilyMemberCommand instance,
) => <String, dynamic>{'familyMemberIds': instance.familyMemberIds};
