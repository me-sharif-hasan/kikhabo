import 'package:freezed_annotation/freezed_annotation.dart';

part 'preference.freezed.dart';
part 'preference.g.dart';

/// Represents user dietary preferences.
@freezed
abstract class Preference with _$Preference {
  const factory Preference({
    int? id,
    double? spicyRating,
    @JsonKey(name: 'saltTasteRating') double? saltRating, // Mapped from backend 'saltTasteRating'
    double? budgetRating,
    bool? hasDiabetics,
    @JsonKey(name: 'pregnant') bool? isPregnant, // Mapped from backend 'pregnant'
    String? specialNotes,
  }) = _Preference;

  factory Preference.fromJson(Map<String, dynamic> json) => _$PreferenceFromJson(json);
}

/// Command to update preferences.
@freezed
abstract class UpdatePreferenceCommand with _$UpdatePreferenceCommand {
  const factory UpdatePreferenceCommand({
    double? spicyRating,
    double? budgetRating,
    @JsonKey(name: 'SaltTasteRating') double? saltTasteRating, // Backend expects Capital 'SaltTasteRating'
    bool? hasDiabetics,
    bool? isPregnant,
    String? specialNotes,
  }) = _UpdatePreferenceCommand;

  factory UpdatePreferenceCommand.fromJson(Map<String, dynamic> json) => _$UpdatePreferenceCommandFromJson(json);
}
