// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'user.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$User {

 int get id; String get email; String? get firstName; String? get lastName; String? get gender; String? get country; String? get dateOfBirth; double? get weightInKg; double? get heightInFt; String? get religion; Preference? get preference;
/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UserCopyWith<User> get copyWith => _$UserCopyWithImpl<User>(this as User, _$identity);

  /// Serializes this User to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is User&&(identical(other.id, id) || other.id == id)&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.country, country) || other.country == country)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.preference, preference) || other.preference == preference));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,email,firstName,lastName,gender,country,dateOfBirth,weightInKg,heightInFt,religion,preference);

@override
String toString() {
  return 'User(id: $id, email: $email, firstName: $firstName, lastName: $lastName, gender: $gender, country: $country, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt, religion: $religion, preference: $preference)';
}


}

/// @nodoc
abstract mixin class $UserCopyWith<$Res>  {
  factory $UserCopyWith(User value, $Res Function(User) _then) = _$UserCopyWithImpl;
@useResult
$Res call({
 int id, String email, String? firstName, String? lastName, String? gender, String? country, String? dateOfBirth, double? weightInKg, double? heightInFt, String? religion, Preference? preference
});


$PreferenceCopyWith<$Res>? get preference;

}
/// @nodoc
class _$UserCopyWithImpl<$Res>
    implements $UserCopyWith<$Res> {
  _$UserCopyWithImpl(this._self, this._then);

  final User _self;
  final $Res Function(User) _then;

/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? email = null,Object? firstName = freezed,Object? lastName = freezed,Object? gender = freezed,Object? country = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,Object? religion = freezed,Object? preference = freezed,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,religion: freezed == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String?,preference: freezed == preference ? _self.preference : preference // ignore: cast_nullable_to_non_nullable
as Preference?,
  ));
}
/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PreferenceCopyWith<$Res>? get preference {
    if (_self.preference == null) {
    return null;
  }

  return $PreferenceCopyWith<$Res>(_self.preference!, (value) {
    return _then(_self.copyWith(preference: value));
  });
}
}


/// Adds pattern-matching-related methods to [User].
extension UserPatterns on User {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _User value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _User() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _User value)  $default,){
final _that = this;
switch (_that) {
case _User():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _User value)?  $default,){
final _that = this;
switch (_that) {
case _User() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _User() when $default != null:
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)  $default,) {final _that = this;
switch (_that) {
case _User():
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)?  $default,) {final _that = this;
switch (_that) {
case _User() when $default != null:
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _User implements User {
  const _User({required this.id, required this.email, this.firstName, this.lastName, this.gender, this.country, this.dateOfBirth, this.weightInKg, this.heightInFt, this.religion, this.preference});
  factory _User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

@override final  int id;
@override final  String email;
@override final  String? firstName;
@override final  String? lastName;
@override final  String? gender;
@override final  String? country;
@override final  String? dateOfBirth;
@override final  double? weightInKg;
@override final  double? heightInFt;
@override final  String? religion;
@override final  Preference? preference;

/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UserCopyWith<_User> get copyWith => __$UserCopyWithImpl<_User>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UserToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _User&&(identical(other.id, id) || other.id == id)&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.country, country) || other.country == country)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.preference, preference) || other.preference == preference));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,email,firstName,lastName,gender,country,dateOfBirth,weightInKg,heightInFt,religion,preference);

@override
String toString() {
  return 'User(id: $id, email: $email, firstName: $firstName, lastName: $lastName, gender: $gender, country: $country, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt, religion: $religion, preference: $preference)';
}


}

/// @nodoc
abstract mixin class _$UserCopyWith<$Res> implements $UserCopyWith<$Res> {
  factory _$UserCopyWith(_User value, $Res Function(_User) _then) = __$UserCopyWithImpl;
@override @useResult
$Res call({
 int id, String email, String? firstName, String? lastName, String? gender, String? country, String? dateOfBirth, double? weightInKg, double? heightInFt, String? religion, Preference? preference
});


@override $PreferenceCopyWith<$Res>? get preference;

}
/// @nodoc
class __$UserCopyWithImpl<$Res>
    implements _$UserCopyWith<$Res> {
  __$UserCopyWithImpl(this._self, this._then);

  final _User _self;
  final $Res Function(_User) _then;

/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? email = null,Object? firstName = freezed,Object? lastName = freezed,Object? gender = freezed,Object? country = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,Object? religion = freezed,Object? preference = freezed,}) {
  return _then(_User(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,religion: freezed == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String?,preference: freezed == preference ? _self.preference : preference // ignore: cast_nullable_to_non_nullable
as Preference?,
  ));
}

