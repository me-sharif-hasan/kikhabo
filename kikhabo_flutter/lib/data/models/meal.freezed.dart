// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'meal.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$Grocery {

 String get name; String get priceRatingOutOf10; String get amountInGm;
/// Create a copy of Grocery
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$GroceryCopyWith<Grocery> get copyWith => _$GroceryCopyWithImpl<Grocery>(this as Grocery, _$identity);

  /// Serializes this Grocery to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is Grocery&&(identical(other.name, name) || other.name == name)&&(identical(other.priceRatingOutOf10, priceRatingOutOf10) || other.priceRatingOutOf10 == priceRatingOutOf10)&&(identical(other.amountInGm, amountInGm) || other.amountInGm == amountInGm));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,priceRatingOutOf10,amountInGm);

@override
String toString() {
  return 'Grocery(name: $name, priceRatingOutOf10: $priceRatingOutOf10, amountInGm: $amountInGm)';
}


}

/// @nodoc
abstract mixin class $GroceryCopyWith<$Res>  {
  factory $GroceryCopyWith(Grocery value, $Res Function(Grocery) _then) = _$GroceryCopyWithImpl;
@useResult
$Res call({
 String name, String priceRatingOutOf10, String amountInGm
});




}
/// @nodoc
class _$GroceryCopyWithImpl<$Res>
    implements $GroceryCopyWith<$Res> {
  _$GroceryCopyWithImpl(this._self, this._then);

  final Grocery _self;
  final $Res Function(Grocery) _then;

/// Create a copy of Grocery
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? name = null,Object? priceRatingOutOf10 = null,Object? amountInGm = null,}) {
  return _then(_self.copyWith(
name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,priceRatingOutOf10: null == priceRatingOutOf10 ? _self.priceRatingOutOf10 : priceRatingOutOf10 // ignore: cast_nullable_to_non_nullable
as String,amountInGm: null == amountInGm ? _self.amountInGm : amountInGm // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [Grocery].
extension GroceryPatterns on Grocery {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _Grocery value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _Grocery() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _Grocery value)  $default,){
final _that = this;
switch (_that) {
case _Grocery():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _Grocery value)?  $default,){
final _that = this;
switch (_that) {
case _Grocery() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String name,  String priceRatingOutOf10,  String amountInGm)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _Grocery() when $default != null:
return $default(_that.name,_that.priceRatingOutOf10,_that.amountInGm);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String name,  String priceRatingOutOf10,  String amountInGm)  $default,) {final _that = this;
switch (_that) {
case _Grocery():
return $default(_that.name,_that.priceRatingOutOf10,_that.amountInGm);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String name,  String priceRatingOutOf10,  String amountInGm)?  $default,) {final _that = this;
switch (_that) {
case _Grocery() when $default != null:
return $default(_that.name,_that.priceRatingOutOf10,_that.amountInGm);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _Grocery implements Grocery {
  const _Grocery({required this.name, required this.priceRatingOutOf10, required this.amountInGm});
  factory _Grocery.fromJson(Map<String, dynamic> json) => _$GroceryFromJson(json);

@override final  String name;
@override final  String priceRatingOutOf10;
@override final  String amountInGm;

/// Create a copy of Grocery
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$GroceryCopyWith<_Grocery> get copyWith => __$GroceryCopyWithImpl<_Grocery>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$GroceryToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _Grocery&&(identical(other.name, name) || other.name == name)&&(identical(other.priceRatingOutOf10, priceRatingOutOf10) || other.priceRatingOutOf10 == priceRatingOutOf10)&&(identical(other.amountInGm, amountInGm) || other.amountInGm == amountInGm));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,priceRatingOutOf10,amountInGm);

@override
String toString() {
  return 'Grocery(name: $name, priceRatingOutOf10: $priceRatingOutOf10, amountInGm: $amountInGm)';
}


}

/// @nodoc
abstract mixin class _$GroceryCopyWith<$Res> implements $GroceryCopyWith<$Res> {
  factory _$GroceryCopyWith(_Grocery value, $Res Function(_Grocery) _then) = __$GroceryCopyWithImpl;
@override @useResult
$Res call({
 String name, String priceRatingOutOf10, String amountInGm
});




}
/// @nodoc
class __$GroceryCopyWithImpl<$Res>
    implements _$GroceryCopyWith<$Res> {
  __$GroceryCopyWithImpl(this._self, this._then);

  final _Grocery _self;
  final $Res Function(_Grocery) _then;

/// Create a copy of Grocery
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? name = null,Object? priceRatingOutOf10 = null,Object? amountInGm = null,}) {
  return _then(_Grocery(
name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,priceRatingOutOf10: null == priceRatingOutOf10 ? _self.priceRatingOutOf10 : priceRatingOutOf10 // ignore: cast_nullable_to_non_nullable
as String,amountInGm: null == amountInGm ? _self.amountInGm : amountInGm // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}


/// @nodoc
mixin _$Meal {

 int? get id; String get mealName; dynamic get totalEnergy;// Changed to dynamic to handle int or String from API
 String? get ingredients;// Changed from List<String>? to String? to match API
 List<Grocery>? get groceries;// From meal planning API
 List<String>? get groceryNames;// From meal history API
 String? get note; double? get rating;// 1-5 rating
 String? get mealStatus;// TAKEN, SKIPPED, PLANNED
 String? get userNote;// User's feedback/note
 int? get timestamp;
/// Create a copy of Meal
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$MealCopyWith<Meal> get copyWith => _$MealCopyWithImpl<Meal>(this as Meal, _$identity);

  /// Serializes this Meal to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is Meal&&(identical(other.id, id) || other.id == id)&&(identical(other.mealName, mealName) || other.mealName == mealName)&&const DeepCollectionEquality().equals(other.totalEnergy, totalEnergy)&&(identical(other.ingredients, ingredients) || other.ingredients == ingredients)&&const DeepCollectionEquality().equals(other.groceries, groceries)&&const DeepCollectionEquality().equals(other.groceryNames, groceryNames)&&(identical(other.note, note) || other.note == note)&&(identical(other.rating, rating) || other.rating == rating)&&(identical(other.mealStatus, mealStatus) || other.mealStatus == mealStatus)&&(identical(other.userNote, userNote) || other.userNote == userNote)&&(identical(other.timestamp, timestamp) || other.timestamp == timestamp));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,mealName,const DeepCollectionEquality().hash(totalEnergy),ingredients,const DeepCollectionEquality().hash(groceries),const DeepCollectionEquality().hash(groceryNames),note,rating,mealStatus,userNote,timestamp);

@override
String toString() {
  return 'Meal(id: $id, mealName: $mealName, totalEnergy: $totalEnergy, ingredients: $ingredients, groceries: $groceries, groceryNames: $groceryNames, note: $note, rating: $rating, mealStatus: $mealStatus, userNote: $userNote, timestamp: $timestamp)';
}


}

/// @nodoc
abstract mixin class $MealCopyWith<$Res>  {
  factory $MealCopyWith(Meal value, $Res Function(Meal) _then) = _$MealCopyWithImpl;
@useResult
$Res call({
 int? id, String mealName, dynamic totalEnergy, String? ingredients, List<Grocery>? groceries, List<String>? groceryNames, String? note, double? rating, String? mealStatus, String? userNote, int? timestamp
});




}
/// @nodoc
class _$MealCopyWithImpl<$Res>
    implements $MealCopyWith<$Res> {
  _$MealCopyWithImpl(this._self, this._then);

  final Meal _self;
  final $Res Function(Meal) _then;

/// Create a copy of Meal
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = freezed,Object? mealName = null,Object? totalEnergy = freezed,Object? ingredients = freezed,Object? groceries = freezed,Object? groceryNames = freezed,Object? note = freezed,Object? rating = freezed,Object? mealStatus = freezed,Object? userNote = freezed,Object? timestamp = freezed,}) {
  return _then(_self.copyWith(
id: freezed == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int?,mealName: null == mealName ? _self.mealName : mealName // ignore: cast_nullable_to_non_nullable
as String,totalEnergy: freezed == totalEnergy ? _self.totalEnergy : totalEnergy // ignore: cast_nullable_to_non_nullable
as dynamic,ingredients: freezed == ingredients ? _self.ingredients : ingredients // ignore: cast_nullable_to_non_nullable
as String?,groceries: freezed == groceries ? _self.groceries : groceries // ignore: cast_nullable_to_non_nullable
as List<Grocery>?,groceryNames: freezed == groceryNames ? _self.groceryNames : groceryNames // ignore: cast_nullable_to_non_nullable
as List<String>?,note: freezed == note ? _self.note : note // ignore: cast_nullable_to_non_nullable
as String?,rating: freezed == rating ? _self.rating : rating // ignore: cast_nullable_to_non_nullable
as double?,mealStatus: freezed == mealStatus ? _self.mealStatus : mealStatus // ignore: cast_nullable_to_non_nullable
as String?,userNote: freezed == userNote ? _self.userNote : userNote // ignore: cast_nullable_to_non_nullable
as String?,timestamp: freezed == timestamp ? _self.timestamp : timestamp // ignore: cast_nullable_to_non_nullable
as int?,
  ));
}

}


/// Adds pattern-matching-related methods to [Meal].
extension MealPatterns on Meal {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _Meal value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _Meal() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _Meal value)  $default,){
final _that = this;
switch (_that) {
case _Meal():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _Meal value)?  $default,){
final _that = this;
switch (_that) {
case _Meal() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int? id,  String mealName,  dynamic totalEnergy,  String? ingredients,  List<Grocery>? groceries,  List<String>? groceryNames,  String? note,  double? rating,  String? mealStatus,  String? userNote,  int? timestamp)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _Meal() when $default != null:
return $default(_that.id,_that.mealName,_that.totalEnergy,_that.ingredients,_that.groceries,_that.groceryNames,_that.note,_that.rating,_that.mealStatus,_that.userNote,_that.timestamp);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int? id,  String mealName,  dynamic totalEnergy,  String? ingredients,  List<Grocery>? groceries,  List<String>? groceryNames,  String? note,  double? rating,  String? mealStatus,  String? userNote,  int? timestamp)  $default,) {final _that = this;
switch (_that) {
case _Meal():
return $default(_that.id,_that.mealName,_that.totalEnergy,_that.ingredients,_that.groceries,_that.groceryNames,_that.note,_that.rating,_that.mealStatus,_that.userNote,_that.timestamp);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int? id,  String mealName,  dynamic totalEnergy,  String? ingredients,  List<Grocery>? groceries,  List<String>? groceryNames,  String? note,  double? rating,  String? mealStatus,  String? userNote,  int? timestamp)?  $default,) {final _that = this;
switch (_that) {
case _Meal() when $default != null:
return $default(_that.id,_that.mealName,_that.totalEnergy,_that.ingredients,_that.groceries,_that.groceryNames,_that.note,_that.rating,_that.mealStatus,_that.userNote,_that.timestamp);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _Meal implements Meal {
  const _Meal({this.id, required this.mealName, required this.totalEnergy, this.ingredients, final  List<Grocery>? groceries, final  List<String>? groceryNames, this.note, this.rating, this.mealStatus, this.userNote, this.timestamp}): _groceries = groceries,_groceryNames = groceryNames;
  factory _Meal.fromJson(Map<String, dynamic> json) => _$MealFromJson(json);

@override final  int? id;
@override final  String mealName;
@override final  dynamic totalEnergy;
// Changed to dynamic to handle int or String from API
@override final  String? ingredients;
// Changed from List<String>? to String? to match API
 final  List<Grocery>? _groceries;
// Changed from List<String>? to String? to match API
@override List<Grocery>? get groceries {
  final value = _groceries;
  if (value == null) return null;
  if (_groceries is EqualUnmodifiableListView) return _groceries;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(value);
}

// From meal planning API
 final  List<String>? _groceryNames;
// From meal planning API
@override List<String>? get groceryNames {
  final value = _groceryNames;
  if (value == null) return null;
  if (_groceryNames is EqualUnmodifiableListView) return _groceryNames;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(value);
}

// From meal history API
@override final  String? note;
@override final  double? rating;
// 1-5 rating
@override final  String? mealStatus;
// TAKEN, SKIPPED, PLANNED
@override final  String? userNote;
// User's feedback/note
@override final  int? timestamp;

/// Create a copy of Meal
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$MealCopyWith<_Meal> get copyWith => __$MealCopyWithImpl<_Meal>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$MealToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _Meal&&(identical(other.id, id) || other.id == id)&&(identical(other.mealName, mealName) || other.mealName == mealName)&&const DeepCollectionEquality().equals(other.totalEnergy, totalEnergy)&&(identical(other.ingredients, ingredients) || other.ingredients == ingredients)&&const DeepCollectionEquality().equals(other._groceries, _groceries)&&const DeepCollectionEquality().equals(other._groceryNames, _groceryNames)&&(identical(other.note, note) || other.note == note)&&(identical(other.rating, rating) || other.rating == rating)&&(identical(other.mealStatus, mealStatus) || other.mealStatus == mealStatus)&&(identical(other.userNote, userNote) || other.userNote == userNote)&&(identical(other.timestamp, timestamp) || other.timestamp == timestamp));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,mealName,const DeepCollectionEquality().hash(totalEnergy),ingredients,const DeepCollectionEquality().hash(_groceries),const DeepCollectionEquality().hash(_groceryNames),note,rating,mealStatus,userNote,timestamp);

@override
String toString() {
  return 'Meal(id: $id, mealName: $mealName, totalEnergy: $totalEnergy, ingredients: $ingredients, groceries: $groceries, groceryNames: $groceryNames, note: $note, rating: $rating, mealStatus: $mealStatus, userNote: $userNote, timestamp: $timestamp)';
}


}

