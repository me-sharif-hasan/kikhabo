// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'analytics_models.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$EnergyData {

 String get period;// Date or period label (e.g., "2024-02-06", "Week 1")
 double get totalEnergy;
/// Create a copy of EnergyData
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$EnergyDataCopyWith<EnergyData> get copyWith => _$EnergyDataCopyWithImpl<EnergyData>(this as EnergyData, _$identity);

  /// Serializes this EnergyData to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is EnergyData&&(identical(other.period, period) || other.period == period)&&(identical(other.totalEnergy, totalEnergy) || other.totalEnergy == totalEnergy));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,period,totalEnergy);

@override
String toString() {
  return 'EnergyData(period: $period, totalEnergy: $totalEnergy)';
}


}

/// @nodoc
abstract mixin class $EnergyDataCopyWith<$Res>  {
  factory $EnergyDataCopyWith(EnergyData value, $Res Function(EnergyData) _then) = _$EnergyDataCopyWithImpl;
@useResult
$Res call({
 String period, double totalEnergy
});




}
/// @nodoc
class _$EnergyDataCopyWithImpl<$Res>
    implements $EnergyDataCopyWith<$Res> {
  _$EnergyDataCopyWithImpl(this._self, this._then);

  final EnergyData _self;
  final $Res Function(EnergyData) _then;

/// Create a copy of EnergyData
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? period = null,Object? totalEnergy = null,}) {
  return _then(_self.copyWith(
period: null == period ? _self.period : period // ignore: cast_nullable_to_non_nullable
as String,totalEnergy: null == totalEnergy ? _self.totalEnergy : totalEnergy // ignore: cast_nullable_to_non_nullable
as double,
  ));
}

}


/// Adds pattern-matching-related methods to [EnergyData].
extension EnergyDataPatterns on EnergyData {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _EnergyData value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _EnergyData() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _EnergyData value)  $default,){
final _that = this;
switch (_that) {
case _EnergyData():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _EnergyData value)?  $default,){
final _that = this;
switch (_that) {
case _EnergyData() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String period,  double totalEnergy)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _EnergyData() when $default != null:
return $default(_that.period,_that.totalEnergy);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String period,  double totalEnergy)  $default,) {final _that = this;
switch (_that) {
case _EnergyData():
return $default(_that.period,_that.totalEnergy);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String period,  double totalEnergy)?  $default,) {final _that = this;
switch (_that) {
case _EnergyData() when $default != null:
return $default(_that.period,_that.totalEnergy);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _EnergyData implements EnergyData {
  const _EnergyData({required this.period, required this.totalEnergy});
  factory _EnergyData.fromJson(Map<String, dynamic> json) => _$EnergyDataFromJson(json);

@override final  String period;
// Date or period label (e.g., "2024-02-06", "Week 1")
@override final  double totalEnergy;

/// Create a copy of EnergyData
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$EnergyDataCopyWith<_EnergyData> get copyWith => __$EnergyDataCopyWithImpl<_EnergyData>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$EnergyDataToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _EnergyData&&(identical(other.period, period) || other.period == period)&&(identical(other.totalEnergy, totalEnergy) || other.totalEnergy == totalEnergy));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,period,totalEnergy);

@override
String toString() {
  return 'EnergyData(period: $period, totalEnergy: $totalEnergy)';
}


}

/// @nodoc
abstract mixin class _$EnergyDataCopyWith<$Res> implements $EnergyDataCopyWith<$Res> {
  factory _$EnergyDataCopyWith(_EnergyData value, $Res Function(_EnergyData) _then) = __$EnergyDataCopyWithImpl;
@override @useResult
$Res call({
 String period, double totalEnergy
});




}
/// @nodoc
class __$EnergyDataCopyWithImpl<$Res>
    implements _$EnergyDataCopyWith<$Res> {
  __$EnergyDataCopyWithImpl(this._self, this._then);

  final _EnergyData _self;
  final $Res Function(_EnergyData) _then;

/// Create a copy of EnergyData
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? period = null,Object? totalEnergy = null,}) {
  return _then(_EnergyData(
period: null == period ? _self.period : period // ignore: cast_nullable_to_non_nullable
as String,totalEnergy: null == totalEnergy ? _self.totalEnergy : totalEnergy // ignore: cast_nullable_to_non_nullable
as double,
  ));
}


}


