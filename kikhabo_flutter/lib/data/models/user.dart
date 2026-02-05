import 'package:freezed_annotation/freezed_annotation.dart';
import 'preference.dart';

part 'user.freezed.dart';
part 'user.g.dart';

/// Represents a registered user in the system.
@freezed
abstract class User with _$User {
  const factory User({
    required int id,
    required String email,
    String? firstName,
    String? lastName,
    String? gender,
    String? country,
    String? dateOfBirth,
    double? weightInKg,
    double? heightInFt,
    String? religion,
    Preference? preference,
  }) = _User;

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
}

/// DTO for user registration.
@freezed
abstract class UserDto with _$UserDto {
  const factory UserDto({
    required String email,
    required String password,
    required String firstName,
    required String lastName,
    required String country,
    required String gender,
    required String religion,
    required String dateOfBirth,
    required double weightInKg,
    required double heightInFt,
  }) = _UserDto;

  factory UserDto.fromJson(Map<String, dynamic> json) => _$UserDtoFromJson(json);
}

/// DTO for updating user profile.
@freezed
abstract class UserUpdateDto with _$UserUpdateDto {
  const factory UserUpdateDto({
    String? email,
    String? firstName,
    String? lastName,
    String? country,
    String? gender,
    String? dateOfBirth,
    double? weightInKg,
    double? heightInFt,
  }) = _UserUpdateDto;

  factory UserUpdateDto.fromJson(Map<String, dynamic> json) => _$UserUpdateDtoFromJson(json);
}

/// DTO for login credentials.
@freezed
abstract class CredentialsDto with _$CredentialsDto {
  const factory CredentialsDto({
    required String email,
    required String password,
  }) = _CredentialsDto;

  factory CredentialsDto.fromJson(Map<String, dynamic> json) => _$CredentialsDtoFromJson(json);
}

/// Response object for login request.
@freezed
abstract class LoginResponseDto with _$LoginResponseDto {
  const factory LoginResponseDto({
    required String status,
    required String token,
  }) = _LoginResponseDto;

  factory LoginResponseDto.fromJson(Map<String, dynamic> json) => _$LoginResponseDtoFromJson(json);
}

/// Response object when fetching current user (wraps User and adds/modifies fields if needed).
/// Based on `GetUserResponse` in React analysis.
@freezed
abstract class GetUserResponse with _$GetUserResponse {
  const factory GetUserResponse({
    required int id,
    required String email,
    String? firstName,
    String? lastName,
    String? gender,
    String? country,
    String? dateOfBirth,
    double? weightInKg,
    double? heightInFt,
    String? religion,
    Preference? preference,
  }) = _GetUserResponse;

  factory GetUserResponse.fromJson(Map<String, dynamic> json) => _$GetUserResponseFromJson(json);
}