/// @nodoc
abstract mixin class _$MealCopyWith<$Res> implements $MealCopyWith<$Res> {
  factory _$MealCopyWith(_Meal value, $Res Function(_Meal) _then) = __$MealCopyWithImpl;
@override @useResult
$Res call({
 int? id, String mealName, dynamic totalEnergy, String? ingredients, List<Grocery>? groceries, List<String>? groceryNames, String? note, double? rating, String? mealStatus, String? userNote, int? timestamp
});




}
/// @nodoc
class __$MealCopyWithImpl<$Res>
    implements _$MealCopyWith<$Res> {
  __$MealCopyWithImpl(this._self, this._then);

  final _Meal _self;
  final $Res Function(_Meal) _then;

/// Create a copy of Meal
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = freezed,Object? mealName = null,Object? totalEnergy = freezed,Object? ingredients = freezed,Object? groceries = freezed,Object? groceryNames = freezed,Object? note = freezed,Object? rating = freezed,Object? mealStatus = freezed,Object? userNote = freezed,Object? timestamp = freezed,}) {
  return _then(_Meal(
id: freezed == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int?,mealName: null == mealName ? _self.mealName : mealName // ignore: cast_nullable_to_non_nullable
as String,totalEnergy: freezed == totalEnergy ? _self.totalEnergy : totalEnergy // ignore: cast_nullable_to_non_nullable
as dynamic,ingredients: freezed == ingredients ? _self.ingredients : ingredients // ignore: cast_nullable_to_non_nullable
as String?,groceries: freezed == groceries ? _self._groceries : groceries // ignore: cast_nullable_to_non_nullable
as List<Grocery>?,groceryNames: freezed == groceryNames ? _self._groceryNames : groceryNames // ignore: cast_nullable_to_non_nullable
as List<String>?,note: freezed == note ? _self.note : note // ignore: cast_nullable_to_non_nullable
as String?,rating: freezed == rating ? _self.rating : rating // ignore: cast_nullable_to_non_nullable
as double?,mealStatus: freezed == mealStatus ? _self.mealStatus : mealStatus // ignore: cast_nullable_to_non_nullable
as String?,userNote: freezed == userNote ? _self.userNote : userNote // ignore: cast_nullable_to_non_nullable
as String?,timestamp: freezed == timestamp ? _self.timestamp : timestamp // ignore: cast_nullable_to_non_nullable
as int?,
  ));
}


}


