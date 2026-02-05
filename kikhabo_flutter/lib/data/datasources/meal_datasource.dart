import 'package:dio/dio.dart';
import '../models/meal.dart';
import '../models/preference.dart';
import '../../core/constants/api_constants.dart';

/// Responsible for Meal Planning related API calls.
class MealDataSource {
  final Dio _dio;

  MealDataSource(this._dio);

  /// Generates a meal plan based on preferences.
  Future<MealInformation> generateMealPlan(MealPreferenceDto preferenceDto) async {
    try {
      final response = await _dio.post(
        ApiConstants.mealPlanning,
        data: preferenceDto.toJson(),
      );
      
      final promptResponse = GroceryPlanningPromptResponse.fromJson(response.data);
      return promptResponse.data;
    } catch (e) {
      rethrow;
    }
  }

  /// Updates a meal's rating or status (Accept/Reject).
  Future<void> updateMealRating(MealRatingStatusDto ratingDto) async {
    try {
      await _dio.post(
        ApiConstants.updateMeal,
        data: ratingDto.toJson(),
      );
    } catch (e) {
      rethrow;
    }
  }

  /// Updates the user's dietary preferences.
  Future<void> updatePreference(UpdatePreferenceCommand preferenceCommand) async {
    try {
      await _dio.post(
        ApiConstants.updatePreference,
        data: preferenceCommand.toJson(),
      );
    } catch (e) {
      rethrow;
    }
  }
}
