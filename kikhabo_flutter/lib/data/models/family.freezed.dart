// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'family.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$GetFamilyMembersDto {

 List<User> get members;
/// Create a copy of GetFamilyMembersDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$GetFamilyMembersDtoCopyWith<GetFamilyMembersDto> get copyWith => _$GetFamilyMembersDtoCopyWithImpl<GetFamilyMembersDto>(this as GetFamilyMembersDto, _$identity);

  /// Serializes this GetFamilyMembersDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is GetFamilyMembersDto&&const DeepCollectionEquality().equals(other.members, members));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(members));

@override
String toString() {
  return 'GetFamilyMembersDto(members: $members)';
}


}

/// @nodoc
abstract mixin class $GetFamilyMembersDtoCopyWith<$Res>  {
  factory $GetFamilyMembersDtoCopyWith(GetFamilyMembersDto value, $Res Function(GetFamilyMembersDto) _then) = _$GetFamilyMembersDtoCopyWithImpl;
@useResult
$Res call({
 List<User> members
});




}
/// @nodoc
class _$GetFamilyMembersDtoCopyWithImpl<$Res>
    implements $GetFamilyMembersDtoCopyWith<$Res> {
  _$GetFamilyMembersDtoCopyWithImpl(this._self, this._then);

  final GetFamilyMembersDto _self;
  final $Res Function(GetFamilyMembersDto) _then;

/// Create a copy of GetFamilyMembersDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? members = null,}) {
  return _then(_self.copyWith(
members: null == members ? _self.members : members // ignore: cast_nullable_to_non_nullable
as List<User>,
  ));
}

}


/// Adds pattern-matching-related methods to [GetFamilyMembersDto].
extension GetFamilyMembersDtoPatterns on GetFamilyMembersDto {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _GetFamilyMembersDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _GetFamilyMembersDto() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _GetFamilyMembersDto value)  $default,){
final _that = this;
switch (_that) {
case _GetFamilyMembersDto():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _GetFamilyMembersDto value)?  $default,){
final _that = this;
switch (_that) {
case _GetFamilyMembersDto() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( List<User> members)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _GetFamilyMembersDto() when $default != null:
return $default(_that.members);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( List<User> members)  $default,) {final _that = this;
switch (_that) {
case _GetFamilyMembersDto():
return $default(_that.members);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( List<User> members)?  $default,) {final _that = this;
switch (_that) {
case _GetFamilyMembersDto() when $default != null:
return $default(_that.members);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _GetFamilyMembersDto implements GetFamilyMembersDto {
  const _GetFamilyMembersDto({required final  List<User> members}): _members = members;
  factory _GetFamilyMembersDto.fromJson(Map<String, dynamic> json) => _$GetFamilyMembersDtoFromJson(json);

 final  List<User> _members;
@override List<User> get members {
  if (_members is EqualUnmodifiableListView) return _members;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_members);
}


/// Create a copy of GetFamilyMembersDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$GetFamilyMembersDtoCopyWith<_GetFamilyMembersDto> get copyWith => __$GetFamilyMembersDtoCopyWithImpl<_GetFamilyMembersDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$GetFamilyMembersDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _GetFamilyMembersDto&&const DeepCollectionEquality().equals(other._members, _members));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(_members));

@override
String toString() {
  return 'GetFamilyMembersDto(members: $members)';
}


}

/// @nodoc
abstract mixin class _$GetFamilyMembersDtoCopyWith<$Res> implements $GetFamilyMembersDtoCopyWith<$Res> {
  factory _$GetFamilyMembersDtoCopyWith(_GetFamilyMembersDto value, $Res Function(_GetFamilyMembersDto) _then) = __$GetFamilyMembersDtoCopyWithImpl;
@override @useResult
$Res call({
 List<User> members
});




}
/// @nodoc
class __$GetFamilyMembersDtoCopyWithImpl<$Res>
    implements _$GetFamilyMembersDtoCopyWith<$Res> {
  __$GetFamilyMembersDtoCopyWithImpl(this._self, this._then);

  final _GetFamilyMembersDto _self;
  final $Res Function(_GetFamilyMembersDto) _then;

/// Create a copy of GetFamilyMembersDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? members = null,}) {
  return _then(_GetFamilyMembersDto(
members: null == members ? _self._members : members // ignore: cast_nullable_to_non_nullable
as List<User>,
  ));
}


}


/// @nodoc
mixin _$AddFamilyMemberCommand {

 List<int> get familyMemberIds;
/// Create a copy of AddFamilyMemberCommand
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$AddFamilyMemberCommandCopyWith<AddFamilyMemberCommand> get copyWith => _$AddFamilyMemberCommandCopyWithImpl<AddFamilyMemberCommand>(this as AddFamilyMemberCommand, _$identity);

  /// Serializes this AddFamilyMemberCommand to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is AddFamilyMemberCommand&&const DeepCollectionEquality().equals(other.familyMemberIds, familyMemberIds));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(familyMemberIds));

@override
String toString() {
  return 'AddFamilyMemberCommand(familyMemberIds: $familyMemberIds)';
}


}

/// @nodoc
abstract mixin class $AddFamilyMemberCommandCopyWith<$Res>  {
  factory $AddFamilyMemberCommandCopyWith(AddFamilyMemberCommand value, $Res Function(AddFamilyMemberCommand) _then) = _$AddFamilyMemberCommandCopyWithImpl;
@useResult
$Res call({
 List<int> familyMemberIds
});




}
/// @nodoc
class _$AddFamilyMemberCommandCopyWithImpl<$Res>
    implements $AddFamilyMemberCommandCopyWith<$Res> {
  _$AddFamilyMemberCommandCopyWithImpl(this._self, this._then);

  final AddFamilyMemberCommand _self;
  final $Res Function(AddFamilyMemberCommand) _then;

/// Create a copy of AddFamilyMemberCommand
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? familyMemberIds = null,}) {
  return _then(_self.copyWith(
familyMemberIds: null == familyMemberIds ? _self.familyMemberIds : familyMemberIds // ignore: cast_nullable_to_non_nullable
as List<int>,
  ));
}

}