/// @nodoc
mixin _$MealPreferenceDto {

 double get spicyRating; double get saltRating; int get dayCount; double get priceRating; int get totalMealCount; int get mealPerDay; List<int> get agesOfTheMembers;
/// Create a copy of MealPreferenceDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$MealPreferenceDtoCopyWith<MealPreferenceDto> get copyWith => _$MealPreferenceDtoCopyWithImpl<MealPreferenceDto>(this as MealPreferenceDto, _$identity);

  /// Serializes this MealPreferenceDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is MealPreferenceDto&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.saltRating, saltRating) || other.saltRating == saltRating)&&(identical(other.dayCount, dayCount) || other.dayCount == dayCount)&&(identical(other.priceRating, priceRating) || other.priceRating == priceRating)&&(identical(other.totalMealCount, totalMealCount) || other.totalMealCount == totalMealCount)&&(identical(other.mealPerDay, mealPerDay) || other.mealPerDay == mealPerDay)&&const DeepCollectionEquality().equals(other.agesOfTheMembers, agesOfTheMembers));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,spicyRating,saltRating,dayCount,priceRating,totalMealCount,mealPerDay,const DeepCollectionEquality().hash(agesOfTheMembers));

@override
String toString() {
  return 'MealPreferenceDto(spicyRating: $spicyRating, saltRating: $saltRating, dayCount: $dayCount, priceRating: $priceRating, totalMealCount: $totalMealCount, mealPerDay: $mealPerDay, agesOfTheMembers: $agesOfTheMembers)';
}


}