/// @nodoc
mixin _$CostData {

 String get period;// Date or period label
 double get costPercentage;
/// Create a copy of CostData
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$CostDataCopyWith<CostData> get copyWith => _$CostDataCopyWithImpl<CostData>(this as CostData, _$identity);

  /// Serializes this CostData to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is CostData&&(identical(other.period, period) || other.period == period)&&(identical(other.costPercentage, costPercentage) || other.costPercentage == costPercentage));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,period,costPercentage);

@override
String toString() {
  return 'CostData(period: $period, costPercentage: $costPercentage)';
}


}

/// @nodoc
abstract mixin class $CostDataCopyWith<$Res>  {
  factory $CostDataCopyWith(CostData value, $Res Function(CostData) _then) = _$CostDataCopyWithImpl;
@useResult
$Res call({
 String period, double costPercentage
});




}
/// @nodoc
class _$CostDataCopyWithImpl<$Res>
    implements $CostDataCopyWith<$Res> {
  _$CostDataCopyWithImpl(this._self, this._then);

  final CostData _self;
  final $Res Function(CostData) _then;

/// Create a copy of CostData
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? period = null,Object? costPercentage = null,}) {
  return _then(_self.copyWith(
period: null == period ? _self.period : period // ignore: cast_nullable_to_non_nullable
as String,costPercentage: null == costPercentage ? _self.costPercentage : costPercentage // ignore: cast_nullable_to_non_nullable
as double,
  ));
}

}


/// Adds pattern-matching-related methods to [CostData].
extension CostDataPatterns on CostData {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _CostData value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _CostData() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _CostData value)  $default,){
final _that = this;
switch (_that) {
case _CostData():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _CostData value)?  $default,){
final _that = this;
switch (_that) {
case _CostData() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String period,  double costPercentage)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _CostData() when $default != null:
return $default(_that.period,_that.costPercentage);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String period,  double costPercentage)  $default,) {final _that = this;
switch (_that) {
case _CostData():
return $default(_that.period,_that.costPercentage);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String period,  double costPercentage)?  $default,) {final _that = this;
switch (_that) {
case _CostData() when $default != null:
return $default(_that.period,_that.costPercentage);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _CostData implements CostData {
  const _CostData({required this.period, required this.costPercentage});
  factory _CostData.fromJson(Map<String, dynamic> json) => _$CostDataFromJson(json);

@override final  String period;
// Date or period label
@override final  double costPercentage;

/// Create a copy of CostData
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$CostDataCopyWith<_CostData> get copyWith => __$CostDataCopyWithImpl<_CostData>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$CostDataToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _CostData&&(identical(other.period, period) || other.period == period)&&(identical(other.costPercentage, costPercentage) || other.costPercentage == costPercentage));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,period,costPercentage);

@override
String toString() {
  return 'CostData(period: $period, costPercentage: $costPercentage)';
}


}

/// @nodoc
abstract mixin class _$CostDataCopyWith<$Res> implements $CostDataCopyWith<$Res> {
  factory _$CostDataCopyWith(_CostData value, $Res Function(_CostData) _then) = __$CostDataCopyWithImpl;
@override @useResult
$Res call({
 String period, double costPercentage
});




}
/// @nodoc
class __$CostDataCopyWithImpl<$Res>
    implements _$CostDataCopyWith<$Res> {
  __$CostDataCopyWithImpl(this._self, this._then);

  final _CostData _self;
  final $Res Function(_CostData) _then;

/// Create a copy of CostData
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? period = null,Object? costPercentage = null,}) {
  return _then(_CostData(
period: null == period ? _self.period : period // ignore: cast_nullable_to_non_nullable
as String,costPercentage: null == costPercentage ? _self.costPercentage : costPercentage // ignore: cast_nullable_to_non_nullable
as double,
  ));
}


}

// dart format on
