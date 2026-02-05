// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'preference.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_Preference _$PreferenceFromJson(Map<String, dynamic> json) => _Preference(
  id: (json['id'] as num?)?.toInt(),
  spicyRating: (json['spicyRating'] as num?)?.toDouble(),
  saltRating: (json['saltTasteRating'] as num?)?.toDouble(),
  budgetRating: (json['budgetRating'] as num?)?.toDouble(),
  hasDiabetics: json['hasDiabetics'] as bool?,
  isPregnant: json['pregnant'] as bool?,
  specialNotes: json['specialNotes'] as String?,
);

Map<String, dynamic> _$PreferenceToJson(_Preference instance) =>
    <String, dynamic>{
      'id': instance.id,
      'spicyRating': instance.spicyRating,
      'saltTasteRating': instance.saltRating,
      'budgetRating': instance.budgetRating,
      'hasDiabetics': instance.hasDiabetics,
      'pregnant': instance.isPregnant,
      'specialNotes': instance.specialNotes,
    };

_UpdatePreferenceCommand _$UpdatePreferenceCommandFromJson(
  Map<String, dynamic> json,
) => _UpdatePreferenceCommand(
  spicyRating: (json['spicyRating'] as num?)?.toDouble(),
  budgetRating: (json['budgetRating'] as num?)?.toDouble(),
  saltTasteRating: (json['SaltTasteRating'] as num?)?.toDouble(),
  hasDiabetics: json['hasDiabetics'] as bool?,
  isPregnant: json['isPregnant'] as bool?,
  specialNotes: json['specialNotes'] as String?,
);

Map<String, dynamic> _$UpdatePreferenceCommandToJson(
  _UpdatePreferenceCommand instance,
) => <String, dynamic>{
  'spicyRating': instance.spicyRating,
  'budgetRating': instance.budgetRating,
  'SaltTasteRating': instance.saltTasteRating,
  'hasDiabetics': instance.hasDiabetics,
  'isPregnant': instance.isPregnant,
  'specialNotes': instance.specialNotes,
};