/// @nodoc
abstract mixin class $MealPreferenceDtoCopyWith<$Res>  {
  factory $MealPreferenceDtoCopyWith(MealPreferenceDto value, $Res Function(MealPreferenceDto) _then) = _$MealPreferenceDtoCopyWithImpl;
@useResult
$Res call({
 double spicyRating, double saltRating, int dayCount, double priceRating, int totalMealCount, int mealPerDay, List<int> agesOfTheMembers
});




}
/// @nodoc
class _$MealPreferenceDtoCopyWithImpl<$Res>
    implements $MealPreferenceDtoCopyWith<$Res> {
  _$MealPreferenceDtoCopyWithImpl(this._self, this._then);

  final MealPreferenceDto _self;
  final $Res Function(MealPreferenceDto) _then;

/// Create a copy of MealPreferenceDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? spicyRating = null,Object? saltRating = null,Object? dayCount = null,Object? priceRating = null,Object? totalMealCount = null,Object? mealPerDay = null,Object? agesOfTheMembers = null,}) {
  return _then(_self.copyWith(
spicyRating: null == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double,saltRating: null == saltRating ? _self.saltRating : saltRating // ignore: cast_nullable_to_non_nullable
as double,dayCount: null == dayCount ? _self.dayCount : dayCount // ignore: cast_nullable_to_non_nullable
as int,priceRating: null == priceRating ? _self.priceRating : priceRating // ignore: cast_nullable_to_non_nullable
as double,totalMealCount: null == totalMealCount ? _self.totalMealCount : totalMealCount // ignore: cast_nullable_to_non_nullable
as int,mealPerDay: null == mealPerDay ? _self.mealPerDay : mealPerDay // ignore: cast_nullable_to_non_nullable
as int,agesOfTheMembers: null == agesOfTheMembers ? _self.agesOfTheMembers : agesOfTheMembers // ignore: cast_nullable_to_non_nullable
as List<int>,
  ));
}

}


