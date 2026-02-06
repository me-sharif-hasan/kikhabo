import 'package:freezed_annotation/freezed_annotation.dart';

part 'meal.freezed.dart';
part 'meal.g.dart';

/// Represents a Grocery item in a meal.
@freezed
abstract class Grocery with _$Grocery {
  const factory Grocery({
    required String name,
    required String priceRatingOutOf10,
    required String amountInGm,
  }) = _Grocery;

  factory Grocery.fromJson(Map<String, dynamic> json) => _$GroceryFromJson(json);
}

/// Represents a single meal.
@freezed
abstract class Meal with _$Meal {
  const factory Meal({
    int? id,
    required String mealName,
    required dynamic totalEnergy, // Changed to dynamic to handle int or String from API
    String? ingredients, // Changed from List<String>? to String? to match API
    List<Grocery>? groceries, // From meal planning API
    List<String>? groceryNames, // From meal history API
    String? note,
    double? rating, // 1-5 rating
    String? mealStatus, // TAKEN, SKIPPED, PLANNED
    String? userNote, // User's feedback/note
    int? timestamp, // From meal history API
  }) = _Meal;

  factory Meal.fromJson(Map<String, dynamic> json) => _$MealFromJson(json);
}

/// DTO for meal planning preferences sent to API.
@freezed
abstract class MealPreferenceDto with _$MealPreferenceDto {
  const factory MealPreferenceDto({
    required double spicyRating,
    required double saltRating,
    required int dayCount,
    required double priceRating,
    required int totalMealCount,
    required int mealPerDay,
    required List<int> agesOfTheMembers,
  }) = _MealPreferenceDto;

  factory MealPreferenceDto.fromJson(Map<String, dynamic> json) => _$MealPreferenceDtoFromJson(json);
}

/// DTO for updating meal rating/status.
@freezed
abstract class MealRatingStatusDto with _$MealRatingStatusDto {
  const factory MealRatingStatusDto({
    required int id,
    required int rating,
    required String userNote,
    required String mealStatus, // ACCEPTED, REJECTED
  }) = _MealRatingStatusDto;

  factory MealRatingStatusDto.fromJson(Map<String, dynamic> json) => _$MealRatingStatusDtoFromJson(json);
}

/// Response returned from planning prompt API (wrapping generic data).
@freezed
abstract class GroceryPlanningPromptResponse with _$GroceryPlanningPromptResponse {
  const factory GroceryPlanningPromptResponse({
    required String status,
    required String message,
    required MealInformation data,
  }) = _GroceryPlanningPromptResponse;

  factory GroceryPlanningPromptResponse.fromJson(Map<String, dynamic> json) => _$GroceryPlanningPromptResponseFromJson(json);
}

/// Contains list of meals and total count.
@freezed
abstract class MealInformation with _$MealInformation {
  const factory MealInformation({
    required int totalMeals,
    required List<Meal> meals,
  }) = _MealInformation;

  factory MealInformation.fromJson(Map<String, dynamic> json) => _$MealInformationFromJson(json);
}