/// Create a copy of User
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PreferenceCopyWith<$Res>? get preference {
    if (_self.preference == null) {
    return null;
  }

  return $PreferenceCopyWith<$Res>(_self.preference!, (value) {
    return _then(_self.copyWith(preference: value));
  });
}
}


/// @nodoc
mixin _$UserDto {

 String get email; String get password; String get firstName; String get lastName; String get country; String get gender; String get religion; String get dateOfBirth; double get weightInKg; double get heightInFt;
/// Create a copy of UserDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UserDtoCopyWith<UserDto> get copyWith => _$UserDtoCopyWithImpl<UserDto>(this as UserDto, _$identity);

  /// Serializes this UserDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is UserDto&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.country, country) || other.country == country)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,password,firstName,lastName,country,gender,religion,dateOfBirth,weightInKg,heightInFt);

@override
String toString() {
  return 'UserDto(email: $email, password: $password, firstName: $firstName, lastName: $lastName, country: $country, gender: $gender, religion: $religion, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt)';
}


}

/// @nodoc
abstract mixin class $UserDtoCopyWith<$Res>  {
  factory $UserDtoCopyWith(UserDto value, $Res Function(UserDto) _then) = _$UserDtoCopyWithImpl;
@useResult
$Res call({
 String email, String password, String firstName, String lastName, String country, String gender, String religion, String dateOfBirth, double weightInKg, double heightInFt
});




}
/// @nodoc
class _$UserDtoCopyWithImpl<$Res>
    implements $UserDtoCopyWith<$Res> {
  _$UserDtoCopyWithImpl(this._self, this._then);

  final UserDto _self;
  final $Res Function(UserDto) _then;

/// Create a copy of UserDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? email = null,Object? password = null,Object? firstName = null,Object? lastName = null,Object? country = null,Object? gender = null,Object? religion = null,Object? dateOfBirth = null,Object? weightInKg = null,Object? heightInFt = null,}) {
  return _then(_self.copyWith(
email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,firstName: null == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String,lastName: null == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String,country: null == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String,gender: null == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String,religion: null == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String,dateOfBirth: null == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String,weightInKg: null == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double,heightInFt: null == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double,
  ));
}

}