/// Adds pattern-matching-related methods to [MealPreferenceDto].
extension MealPreferenceDtoPatterns on MealPreferenceDto {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _MealPreferenceDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _MealPreferenceDto() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _MealPreferenceDto value)  $default,){
final _that = this;
switch (_that) {
case _MealPreferenceDto():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _MealPreferenceDto value)?  $default,){
final _that = this;
switch (_that) {
case _MealPreferenceDto() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( double spicyRating,  double saltRating,  int dayCount,  double priceRating,  int totalMealCount,  int mealPerDay,  List<int> agesOfTheMembers)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _MealPreferenceDto() when $default != null:
return $default(_that.spicyRating,_that.saltRating,_that.dayCount,_that.priceRating,_that.totalMealCount,_that.mealPerDay,_that.agesOfTheMembers);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( double spicyRating,  double saltRating,  int dayCount,  double priceRating,  int totalMealCount,  int mealPerDay,  List<int> agesOfTheMembers)  $default,) {final _that = this;
switch (_that) {
case _MealPreferenceDto():
return $default(_that.spicyRating,_that.saltRating,_that.dayCount,_that.priceRating,_that.totalMealCount,_that.mealPerDay,_that.agesOfTheMembers);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( double spicyRating,  double saltRating,  int dayCount,  double priceRating,  int totalMealCount,  int mealPerDay,  List<int> agesOfTheMembers)?  $default,) {final _that = this;
switch (_that) {
case _MealPreferenceDto() when $default != null:
return $default(_that.spicyRating,_that.saltRating,_that.dayCount,_that.priceRating,_that.totalMealCount,_that.mealPerDay,_that.agesOfTheMembers);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _MealPreferenceDto implements MealPreferenceDto {
  const _MealPreferenceDto({required this.spicyRating, required this.saltRating, required this.dayCount, required this.priceRating, required this.totalMealCount, required this.mealPerDay, required final  List<int> agesOfTheMembers}): _agesOfTheMembers = agesOfTheMembers;
  factory _MealPreferenceDto.fromJson(Map<String, dynamic> json) => _$MealPreferenceDtoFromJson(json);

@override final  double spicyRating;
@override final  double saltRating;
@override final  int dayCount;
@override final  double priceRating;
@override final  int totalMealCount;
@override final  int mealPerDay;
 final  List<int> _agesOfTheMembers;
@override List<int> get agesOfTheMembers {
  if (_agesOfTheMembers is EqualUnmodifiableListView) return _agesOfTheMembers;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_agesOfTheMembers);
}


/// Create a copy of MealPreferenceDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$MealPreferenceDtoCopyWith<_MealPreferenceDto> get copyWith => __$MealPreferenceDtoCopyWithImpl<_MealPreferenceDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$MealPreferenceDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _MealPreferenceDto&&(identical(other.spicyRating, spicyRating) || other.spicyRating == spicyRating)&&(identical(other.saltRating, saltRating) || other.saltRating == saltRating)&&(identical(other.dayCount, dayCount) || other.dayCount == dayCount)&&(identical(other.priceRating, priceRating) || other.priceRating == priceRating)&&(identical(other.totalMealCount, totalMealCount) || other.totalMealCount == totalMealCount)&&(identical(other.mealPerDay, mealPerDay) || other.mealPerDay == mealPerDay)&&const DeepCollectionEquality().equals(other._agesOfTheMembers, _agesOfTheMembers));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,spicyRating,saltRating,dayCount,priceRating,totalMealCount,mealPerDay,const DeepCollectionEquality().hash(_agesOfTheMembers));

@override
String toString() {
  return 'MealPreferenceDto(spicyRating: $spicyRating, saltRating: $saltRating, dayCount: $dayCount, priceRating: $priceRating, totalMealCount: $totalMealCount, mealPerDay: $mealPerDay, agesOfTheMembers: $agesOfTheMembers)';
}


}

/// @nodoc
abstract mixin class _$MealPreferenceDtoCopyWith<$Res> implements $MealPreferenceDtoCopyWith<$Res> {
  factory _$MealPreferenceDtoCopyWith(_MealPreferenceDto value, $Res Function(_MealPreferenceDto) _then) = __$MealPreferenceDtoCopyWithImpl;
@override @useResult
$Res call({
 double spicyRating, double saltRating, int dayCount, double priceRating, int totalMealCount, int mealPerDay, List<int> agesOfTheMembers
});




}
/// @nodoc
class __$MealPreferenceDtoCopyWithImpl<$Res>
    implements _$MealPreferenceDtoCopyWith<$Res> {
  __$MealPreferenceDtoCopyWithImpl(this._self, this._then);

  final _MealPreferenceDto _self;
  final $Res Function(_MealPreferenceDto) _then;

/// Create a copy of MealPreferenceDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? spicyRating = null,Object? saltRating = null,Object? dayCount = null,Object? priceRating = null,Object? totalMealCount = null,Object? mealPerDay = null,Object? agesOfTheMembers = null,}) {
  return _then(_MealPreferenceDto(
spicyRating: null == spicyRating ? _self.spicyRating : spicyRating // ignore: cast_nullable_to_non_nullable
as double,saltRating: null == saltRating ? _self.saltRating : saltRating // ignore: cast_nullable_to_non_nullable
as double,dayCount: null == dayCount ? _self.dayCount : dayCount // ignore: cast_nullable_to_non_nullable
as int,priceRating: null == priceRating ? _self.priceRating : priceRating // ignore: cast_nullable_to_non_nullable
as double,totalMealCount: null == totalMealCount ? _self.totalMealCount : totalMealCount // ignore: cast_nullable_to_non_nullable
as int,mealPerDay: null == mealPerDay ? _self.mealPerDay : mealPerDay // ignore: cast_nullable_to_non_nullable
as int,agesOfTheMembers: null == agesOfTheMembers ? _self._agesOfTheMembers : agesOfTheMembers // ignore: cast_nullable_to_non_nullable
as List<int>,
  ));
}


}


/// @nodoc
mixin _$MealRatingStatusDto {

 int get id; int get rating; String get userNote; String get mealStatus;
/// Create a copy of MealRatingStatusDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$MealRatingStatusDtoCopyWith<MealRatingStatusDto> get copyWith => _$MealRatingStatusDtoCopyWithImpl<MealRatingStatusDto>(this as MealRatingStatusDto, _$identity);

  /// Serializes this MealRatingStatusDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is MealRatingStatusDto&&(identical(other.id, id) || other.id == id)&&(identical(other.rating, rating) || other.rating == rating)&&(identical(other.userNote, userNote) || other.userNote == userNote)&&(identical(other.mealStatus, mealStatus) || other.mealStatus == mealStatus));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,rating,userNote,mealStatus);

@override
String toString() {
  return 'MealRatingStatusDto(id: $id, rating: $rating, userNote: $userNote, mealStatus: $mealStatus)';
}


}

/// @nodoc
abstract mixin class $MealRatingStatusDtoCopyWith<$Res>  {
  factory $MealRatingStatusDtoCopyWith(MealRatingStatusDto value, $Res Function(MealRatingStatusDto) _then) = _$MealRatingStatusDtoCopyWithImpl;
@useResult
$Res call({
 int id, int rating, String userNote, String mealStatus
});




}
/// @nodoc
class _$MealRatingStatusDtoCopyWithImpl<$Res>
    implements $MealRatingStatusDtoCopyWith<$Res> {
  _$MealRatingStatusDtoCopyWithImpl(this._self, this._then);

  final MealRatingStatusDto _self;
  final $Res Function(MealRatingStatusDto) _then;

/// Create a copy of MealRatingStatusDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? rating = null,Object? userNote = null,Object? mealStatus = null,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,rating: null == rating ? _self.rating : rating // ignore: cast_nullable_to_non_nullable
as int,userNote: null == userNote ? _self.userNote : userNote // ignore: cast_nullable_to_non_nullable
as String,mealStatus: null == mealStatus ? _self.mealStatus : mealStatus // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [MealRatingStatusDto].
extension MealRatingStatusDtoPatterns on MealRatingStatusDto {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _MealRatingStatusDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _MealRatingStatusDto() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _MealRatingStatusDto value)  $default,){
final _that = this;
switch (_that) {
case _MealRatingStatusDto():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _MealRatingStatusDto value)?  $default,){
final _that = this;
switch (_that) {
case _MealRatingStatusDto() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int id,  int rating,  String userNote,  String mealStatus)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _MealRatingStatusDto() when $default != null:
return $default(_that.id,_that.rating,_that.userNote,_that.mealStatus);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int id,  int rating,  String userNote,  String mealStatus)  $default,) {final _that = this;
switch (_that) {
case _MealRatingStatusDto():
return $default(_that.id,_that.rating,_that.userNote,_that.mealStatus);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int id,  int rating,  String userNote,  String mealStatus)?  $default,) {final _that = this;
switch (_that) {
case _MealRatingStatusDto() when $default != null:
return $default(_that.id,_that.rating,_that.userNote,_that.mealStatus);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _MealRatingStatusDto implements MealRatingStatusDto {
  const _MealRatingStatusDto({required this.id, required this.rating, required this.userNote, required this.mealStatus});
  factory _MealRatingStatusDto.fromJson(Map<String, dynamic> json) => _$MealRatingStatusDtoFromJson(json);

@override final  int id;
@override final  int rating;
@override final  String userNote;
@override final  String mealStatus;

/// Create a copy of MealRatingStatusDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$MealRatingStatusDtoCopyWith<_MealRatingStatusDto> get copyWith => __$MealRatingStatusDtoCopyWithImpl<_MealRatingStatusDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$MealRatingStatusDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _MealRatingStatusDto&&(identical(other.id, id) || other.id == id)&&(identical(other.rating, rating) || other.rating == rating)&&(identical(other.userNote, userNote) || other.userNote == userNote)&&(identical(other.mealStatus, mealStatus) || other.mealStatus == mealStatus));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,rating,userNote,mealStatus);

@override
String toString() {
  return 'MealRatingStatusDto(id: $id, rating: $rating, userNote: $userNote, mealStatus: $mealStatus)';
}


}

/// @nodoc
abstract mixin class _$MealRatingStatusDtoCopyWith<$Res> implements $MealRatingStatusDtoCopyWith<$Res> {
  factory _$MealRatingStatusDtoCopyWith(_MealRatingStatusDto value, $Res Function(_MealRatingStatusDto) _then) = __$MealRatingStatusDtoCopyWithImpl;
@override @useResult
$Res call({
 int id, int rating, String userNote, String mealStatus
});




}
/// @nodoc
class __$MealRatingStatusDtoCopyWithImpl<$Res>
    implements _$MealRatingStatusDtoCopyWith<$Res> {
  __$MealRatingStatusDtoCopyWithImpl(this._self, this._then);

  final _MealRatingStatusDto _self;
  final $Res Function(_MealRatingStatusDto) _then;

/// Create a copy of MealRatingStatusDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? rating = null,Object? userNote = null,Object? mealStatus = null,}) {
  return _then(_MealRatingStatusDto(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,rating: null == rating ? _self.rating : rating // ignore: cast_nullable_to_non_nullable
as int,userNote: null == userNote ? _self.userNote : userNote // ignore: cast_nullable_to_non_nullable
as String,mealStatus: null == mealStatus ? _self.mealStatus : mealStatus // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}


/// @nodoc
mixin _$GroceryPlanningPromptResponse {

 String get status; String get message; MealInformation get data;
/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$GroceryPlanningPromptResponseCopyWith<GroceryPlanningPromptResponse> get copyWith => _$GroceryPlanningPromptResponseCopyWithImpl<GroceryPlanningPromptResponse>(this as GroceryPlanningPromptResponse, _$identity);

  /// Serializes this GroceryPlanningPromptResponse to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is GroceryPlanningPromptResponse&&(identical(other.status, status) || other.status == status)&&(identical(other.message, message) || other.message == message)&&(identical(other.data, data) || other.data == data));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,status,message,data);

@override
String toString() {
  return 'GroceryPlanningPromptResponse(status: $status, message: $message, data: $data)';
}


}

/// @nodoc
abstract mixin class $GroceryPlanningPromptResponseCopyWith<$Res>  {
  factory $GroceryPlanningPromptResponseCopyWith(GroceryPlanningPromptResponse value, $Res Function(GroceryPlanningPromptResponse) _then) = _$GroceryPlanningPromptResponseCopyWithImpl;
@useResult
$Res call({
 String status, String message, MealInformation data
});


$MealInformationCopyWith<$Res> get data;

}
/// @nodoc
class _$GroceryPlanningPromptResponseCopyWithImpl<$Res>
    implements $GroceryPlanningPromptResponseCopyWith<$Res> {
  _$GroceryPlanningPromptResponseCopyWithImpl(this._self, this._then);

  final GroceryPlanningPromptResponse _self;
  final $Res Function(GroceryPlanningPromptResponse) _then;

/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? status = null,Object? message = null,Object? data = null,}) {
  return _then(_self.copyWith(
status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as String,message: null == message ? _self.message : message // ignore: cast_nullable_to_non_nullable
as String,data: null == data ? _self.data : data // ignore: cast_nullable_to_non_nullable
as MealInformation,
  ));
}
/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$MealInformationCopyWith<$Res> get data {
  
  return $MealInformationCopyWith<$Res>(_self.data, (value) {
    return _then(_self.copyWith(data: value));
  });
}
}


/// Adds pattern-matching-related methods to [GroceryPlanningPromptResponse].
extension GroceryPlanningPromptResponsePatterns on GroceryPlanningPromptResponse {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _GroceryPlanningPromptResponse value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _GroceryPlanningPromptResponse value)  $default,){
final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _GroceryPlanningPromptResponse value)?  $default,){
final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String status,  String message,  MealInformation data)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse() when $default != null:
return $default(_that.status,_that.message,_that.data);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String status,  String message,  MealInformation data)  $default,) {final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse():
return $default(_that.status,_that.message,_that.data);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String status,  String message,  MealInformation data)?  $default,) {final _that = this;
switch (_that) {
case _GroceryPlanningPromptResponse() when $default != null:
return $default(_that.status,_that.message,_that.data);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _GroceryPlanningPromptResponse implements GroceryPlanningPromptResponse {
  const _GroceryPlanningPromptResponse({required this.status, required this.message, required this.data});
  factory _GroceryPlanningPromptResponse.fromJson(Map<String, dynamic> json) => _$GroceryPlanningPromptResponseFromJson(json);

@override final  String status;
@override final  String message;
@override final  MealInformation data;

/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$GroceryPlanningPromptResponseCopyWith<_GroceryPlanningPromptResponse> get copyWith => __$GroceryPlanningPromptResponseCopyWithImpl<_GroceryPlanningPromptResponse>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$GroceryPlanningPromptResponseToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _GroceryPlanningPromptResponse&&(identical(other.status, status) || other.status == status)&&(identical(other.message, message) || other.message == message)&&(identical(other.data, data) || other.data == data));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,status,message,data);

@override
String toString() {
  return 'GroceryPlanningPromptResponse(status: $status, message: $message, data: $data)';
}


}

