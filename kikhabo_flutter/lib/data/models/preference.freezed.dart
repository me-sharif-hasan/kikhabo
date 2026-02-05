// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'preference.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$Preference {

 int? get id; double? get spicyRating;@JsonKey(name: 'saltTasteRating') double? get saltRating;// Mapped from backend 'saltTasteRating'
 double? get budgetRating; bool? get hasDiabetics;@JsonKey(name: 'pregnant') bool? get isPregnant;// Mapped from backend 'pregnant'
 String? get specialNotes;
/// Create a copy of Preference
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$PreferenceCopyWith<Preference> get copyWith => _$PreferenceCopyWithImpl<Preference>(this as Preference, _$identity);

  /// Serializes this Preference to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is Preference&&(identical(other.id, id) || other.id == id)&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.saltRating, saltRating) || other.saltRating == saltRating)&&(identical(other.budgetRating, budgetRating) || other.budgetRating == budgetRating)&&(identical(other.hasDiabetics, hasDiabetics) || other.hasDiabetics == hasDiabetics)&&(identical(other.isPregnant, isPregnant) || other.isPregnant == isPregnant)&&(identical(other.specialNotes, specialNotes) || other.specialNotes == specialNotes));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,spicyRating,saltRating,budgetRating,hasDiabetics,isPregnant,specialNotes);

@override
String toString() {
  return 'Preference(id: $id, spicyRating: $spicyRating, saltRating: $saltRating, budgetRating: $budgetRating, hasDiabetics: $hasDiabetics, isPregnant: $isPregnant, specialNotes: $specialNotes)';
}


}

