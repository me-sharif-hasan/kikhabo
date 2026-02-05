// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_User _$UserFromJson(Map<String, dynamic> json) => _User(
  id: (json['id'] as num).toInt(),
  email: json['email'] as String,
  firstName: json['firstName'] as String?,
  lastName: json['lastName'] as String?,
  gender: json['gender'] as String?,
  country: json['country'] as String?,
  dateOfBirth: json['dateOfBirth'] as String?,
  weightInKg: (json['weightInKg'] as num?)?.toDouble(),
  heightInFt: (json['heightInFt'] as num?)?.toDouble(),
  religion: json['religion'] as String?,
  preference: json['preference'] == null
      ? null
      : Preference.fromJson(json['preference'] as Map<String, dynamic>),
);

Map<String, dynamic> _$UserToJson(_User instance) => <String, dynamic>{
  'id': instance.id,
  'email': instance.email,
  'firstName': instance.firstName,
  'lastName': instance.lastName,
  'gender': instance.gender,
  'country': instance.country,
  'dateOfBirth': instance.dateOfBirth,
  'weightInKg': instance.weightInKg,
  'heightInFt': instance.heightInFt,
  'religion': instance.religion,
  'preference': instance.preference,
};

_UserDto _$UserDtoFromJson(Map<String, dynamic> json) => _UserDto(
  email: json['email'] as String,
  password: json['password'] as String,
  firstName: json['firstName'] as String,
  lastName: json['lastName'] as String,
  country: json['country'] as String,
  gender: json['gender'] as String,
  religion: json['religion'] as String,
  dateOfBirth: json['dateOfBirth'] as String,
  weightInKg: (json['weightInKg'] as num).toDouble(),
  heightInFt: (json['heightInFt'] as num).toDouble(),
);

Map<String, dynamic> _$UserDtoToJson(_UserDto instance) => <String, dynamic>{
  'email': instance.email,
  'password': instance.password,
  'firstName': instance.firstName,
  'lastName': instance.lastName,
  'country': instance.country,
  'gender': instance.gender,
  'religion': instance.religion,
  'dateOfBirth': instance.dateOfBirth,
  'weightInKg': instance.weightInKg,
  'heightInFt': instance.heightInFt,
};

_UserUpdateDto _$UserUpdateDtoFromJson(Map<String, dynamic> json) =>
    _UserUpdateDto(
      email: json['email'] as String?,
      firstName: json['firstName'] as String?,
      lastName: json['lastName'] as String?,
      country: json['country'] as String?,
      gender: json['gender'] as String?,
      dateOfBirth: json['dateOfBirth'] as String?,
      weightInKg: (json['weightInKg'] as num?)?.toDouble(),
      heightInFt: (json['heightInFt'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$UserUpdateDtoToJson(_UserUpdateDto instance) =>
    <String, dynamic>{
      'email': instance.email,
      'firstName': instance.firstName,
      'lastName': instance.lastName,
      'country': instance.country,
      'gender': instance.gender,
      'dateOfBirth': instance.dateOfBirth,
      'weightInKg': instance.weightInKg,
      'heightInFt': instance.heightInFt,
    };

_CredentialsDto _$CredentialsDtoFromJson(Map<String, dynamic> json) =>
    _CredentialsDto(
      email: json['email'] as String,
      password: json['password'] as String,
    );

Map<String, dynamic> _$CredentialsDtoToJson(_CredentialsDto instance) =>
    <String, dynamic>{'email': instance.email, 'password': instance.password};

_LoginResponseDto _$LoginResponseDtoFromJson(Map<String, dynamic> json) =>
    _LoginResponseDto(
      status: json['status'] as String,
      token: json['token'] as String,
    );

Map<String, dynamic> _$LoginResponseDtoToJson(_LoginResponseDto instance) =>
    <String, dynamic>{'status': instance.status, 'token': instance.token};

_GetUserResponse _$GetUserResponseFromJson(Map<String, dynamic> json) =>
    _GetUserResponse(
      id: (json['id'] as num).toInt(),
      email: json['email'] as String,
      firstName: json['firstName'] as String?,
      lastName: json['lastName'] as String?,
      gender: json['gender'] as String?,
      country: json['country'] as String?,
      dateOfBirth: json['dateOfBirth'] as String?,
      weightInKg: (json['weightInKg'] as num?)?.toDouble(),
      heightInFt: (json['heightInFt'] as num?)?.toDouble(),
      religion: json['religion'] as String?,
      preference: json['preference'] == null
          ? null
          : Preference.fromJson(json['preference'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$GetUserResponseToJson(_GetUserResponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'email': instance.email,
      'firstName': instance.firstName,
      'lastName': instance.lastName,
      'gender': instance.gender,
      'country': instance.country,
      'dateOfBirth': instance.dateOfBirth,
      'weightInKg': instance.weightInKg,
      'heightInFt': instance.heightInFt,
      'religion': instance.religion,
      'preference': instance.preference,
    };