/// @nodoc
abstract mixin class _$GroceryPlanningPromptResponseCopyWith<$Res> implements $GroceryPlanningPromptResponseCopyWith<$Res> {
  factory _$GroceryPlanningPromptResponseCopyWith(_GroceryPlanningPromptResponse value, $Res Function(_GroceryPlanningPromptResponse) _then) = __$GroceryPlanningPromptResponseCopyWithImpl;
@override @useResult
$Res call({
 String status, String message, MealInformation data
});


@override $MealInformationCopyWith<$Res> get data;

}
/// @nodoc
class __$GroceryPlanningPromptResponseCopyWithImpl<$Res>
    implements _$GroceryPlanningPromptResponseCopyWith<$Res> {
  __$GroceryPlanningPromptResponseCopyWithImpl(this._self, this._then);

  final _GroceryPlanningPromptResponse _self;
  final $Res Function(_GroceryPlanningPromptResponse) _then;

/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? status = null,Object? message = null,Object? data = null,}) {
  return _then(_GroceryPlanningPromptResponse(
status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as String,message: null == message ? _self.message : message // ignore: cast_nullable_to_non_nullable
as String,data: null == data ? _self.data : data // ignore: cast_nullable_to_non_nullable
as MealInformation,
  ));
}

/// Create a copy of GroceryPlanningPromptResponse
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$MealInformationCopyWith<$Res> get data {
  
  return $MealInformationCopyWith<$Res>(_self.data, (value) {
    return _then(_self.copyWith(data: value));
  });
}
}