/// Adds pattern-matching-related methods to [AddFamilyMemberCommand].
extension AddFamilyMemberCommandPatterns on AddFamilyMemberCommand {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _AddFamilyMemberCommand value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _AddFamilyMemberCommand() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _AddFamilyMemberCommand value)  $default,){
final _that = this;
switch (_that) {
case _AddFamilyMemberCommand():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _AddFamilyMemberCommand value)?  $default,){
final _that = this;
switch (_that) {
case _AddFamilyMemberCommand() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( List<int> familyMemberIds)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _AddFamilyMemberCommand() when $default != null:
return $default(_that.familyMemberIds);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( List<int> familyMemberIds)  $default,) {final _that = this;
switch (_that) {
case _AddFamilyMemberCommand():
return $default(_that.familyMemberIds);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( List<int> familyMemberIds)?  $default,) {final _that = this;
switch (_that) {
case _AddFamilyMemberCommand() when $default != null:
return $default(_that.familyMemberIds);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _AddFamilyMemberCommand implements AddFamilyMemberCommand {
  const _AddFamilyMemberCommand({required final  List<int> familyMemberIds}): _familyMemberIds = familyMemberIds;
  factory _AddFamilyMemberCommand.fromJson(Map<String, dynamic> json) => _$AddFamilyMemberCommandFromJson(json);

 final  List<int> _familyMemberIds;
@override List<int> get familyMemberIds {
  if (_familyMemberIds is EqualUnmodifiableListView) return _familyMemberIds;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_familyMemberIds);
}


/// Create a copy of AddFamilyMemberCommand
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$AddFamilyMemberCommandCopyWith<_AddFamilyMemberCommand> get copyWith => __$AddFamilyMemberCommandCopyWithImpl<_AddFamilyMemberCommand>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$AddFamilyMemberCommandToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _AddFamilyMemberCommand&&const DeepCollectionEquality().equals(other._familyMemberIds, _familyMemberIds));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(_familyMemberIds));

@override
String toString() {
  return 'AddFamilyMemberCommand(familyMemberIds: $familyMemberIds)';
}


}

/// @nodoc
abstract mixin class _$AddFamilyMemberCommandCopyWith<$Res> implements $AddFamilyMemberCommandCopyWith<$Res> {
  factory _$AddFamilyMemberCommandCopyWith(_AddFamilyMemberCommand value, $Res Function(_AddFamilyMemberCommand) _then) = __$AddFamilyMemberCommandCopyWithImpl;
@override @useResult
$Res call({
 List<int> familyMemberIds
});




}
/// @nodoc
class __$AddFamilyMemberCommandCopyWithImpl<$Res>
    implements _$AddFamilyMemberCommandCopyWith<$Res> {
  __$AddFamilyMemberCommandCopyWithImpl(this._self, this._then);

  final _AddFamilyMemberCommand _self;
  final $Res Function(_AddFamilyMemberCommand) _then;

/// Create a copy of AddFamilyMemberCommand
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? familyMemberIds = null,}) {
  return _then(_AddFamilyMemberCommand(
familyMemberIds: null == familyMemberIds ? _self._familyMemberIds : familyMemberIds // ignore: cast_nullable_to_non_nullable
as List<int>,
  ));
}


}

// dart format on
