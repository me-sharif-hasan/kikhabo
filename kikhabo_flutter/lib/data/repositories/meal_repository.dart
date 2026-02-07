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
      final message = e.response?.data['message'];
      
      // Handle List of error messages
      if (message is List) {
        return message.map((e) => e.toString()).join('\n');
      } else if (message is String) {
        return message;
      }
      
      return 'Network error occurred';
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

  Future<bool> updateMealHistory(List<MealRatingStatusDto> updates) async {
    try {
      return await _dataSource.updateMealHistory(updates);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<List<Meal>> getMealHistory({int page = 0, int size = 10}) async {
    try {
      return await _dataSource.getMealHistory(page: page, size: size);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }
}