/// @nodoc
mixin _$MealInformation {

 int get totalMeals; List<Meal> get meals;
/// Create a copy of MealInformation
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$MealInformationCopyWith<MealInformation> get copyWith => _$MealInformationCopyWithImpl<MealInformation>(this as MealInformation, _$identity);

  /// Serializes this MealInformation to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is MealInformation&&(identical(other.totalMeals, totalMeals) || other.totalMeals == totalMeals)&&const DeepCollectionEquality().equals(other.meals, meals));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,totalMeals,const DeepCollectionEquality().hash(meals));

@override
String toString() {
  return 'MealInformation(totalMeals: $totalMeals, meals: $meals)';
}


}

/// @nodoc
abstract mixin class $MealInformationCopyWith<$Res>  {
  factory $MealInformationCopyWith(MealInformation value, $Res Function(MealInformation) _then) = _$MealInformationCopyWithImpl;
@useResult
$Res call({
 int totalMeals, List<Meal> meals
});




}
/// @nodoc
class _$MealInformationCopyWithImpl<$Res>
    implements $MealInformationCopyWith<$Res> {
  _$MealInformationCopyWithImpl(this._self, this._then);

  final MealInformation _self;
  final $Res Function(MealInformation) _then;

/// Create a copy of MealInformation
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? totalMeals = null,Object? meals = null,}) {
  return _then(_self.copyWith(
totalMeals: null == totalMeals ? _self.totalMeals : totalMeals // ignore: cast_nullable_to_non_nullable
as int,meals: null == meals ? _self.meals : meals // ignore: cast_nullable_to_non_nullable
as List<Meal>,
  ));
}

}