/// Adds pattern-matching-related methods to [UserDto].
extension UserDtoPatterns on UserDto {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _UserDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _UserDto() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _UserDto value)  $default,){
final _that = this;
switch (_that) {
case _UserDto():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _UserDto value)?  $default,){
final _that = this;
switch (_that) {
case _UserDto() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String email,  String password,  String firstName,  String lastName,  String country,  String gender,  String religion,  String dateOfBirth,  double weightInKg,  double heightInFt)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _UserDto() when $default != null:
return $default(_that.email,_that.password,_that.firstName,_that.lastName,_that.country,_that.gender,_that.religion,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String email,  String password,  String firstName,  String lastName,  String country,  String gender,  String religion,  String dateOfBirth,  double weightInKg,  double heightInFt)  $default,) {final _that = this;
switch (_that) {
case _UserDto():
return $default(_that.email,_that.password,_that.firstName,_that.lastName,_that.country,_that.gender,_that.religion,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String email,  String password,  String firstName,  String lastName,  String country,  String gender,  String religion,  String dateOfBirth,  double weightInKg,  double heightInFt)?  $default,) {final _that = this;
switch (_that) {
case _UserDto() when $default != null:
return $default(_that.email,_that.password,_that.firstName,_that.lastName,_that.country,_that.gender,_that.religion,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _UserDto implements UserDto {
  const _UserDto({required this.email, required this.password, required this.firstName, required this.lastName, required this.country, required this.gender, required this.religion, required this.dateOfBirth, required this.weightInKg, required this.heightInFt});
  factory _UserDto.fromJson(Map<String, dynamic> json) => _$UserDtoFromJson(json);

@override final  String email;
@override final  String password;
@override final  String firstName;
@override final  String lastName;
@override final  String country;
@override final  String gender;
@override final  String religion;
@override final  String dateOfBirth;
@override final  double weightInKg;
@override final  double heightInFt;

/// Create a copy of UserDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UserDtoCopyWith<_UserDto> get copyWith => __$UserDtoCopyWithImpl<_UserDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UserDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _UserDto&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.country, country) || other.country == country)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,password,firstName,lastName,country,gender,religion,dateOfBirth,weightInKg,heightInFt);

@override
String toString() {
  return 'UserDto(email: $email, password: $password, firstName: $firstName, lastName: $lastName, country: $country, gender: $gender, religion: $religion, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt)';
}


}

/// @nodoc
abstract mixin class _$UserDtoCopyWith<$Res> implements $UserDtoCopyWith<$Res> {
  factory _$UserDtoCopyWith(_UserDto value, $Res Function(_UserDto) _then) = __$UserDtoCopyWithImpl;
@override @useResult
$Res call({
 String email, String password, String firstName, String lastName, String country, String gender, String religion, String dateOfBirth, double weightInKg, double heightInFt
});




}
/// @nodoc
class __$UserDtoCopyWithImpl<$Res>
    implements _$UserDtoCopyWith<$Res> {
  __$UserDtoCopyWithImpl(this._self, this._then);

  final _UserDto _self;
  final $Res Function(_UserDto) _then;

/// Create a copy of UserDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? email = null,Object? password = null,Object? firstName = null,Object? lastName = null,Object? country = null,Object? gender = null,Object? religion = null,Object? dateOfBirth = null,Object? weightInKg = null,Object? heightInFt = null,}) {
  return _then(_UserDto(
email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,firstName: null == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String,lastName: null == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String,country: null == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String,gender: null == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String,religion: null == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String,dateOfBirth: null == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String,weightInKg: null == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double,heightInFt: null == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double,
  ));
}


}


/// @nodoc
mixin _$UserUpdateDto {

 String? get email; String? get firstName; String? get lastName; String? get country; String? get gender; String? get dateOfBirth; double? get weightInKg; double? get heightInFt;
/// Create a copy of UserUpdateDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UserUpdateDtoCopyWith<UserUpdateDto> get copyWith => _$UserUpdateDtoCopyWithImpl<UserUpdateDto>(this as UserUpdateDto, _$identity);

  /// Serializes this UserUpdateDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is UserUpdateDto&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.country, country) || other.country == country)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,firstName,lastName,country,gender,dateOfBirth,weightInKg,heightInFt);

@override
String toString() {
  return 'UserUpdateDto(email: $email, firstName: $firstName, lastName: $lastName, country: $country, gender: $gender, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt)';
}


}

/// @nodoc
abstract mixin class $UserUpdateDtoCopyWith<$Res>  {
  factory $UserUpdateDtoCopyWith(UserUpdateDto value, $Res Function(UserUpdateDto) _then) = _$UserUpdateDtoCopyWithImpl;
@useResult
$Res call({
 String? email, String? firstName, String? lastName, String? country, String? gender, String? dateOfBirth, double? weightInKg, double? heightInFt
});




}
/// @nodoc
class _$UserUpdateDtoCopyWithImpl<$Res>
    implements $UserUpdateDtoCopyWith<$Res> {
  _$UserUpdateDtoCopyWithImpl(this._self, this._then);

  final UserUpdateDto _self;
  final $Res Function(UserUpdateDto) _then;

/// Create a copy of UserUpdateDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? email = freezed,Object? firstName = freezed,Object? lastName = freezed,Object? country = freezed,Object? gender = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,}) {
  return _then(_self.copyWith(
email: freezed == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String?,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,
  ));
}

}


/// Adds pattern-matching-related methods to [UserUpdateDto].
extension UserUpdateDtoPatterns on UserUpdateDto {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _UserUpdateDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _UserUpdateDto() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _UserUpdateDto value)  $default,){
final _that = this;
switch (_that) {
case _UserUpdateDto():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _UserUpdateDto value)?  $default,){
final _that = this;
switch (_that) {
case _UserUpdateDto() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String? email,  String? firstName,  String? lastName,  String? country,  String? gender,  String? dateOfBirth,  double? weightInKg,  double? heightInFt)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _UserUpdateDto() when $default != null:
return $default(_that.email,_that.firstName,_that.lastName,_that.country,_that.gender,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String? email,  String? firstName,  String? lastName,  String? country,  String? gender,  String? dateOfBirth,  double? weightInKg,  double? heightInFt)  $default,) {final _that = this;
switch (_that) {
case _UserUpdateDto():
return $default(_that.email,_that.firstName,_that.lastName,_that.country,_that.gender,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String? email,  String? firstName,  String? lastName,  String? country,  String? gender,  String? dateOfBirth,  double? weightInKg,  double? heightInFt)?  $default,) {final _that = this;
switch (_that) {
case _UserUpdateDto() when $default != null:
return $default(_that.email,_that.firstName,_that.lastName,_that.country,_that.gender,_that.dateOfBirth,_that.weightInKg,_that.heightInFt);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _UserUpdateDto implements UserUpdateDto {
  const _UserUpdateDto({this.email, this.firstName, this.lastName, this.country, this.gender, this.dateOfBirth, this.weightInKg, this.heightInFt});
  factory _UserUpdateDto.fromJson(Map<String, dynamic> json) => _$UserUpdateDtoFromJson(json);

@override final  String? email;
@override final  String? firstName;
@override final  String? lastName;
@override final  String? country;
@override final  String? gender;
@override final  String? dateOfBirth;
@override final  double? weightInKg;
@override final  double? heightInFt;

/// Create a copy of UserUpdateDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UserUpdateDtoCopyWith<_UserUpdateDto> get copyWith => __$UserUpdateDtoCopyWithImpl<_UserUpdateDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UserUpdateDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _UserUpdateDto&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.country, country) || other.country == country)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,firstName,lastName,country,gender,dateOfBirth,weightInKg,heightInFt);

@override
String toString() {
  return 'UserUpdateDto(email: $email, firstName: $firstName, lastName: $lastName, country: $country, gender: $gender, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt)';
}


}

/// @nodoc
abstract mixin class _$UserUpdateDtoCopyWith<$Res> implements $UserUpdateDtoCopyWith<$Res> {
  factory _$UserUpdateDtoCopyWith(_UserUpdateDto value, $Res Function(_UserUpdateDto) _then) = __$UserUpdateDtoCopyWithImpl;
@override @useResult
$Res call({
 String? email, String? firstName, String? lastName, String? country, String? gender, String? dateOfBirth, double? weightInKg, double? heightInFt
});




}
/// @nodoc
class __$UserUpdateDtoCopyWithImpl<$Res>
    implements _$UserUpdateDtoCopyWith<$Res> {
  __$UserUpdateDtoCopyWithImpl(this._self, this._then);

  final _UserUpdateDto _self;
  final $Res Function(_UserUpdateDto) _then;

/// Create a copy of UserUpdateDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? email = freezed,Object? firstName = freezed,Object? lastName = freezed,Object? country = freezed,Object? gender = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,}) {
  return _then(_UserUpdateDto(
email: freezed == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String?,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,
  ));
}


}


/// @nodoc
mixin _$CredentialsDto {

 String get email; String get password;
/// Create a copy of CredentialsDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$CredentialsDtoCopyWith<CredentialsDto> get copyWith => _$CredentialsDtoCopyWithImpl<CredentialsDto>(this as CredentialsDto, _$identity);

  /// Serializes this CredentialsDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is CredentialsDto&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,password);

@override
String toString() {
  return 'CredentialsDto(email: $email, password: $password)';
}


}

/// @nodoc
abstract mixin class $CredentialsDtoCopyWith<$Res>  {
  factory $CredentialsDtoCopyWith(CredentialsDto value, $Res Function(CredentialsDto) _then) = _$CredentialsDtoCopyWithImpl;
@useResult
$Res call({
 String email, String password
});




}
/// @nodoc
class _$CredentialsDtoCopyWithImpl<$Res>
    implements $CredentialsDtoCopyWith<$Res> {
  _$CredentialsDtoCopyWithImpl(this._self, this._then);

  final CredentialsDto _self;
  final $Res Function(CredentialsDto) _then;

/// Create a copy of CredentialsDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? email = null,Object? password = null,}) {
  return _then(_self.copyWith(
email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [CredentialsDto].
extension CredentialsDtoPatterns on CredentialsDto {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _CredentialsDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _CredentialsDto() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _CredentialsDto value)  $default,){
final _that = this;
switch (_that) {
case _CredentialsDto():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _CredentialsDto value)?  $default,){
final _that = this;
switch (_that) {
case _CredentialsDto() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String email,  String password)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _CredentialsDto() when $default != null:
return $default(_that.email,_that.password);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String email,  String password)  $default,) {final _that = this;
switch (_that) {
case _CredentialsDto():
return $default(_that.email,_that.password);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String email,  String password)?  $default,) {final _that = this;
switch (_that) {
case _CredentialsDto() when $default != null:
return $default(_that.email,_that.password);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _CredentialsDto implements CredentialsDto {
  const _CredentialsDto({required this.email, required this.password});
  factory _CredentialsDto.fromJson(Map<String, dynamic> json) => _$CredentialsDtoFromJson(json);

@override final  String email;
@override final  String password;

/// Create a copy of CredentialsDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$CredentialsDtoCopyWith<_CredentialsDto> get copyWith => __$CredentialsDtoCopyWithImpl<_CredentialsDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$CredentialsDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _CredentialsDto&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,email,password);

@override
String toString() {
  return 'CredentialsDto(email: $email, password: $password)';
}


}

/// @nodoc
abstract mixin class _$CredentialsDtoCopyWith<$Res> implements $CredentialsDtoCopyWith<$Res> {
  factory _$CredentialsDtoCopyWith(_CredentialsDto value, $Res Function(_CredentialsDto) _then) = __$CredentialsDtoCopyWithImpl;
@override @useResult
$Res call({
 String email, String password
});




}
/// @nodoc
class __$CredentialsDtoCopyWithImpl<$Res>
    implements _$CredentialsDtoCopyWith<$Res> {
  __$CredentialsDtoCopyWithImpl(this._self, this._then);

  final _CredentialsDto _self;
  final $Res Function(_CredentialsDto) _then;

/// Create a copy of CredentialsDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? email = null,Object? password = null,}) {
  return _then(_CredentialsDto(
email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}


/// @nodoc
mixin _$LoginResponseDto {

 String get status; String get token;
/// Create a copy of LoginResponseDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$LoginResponseDtoCopyWith<LoginResponseDto> get copyWith => _$LoginResponseDtoCopyWithImpl<LoginResponseDto>(this as LoginResponseDto, _$identity);

  /// Serializes this LoginResponseDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is LoginResponseDto&&(identical(other.status, status) || other.status == status)&&(identical(other.token, token) || other.token == token));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,status,token);

@override
String toString() {
  return 'LoginResponseDto(status: $status, token: $token)';
}


}

/// @nodoc
abstract mixin class $LoginResponseDtoCopyWith<$Res>  {
  factory $LoginResponseDtoCopyWith(LoginResponseDto value, $Res Function(LoginResponseDto) _then) = _$LoginResponseDtoCopyWithImpl;
@useResult
$Res call({
 String status, String token
});




}
/// @nodoc
class _$LoginResponseDtoCopyWithImpl<$Res>
    implements $LoginResponseDtoCopyWith<$Res> {
  _$LoginResponseDtoCopyWithImpl(this._self, this._then);

  final LoginResponseDto _self;
  final $Res Function(LoginResponseDto) _then;

/// Create a copy of LoginResponseDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? status = null,Object? token = null,}) {
  return _then(_self.copyWith(
status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as String,token: null == token ? _self.token : token // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [LoginResponseDto].
extension LoginResponseDtoPatterns on LoginResponseDto {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _LoginResponseDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _LoginResponseDto() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _LoginResponseDto value)  $default,){
final _that = this;
switch (_that) {
case _LoginResponseDto():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _LoginResponseDto value)?  $default,){
final _that = this;
switch (_that) {
case _LoginResponseDto() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String status,  String token)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _LoginResponseDto() when $default != null:
return $default(_that.status,_that.token);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String status,  String token)  $default,) {final _that = this;
switch (_that) {
case _LoginResponseDto():
return $default(_that.status,_that.token);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String status,  String token)?  $default,) {final _that = this;
switch (_that) {
case _LoginResponseDto() when $default != null:
return $default(_that.status,_that.token);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _LoginResponseDto implements LoginResponseDto {
  const _LoginResponseDto({required this.status, required this.token});
  factory _LoginResponseDto.fromJson(Map<String, dynamic> json) => _$LoginResponseDtoFromJson(json);

@override final  String status;
@override final  String token;

/// Create a copy of LoginResponseDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$LoginResponseDtoCopyWith<_LoginResponseDto> get copyWith => __$LoginResponseDtoCopyWithImpl<_LoginResponseDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$LoginResponseDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _LoginResponseDto&&(identical(other.status, status) || other.status == status)&&(identical(other.token, token) || other.token == token));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,status,token);

@override
String toString() {
  return 'LoginResponseDto(status: $status, token: $token)';
}


}

/// @nodoc
abstract mixin class _$LoginResponseDtoCopyWith<$Res> implements $LoginResponseDtoCopyWith<$Res> {
  factory _$LoginResponseDtoCopyWith(_LoginResponseDto value, $Res Function(_LoginResponseDto) _then) = __$LoginResponseDtoCopyWithImpl;
@override @useResult
$Res call({
 String status, String token
});




}
/// @nodoc
class __$LoginResponseDtoCopyWithImpl<$Res>
    implements _$LoginResponseDtoCopyWith<$Res> {
  __$LoginResponseDtoCopyWithImpl(this._self, this._then);

  final _LoginResponseDto _self;
  final $Res Function(_LoginResponseDto) _then;

/// Create a copy of LoginResponseDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? status = null,Object? token = null,}) {
  return _then(_LoginResponseDto(
status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as String,token: null == token ? _self.token : token // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}


/// @nodoc
mixin _$GetUserResponse {

 int get id; String get email; String? get firstName; String? get lastName; String? get gender; String? get country; String? get dateOfBirth; double? get weightInKg; double? get heightInFt; String? get religion; Preference? get preference;
/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$GetUserResponseCopyWith<GetUserResponse> get copyWith => _$GetUserResponseCopyWithImpl<GetUserResponse>(this as GetUserResponse, _$identity);

  /// Serializes this GetUserResponse to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is GetUserResponse&&(identical(other.id, id) || other.id == id)&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.country, country) || other.country == country)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.preference, preference) || other.preference == preference));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,email,firstName,lastName,gender,country,dateOfBirth,weightInKg,heightInFt,religion,preference);

@override
String toString() {
  return 'GetUserResponse(id: $id, email: $email, firstName: $firstName, lastName: $lastName, gender: $gender, country: $country, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt, religion: $religion, preference: $preference)';
}


}

/// @nodoc
abstract mixin class $GetUserResponseCopyWith<$Res>  {
  factory $GetUserResponseCopyWith(GetUserResponse value, $Res Function(GetUserResponse) _then) = _$GetUserResponseCopyWithImpl;
@useResult
$Res call({
 int id, String email, String? firstName, String? lastName, String? gender, String? country, String? dateOfBirth, double? weightInKg, double? heightInFt, String? religion, Preference? preference
});


$PreferenceCopyWith<$Res>? get preference;

}
/// @nodoc
class _$GetUserResponseCopyWithImpl<$Res>
    implements $GetUserResponseCopyWith<$Res> {
  _$GetUserResponseCopyWithImpl(this._self, this._then);

  final GetUserResponse _self;
  final $Res Function(GetUserResponse) _then;

/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? email = null,Object? firstName = freezed,Object? lastName = freezed,Object? gender = freezed,Object? country = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,Object? religion = freezed,Object? preference = freezed,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,religion: freezed == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String?,preference: freezed == preference ? _self.preference : preference // ignore: cast_nullable_to_non_nullable
as Preference?,
  ));
}
/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PreferenceCopyWith<$Res>? get preference {
    if (_self.preference == null) {
    return null;
  }

  return $PreferenceCopyWith<$Res>(_self.preference!, (value) {
    return _then(_self.copyWith(preference: value));
  });
}
}


/// Adds pattern-matching-related methods to [GetUserResponse].
extension GetUserResponsePatterns on GetUserResponse {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _GetUserResponse value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _GetUserResponse() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _GetUserResponse value)  $default,){
final _that = this;
switch (_that) {
case _GetUserResponse():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _GetUserResponse value)?  $default,){
final _that = this;
switch (_that) {
case _GetUserResponse() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _GetUserResponse() when $default != null:
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)  $default,) {final _that = this;
switch (_that) {
case _GetUserResponse():
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int id,  String email,  String? firstName,  String? lastName,  String? gender,  String? country,  String? dateOfBirth,  double? weightInKg,  double? heightInFt,  String? religion,  Preference? preference)?  $default,) {final _that = this;
switch (_that) {
case _GetUserResponse() when $default != null:
return $default(_that.id,_that.email,_that.firstName,_that.lastName,_that.gender,_that.country,_that.dateOfBirth,_that.weightInKg,_that.heightInFt,_that.religion,_that.preference);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _GetUserResponse implements GetUserResponse {
  const _GetUserResponse({required this.id, required this.email, this.firstName, this.lastName, this.gender, this.country, this.dateOfBirth, this.weightInKg, this.heightInFt, this.religion, this.preference});
  factory _GetUserResponse.fromJson(Map<String, dynamic> json) => _$GetUserResponseFromJson(json);

@override final  int id;
@override final  String email;
@override final  String? firstName;
@override final  String? lastName;
@override final  String? gender;
@override final  String? country;
@override final  String? dateOfBirth;
@override final  double? weightInKg;
@override final  double? heightInFt;
@override final  String? religion;
@override final  Preference? preference;

/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$GetUserResponseCopyWith<_GetUserResponse> get copyWith => __$GetUserResponseCopyWithImpl<_GetUserResponse>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$GetUserResponseToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _GetUserResponse&&(identical(other.id, id) || other.id == id)&&(identical(other.email, email) || other.email == email)&&(identical(other.firstName, firstName) || other.firstName == firstName)&&(identical(other.lastName, lastName) || other.lastName == lastName)&&(identical(other.gender, gender) || other.gender == gender)&&(identical(other.country, country) || other.country == country)&&(identical(other.dateOfBirth, dateOfBirth) || other.dateOfBirth == dateOfBirth)&&(identical(other.weightInKg, weightInKg) || other.weightInKg == weightInKg)&&(identical(other.heightInFt, heightInFt) || other.heightInFt == heightInFt)&&(identical(other.religion, religion) || other.religion == religion)&&(identical(other.preference, preference) || other.preference == preference));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,email,firstName,lastName,gender,country,dateOfBirth,weightInKg,heightInFt,religion,preference);

@override
String toString() {
  return 'GetUserResponse(id: $id, email: $email, firstName: $firstName, lastName: $lastName, gender: $gender, country: $country, dateOfBirth: $dateOfBirth, weightInKg: $weightInKg, heightInFt: $heightInFt, religion: $religion, preference: $preference)';
}


}

/// @nodoc
abstract mixin class _$GetUserResponseCopyWith<$Res> implements $GetUserResponseCopyWith<$Res> {
  factory _$GetUserResponseCopyWith(_GetUserResponse value, $Res Function(_GetUserResponse) _then) = __$GetUserResponseCopyWithImpl;
@override @useResult
$Res call({
 int id, String email, String? firstName, String? lastName, String? gender, String? country, String? dateOfBirth, double? weightInKg, double? heightInFt, String? religion, Preference? preference
});


@override $PreferenceCopyWith<$Res>? get preference;

}
/// @nodoc
class __$GetUserResponseCopyWithImpl<$Res>
    implements _$GetUserResponseCopyWith<$Res> {
  __$GetUserResponseCopyWithImpl(this._self, this._then);

  final _GetUserResponse _self;
  final $Res Function(_GetUserResponse) _then;

/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? email = null,Object? firstName = freezed,Object? lastName = freezed,Object? gender = freezed,Object? country = freezed,Object? dateOfBirth = freezed,Object? weightInKg = freezed,Object? heightInFt = freezed,Object? religion = freezed,Object? preference = freezed,}) {
  return _then(_GetUserResponse(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,firstName: freezed == firstName ? _self.firstName : firstName // ignore: cast_nullable_to_non_nullable
as String?,lastName: freezed == lastName ? _self.lastName : lastName // ignore: cast_nullable_to_non_nullable
as String?,gender: freezed == gender ? _self.gender : gender // ignore: cast_nullable_to_non_nullable
as String?,country: freezed == country ? _self.country : country // ignore: cast_nullable_to_non_nullable
as String?,dateOfBirth: freezed == dateOfBirth ? _self.dateOfBirth : dateOfBirth // ignore: cast_nullable_to_non_nullable
as String?,weightInKg: freezed == weightInKg ? _self.weightInKg : weightInKg // ignore: cast_nullable_to_non_nullable
as double?,heightInFt: freezed == heightInFt ? _self.heightInFt : heightInFt // ignore: cast_nullable_to_non_nullable
as double?,religion: freezed == religion ? _self.religion : religion // ignore: cast_nullable_to_non_nullable
as String?,preference: freezed == preference ? _self.preference : preference // ignore: cast_nullable_to_non_nullable
as Preference?,
  ));
}

/// Create a copy of GetUserResponse
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PreferenceCopyWith<$Res>? get preference {
    if (_self.preference == null) {
    return null;
  }

  return $PreferenceCopyWith<$Res>(_self.preference!, (value) {
    return _then(_self.copyWith(preference: value));
  });
}
}

// dart format on