/// @nodoc
abstract mixin class $PreferenceCopyWith<$Res>  {
  factory $PreferenceCopyWith(Preference value, $Res Function(Preference) _then) = _$PreferenceCopyWithImpl;
@useResult
$Res call({
 int? id, double? spicyRating,@JsonKey(name: 'saltTasteRating') double? saltRating, double? budgetRating, bool? hasDiabetics,@JsonKey(name: 'pregnant') bool? isPregnant, String? specialNotes
});




}
/// @nodoc
class _$PreferenceCopyWithImpl<$Res>
    implements $PreferenceCopyWith<$Res> {
  _$PreferenceCopyWithImpl(this._self, this._then);

  final Preference _self;
  final $Res Function(Preference) _then;

/// Create a copy of Preference
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = freezed,Object? spicyRating = freezed,Object? saltRating = freezed,Object? budgetRating = freezed,Object? hasDiabetics = freezed,Object? isPregnant = freezed,Object? specialNotes = freezed,}) {
  return _then(_self.copyWith(
id: freezed == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int?,spicyRating: freezed == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double?,saltRating: freezed == saltRating ? _self.saltRating : saltRating // ignore: cast_nullable_to_non_nullable
as double?,budgetRating: freezed == budgetRating ? _self.budgetRating : budgetRating // ignore: cast_nullable_to_non_nullable
as double?,hasDiabetics: freezed == hasDiabetics ? _self.hasDiabetics : hasDiabetics // ignore: cast_nullable_to_non_nullable
as bool?,isPregnant: freezed == isPregnant ? _self.isPregnant : isPregnant // ignore: cast_nullable_to_non_nullable
as bool?,specialNotes: freezed == specialNotes ? _self.specialNotes : specialNotes // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}

}


/// Adds pattern-matching-related methods to [Preference].
extension PreferencePatterns on Preference {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _Preference value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _Preference() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _Preference value)  $default,){
final _that = this;
switch (_that) {
case _Preference():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _Preference value)?  $default,){
final _that = this;
switch (_that) {
case _Preference() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int? id,  double? spicyRating, @JsonKey(name: 'saltTasteRating')  double? saltRating,  double? budgetRating,  bool? hasDiabetics, @JsonKey(name: 'pregnant')  bool? isPregnant,  String? specialNotes)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _Preference() when $default != null:
return $default(_that.id,_that.spicyRating,_that.saltRating,_that.budgetRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int? id,  double? spicyRating, @JsonKey(name: 'saltTasteRating')  double? saltRating,  double? budgetRating,  bool? hasDiabetics, @JsonKey(name: 'pregnant')  bool? isPregnant,  String? specialNotes)  $default,) {final _that = this;
switch (_that) {
case _Preference():
return $default(_that.id,_that.spicyRating,_that.saltRating,_that.budgetRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int? id,  double? spicyRating, @JsonKey(name: 'saltTasteRating')  double? saltRating,  double? budgetRating,  bool? hasDiabetics, @JsonKey(name: 'pregnant')  bool? isPregnant,  String? specialNotes)?  $default,) {final _that = this;
switch (_that) {
case _Preference() when $default != null:
return $default(_that.id,_that.spicyRating,_that.saltRating,_that.budgetRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _Preference implements Preference {
  const _Preference({this.id, this.spicyRating, @JsonKey(name: 'saltTasteRating') this.saltRating, this.budgetRating, this.hasDiabetics, @JsonKey(name: 'pregnant') this.isPregnant, this.specialNotes});
  factory _Preference.fromJson(Map<String, dynamic> json) => _$PreferenceFromJson(json);

@override final  int? id;
@override final  double? spicyRating;
@override@JsonKey(name: 'saltTasteRating') final  double? saltRating;
// Mapped from backend 'saltTasteRating'
@override final  double? budgetRating;
@override final  bool? hasDiabetics;
@override@JsonKey(name: 'pregnant') final  bool? isPregnant;
// Mapped from backend 'pregnant'
@override final  String? specialNotes;

/// Create a copy of Preference
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$PreferenceCopyWith<_Preference> get copyWith => __$PreferenceCopyWithImpl<_Preference>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$PreferenceToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _Preference&&(identical(other.id, id) || other.id == id)&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.saltRating, saltRating) || other.saltRating == saltRating)&&(identical(other.budgetRating, budgetRating) || other.budgetRating == budgetRating)&&(identical(other.hasDiabetics, hasDiabetics) || other.hasDiabetics == hasDiabetics)&&(identical(other.isPregnant, isPregnant) || other.isPregnant == isPregnant)&&(identical(other.specialNotes, specialNotes) || other.specialNotes == specialNotes));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,spicyRating,saltRating,budgetRating,hasDiabetics,isPregnant,specialNotes);

@override
String toString() {
  return 'Preference(id: $id, spicyRating: $spicyRating, saltRating: $saltRating, budgetRating: $budgetRating, hasDiabetics: $hasDiabetics, isPregnant: $isPregnant, specialNotes: $specialNotes)';
}


}

/// @nodoc
abstract mixin class _$PreferenceCopyWith<$Res> implements $PreferenceCopyWith<$Res> {
  factory _$PreferenceCopyWith(_Preference value, $Res Function(_Preference) _then) = __$PreferenceCopyWithImpl;
@override @useResult
$Res call({
 int? id, double? spicyRating,@JsonKey(name: 'saltTasteRating') double? saltRating, double? budgetRating, bool? hasDiabetics,@JsonKey(name: 'pregnant') bool? isPregnant, String? specialNotes
});




}
/// @nodoc
class __$PreferenceCopyWithImpl<$Res>
    implements _$PreferenceCopyWith<$Res> {
  __$PreferenceCopyWithImpl(this._self, this._then);

  final _Preference _self;
  final $Res Function(_Preference) _then;

/// Create a copy of Preference
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = freezed,Object? spicyRating = freezed,Object? saltRating = freezed,Object? budgetRating = freezed,Object? hasDiabetics = freezed,Object? isPregnant = freezed,Object? specialNotes = freezed,}) {
  return _then(_Preference(
id: freezed == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int?,spicyRating: freezed == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double?,saltRating: freezed == saltRating ? _self.saltRating : saltRating // ignore: cast_nullable_to_non_nullable
as double?,budgetRating: freezed == budgetRating ? _self.budgetRating : budgetRating // ignore: cast_nullable_to_non_nullable
as double?,hasDiabetics: freezed == hasDiabetics ? _self.hasDiabetics : hasDiabetics // ignore: cast_nullable_to_non_nullable
as bool?,isPregnant: freezed == isPregnant ? _self.isPregnant : isPregnant // ignore: cast_nullable_to_non_nullable
as bool?,specialNotes: freezed == specialNotes ? _self.specialNotes : specialNotes // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}


}


/// @nodoc
mixin _$UpdatePreferenceCommand {

 double? get spicyRating; double? get budgetRating;@JsonKey(name: 'SaltTasteRating') double? get saltTasteRating;// Backend expects Capital 'SaltTasteRating'
 bool? get hasDiabetics; bool? get isPregnant; String? get specialNotes;
/// Create a copy of UpdatePreferenceCommand
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UpdatePreferenceCommandCopyWith<UpdatePreferenceCommand> get copyWith => _$UpdatePreferenceCommandCopyWithImpl<UpdatePreferenceCommand>(this as UpdatePreferenceCommand, _$identity);

  /// Serializes this UpdatePreferenceCommand to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is UpdatePreferenceCommand&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.budgetRating, budgetRating) || other.budgetRating == budgetRating)&&(identical(other.saltTasteRating, saltTasteRating) || other.saltTasteRating == saltTasteRating)&&(identical(other.hasDiabetics, hasDiabetics) || other.hasDiabetics == hasDiabetics)&&(identical(other.isPregnant, isPregnant) || other.isPregnant == isPregnant)&&(identical(other.specialNotes, specialNotes) || other.specialNotes == specialNotes));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,spicyRating,budgetRating,saltTasteRating,hasDiabetics,isPregnant,specialNotes);

@override
String toString() {
  return 'UpdatePreferenceCommand(spicyRating: $spicyRating, budgetRating: $budgetRating, saltTasteRating: $saltTasteRating, hasDiabetics: $hasDiabetics, isPregnant: $isPregnant, specialNotes: $specialNotes)';
}


}

/// @nodoc
abstract mixin class $UpdatePreferenceCommandCopyWith<$Res>  {
  factory $UpdatePreferenceCommandCopyWith(UpdatePreferenceCommand value, $Res Function(UpdatePreferenceCommand) _then) = _$UpdatePreferenceCommandCopyWithImpl;
@useResult
$Res call({
 double? spicyRating, double? budgetRating,@JsonKey(name: 'SaltTasteRating') double? saltTasteRating, bool? hasDiabetics, bool? isPregnant, String? specialNotes
});




}
/// @nodoc
class _$UpdatePreferenceCommandCopyWithImpl<$Res>
    implements $UpdatePreferenceCommandCopyWith<$Res> {
  _$UpdatePreferenceCommandCopyWithImpl(this._self, this._then);

  final UpdatePreferenceCommand _self;
  final $Res Function(UpdatePreferenceCommand) _then;

/// Create a copy of UpdatePreferenceCommand
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? spicyRating = freezed,Object? budgetRating = freezed,Object? saltTasteRating = freezed,Object? hasDiabetics = freezed,Object? isPregnant = freezed,Object? specialNotes = freezed,}) {
  return _then(_self.copyWith(
spicyRating: freezed == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double?,budgetRating: freezed == budgetRating ? _self.budgetRating : budgetRating // ignore: cast_nullable_to_non_nullable
as double?,saltTasteRating: freezed == saltTasteRating ? _self.saltTasteRating : saltTasteRating // ignore: cast_nullable_to_non_nullable
as double?,hasDiabetics: freezed == hasDiabetics ? _self.hasDiabetics : hasDiabetics // ignore: cast_nullable_to_non_nullable
as bool?,isPregnant: freezed == isPregnant ? _self.isPregnant : isPregnant // ignore: cast_nullable_to_non_nullable
as bool?,specialNotes: freezed == specialNotes ? _self.specialNotes : specialNotes // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}

}


/// Adds pattern-matching-related methods to [UpdatePreferenceCommand].
extension UpdatePreferenceCommandPatterns on UpdatePreferenceCommand {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _UpdatePreferenceCommand value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _UpdatePreferenceCommand() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _UpdatePreferenceCommand value)  $default,){
final _that = this;
switch (_that) {
case _UpdatePreferenceCommand():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _UpdatePreferenceCommand value)?  $default,){
final _that = this;
switch (_that) {
case _UpdatePreferenceCommand() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( double? spicyRating,  double? budgetRating, @JsonKey(name: 'SaltTasteRating')  double? saltTasteRating,  bool? hasDiabetics,  bool? isPregnant,  String? specialNotes)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _UpdatePreferenceCommand() when $default != null:
return $default(_that.spicyRating,_that.budgetRating,_that.saltTasteRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( double? spicyRating,  double? budgetRating, @JsonKey(name: 'SaltTasteRating')  double? saltTasteRating,  bool? hasDiabetics,  bool? isPregnant,  String? specialNotes)  $default,) {final _that = this;
switch (_that) {
case _UpdatePreferenceCommand():
return $default(_that.spicyRating,_that.budgetRating,_that.saltTasteRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( double? spicyRating,  double? budgetRating, @JsonKey(name: 'SaltTasteRating')  double? saltTasteRating,  bool? hasDiabetics,  bool? isPregnant,  String? specialNotes)?  $default,) {final _that = this;
switch (_that) {
case _UpdatePreferenceCommand() when $default != null:
return $default(_that.spicyRating,_that.budgetRating,_that.saltTasteRating,_that.hasDiabetics,_that.isPregnant,_that.specialNotes);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _UpdatePreferenceCommand implements UpdatePreferenceCommand {
  const _UpdatePreferenceCommand({this.spicyRating, this.budgetRating, @JsonKey(name: 'SaltTasteRating') this.saltTasteRating, this.hasDiabetics, this.isPregnant, this.specialNotes});
  factory _UpdatePreferenceCommand.fromJson(Map<String, dynamic> json) => _$UpdatePreferenceCommandFromJson(json);

@override final  double? spicyRating;
@override final  double? budgetRating;
@override@JsonKey(name: 'SaltTasteRating') final  double? saltTasteRating;
// Backend expects Capital 'SaltTasteRating'
@override final  bool? hasDiabetics;
@override final  bool? isPregnant;
@override final  String? specialNotes;

/// Create a copy of UpdatePreferenceCommand
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UpdatePreferenceCommandCopyWith<_UpdatePreferenceCommand> get copyWith => __$UpdatePreferenceCommandCopyWithImpl<_UpdatePreferenceCommand>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UpdatePreferenceCommandToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _UpdatePreferenceCommand&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.budgetRating, budgetRating) || other.budgetRating == budgetRating)&&(identical(other.saltTasteRating, saltTasteRating) || other.saltTasteRating == saltTasteRating)&&(identical(other.hasDiabetics, hasDiabetics) || other.hasDiabetics == hasDiabetics)&&(identical(other.isPregnant, isPregnant) || other.isPregnant == isPregnant)&&(identical(other.specialNotes, specialNotes) || other.specialNotes == specialNotes));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,spicyRating,budgetRating,saltTasteRating,hasDiabetics,isPregnant,specialNotes);

@override
String toString() {
  return 'UpdatePreferenceCommand(spicyRating: $spicyRating, budgetRating: $budgetRating, saltTasteRating: $saltTasteRating, hasDiabetics: $hasDiabetics, isPregnant: $isPregnant, specialNotes: $specialNotes)';
}


}

/// @nodoc
abstract mixin class _$UpdatePreferenceCommandCopyWith<$Res> implements $UpdatePreferenceCommandCopyWith<$Res> {
  factory _$UpdatePreferenceCommandCopyWith(_UpdatePreferenceCommand value, $Res Function(_UpdatePreferenceCommand) _then) = __$UpdatePreferenceCommandCopyWithImpl;
@override @useResult
$Res call({
 double? spicyRating, double? budgetRating,@JsonKey(name: 'SaltTasteRating') double? saltTasteRating, bool? hasDiabetics, bool? isPregnant, String? specialNotes
});




}
/// @nodoc
class __$UpdatePreferenceCommandCopyWithImpl<$Res>
    implements _$UpdatePreferenceCommandCopyWith<$Res> {
  __$UpdatePreferenceCommandCopyWithImpl(this._self, this._then);

  final _UpdatePreferenceCommand _self;
  final $Res Function(_UpdatePreferenceCommand) _then;

/// Create a copy of UpdatePreferenceCommand
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? spicyRating = freezed,Object? budgetRating = freezed,Object? saltTasteRating = freezed,Object? hasDiabetics = freezed,Object? isPregnant = freezed,Object? specialNotes = freezed,}) {
  return _then(_UpdatePreferenceCommand(
spicyRating: freezed == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double?,budgetRating: freezed == budgetRating ? _self.budgetRating : budgetRating // ignore: cast_nullable_to_non_nullable
as double?,saltTasteRating: freezed == saltTasteRating ? _self.saltTasteRating : saltTasteRating // ignore: cast_nullable_to_non_nullable
as double?,hasDiabetics: freezed == hasDiabetics ? _self.hasDiabetics : hasDiabetics // ignore: cast_nullable_to_non_nullable
as bool?,isPregnant: freezed == isPregnant ? _self.isPregnant : isPregnant // ignore: cast_nullable_to_non_nullable
as bool?,specialNotes: freezed == specialNotes ? _self.specialNotes : specialNotes // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}


}

// dart format on