/// Adds pattern-matching-related methods to [MealInformation].
extension MealInformationPatterns on MealInformation {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _MealInformation value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _MealInformation() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _MealInformation value)  $default,){
final _that = this;
switch (_that) {
case _MealInformation():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _MealInformation value)?  $default,){
final _that = this;
switch (_that) {
case _MealInformation() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int totalMeals,  List<Meal> meals)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _MealInformation() when $default != null:
return $default(_that.totalMeals,_that.meals);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int totalMeals,  List<Meal> meals)  $default,) {final _that = this;
switch (_that) {
case _MealInformation():
return $default(_that.totalMeals,_that.meals);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int totalMeals,  List<Meal> meals)?  $default,) {final _that = this;
switch (_that) {
case _MealInformation() when $default != null:
return $default(_that.totalMeals,_that.meals);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _MealInformation implements MealInformation {
  const _MealInformation({required this.totalMeals, required final  List<Meal> meals}): _meals = meals;
  factory _MealInformation.fromJson(Map<String, dynamic> json) => _$MealInformationFromJson(json);

@override final  int totalMeals;
 final  List<Meal> _meals;
@override List<Meal> get meals {
  if (_meals is EqualUnmodifiableListView) return _meals;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_meals);
}


/// Create a copy of MealInformation
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$MealInformationCopyWith<_MealInformation> get copyWith => __$MealInformationCopyWithImpl<_MealInformation>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$MealInformationToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _MealInformation&&(identical(other.totalMeals, totalMeals) || other.totalMeals == totalMeals)&&const DeepCollectionEquality().equals(other._meals, _meals));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,totalMeals,const DeepCollectionEquality().hash(_meals));

@override
String toString() {
  return 'MealInformation(totalMeals: $totalMeals, meals: $meals)';
}


}

/// @nodoc
abstract mixin class _$MealInformationCopyWith<$Res> implements $MealInformationCopyWith<$Res> {
  factory _$MealInformationCopyWith(_MealInformation value, $Res Function(_MealInformation) _then) = __$MealInformationCopyWithImpl;
@override @useResult
$Res call({
 int totalMeals, List<Meal> meals
});




}
/// @nodoc
class __$MealInformationCopyWithImpl<$Res>
    implements _$MealInformationCopyWith<$Res> {
  __$MealInformationCopyWithImpl(this._self, this._then);

  final _MealInformation _self;
  final $Res Function(_MealInformation) _then;

/// Create a copy of MealInformation
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? totalMeals = null,Object? meals = null,}) {
  return _then(_MealInformation(
totalMeals: null == totalMeals ? _self.totalMeals : totalMeals // ignore: cast_nullable_to_non_nullable
as int,meals: null == meals ? _self._meals : meals // ignore: cast_nullable_to_non_nullable
as List<Meal>,
  ));
}


}

// dart format on
