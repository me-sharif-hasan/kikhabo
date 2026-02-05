import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import '../../data/datasources/meal_datasource.dart';
import '../../data/models/meal.dart';
import '../../data/models/preference.dart';

/// Repository for Meal Planning logic.
class MealRepository {
  final MealDataSource _dataSource;

  MealRepository(this._dataSource);

  String _mapError(Object e) {
    debugPrint(e.toString());
    if (e is DioException) {
      return e.response?.data['message'] ?? 'Network error occurred';
    }
    return 'Unknown error occurred';
  }

  Future<MealInformation> generateMealPlan(MealPreferenceDto preferences) async {
    try {
      return await _dataSource.generateMealPlan(preferences);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> updateMealRating(MealRatingStatusDto ratingDto) async {
    try {
      await _dataSource.updateMealRating(ratingDto);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> updatePreference(UpdatePreferenceCommand command) async {
    try {
      await _dataSource.updatePreference(command);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }
}
