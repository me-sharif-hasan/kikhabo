import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import '../../core/services/cache_service.dart';
import '../../data/datasources/meal_datasource.dart';
import '../../data/models/meal.dart';
import '../../data/models/preference.dart';

/// Repository for Meal Planning logic.
class MealRepository {
  final MealDataSource _dataSource;
  final CacheService _cacheService;

  MealRepository(this._dataSource, this._cacheService);

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
      final result = await _dataSource.generateMealPlan(preferences);
      // Cache in background without blocking
      if (result.meals != null && result.meals!.isNotEmpty) {
        _cacheService.cacheSuggestedMeals(result.meals!).catchError((e) {
          debugPrint('Failed to cache suggested meals: $e');
        });
      }
      return result;
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> updateMealRating(MealRatingStatusDto ratingDto) async {
    try {
      await _dataSource.updateMealRating(ratingDto);
      // Clear cache to force fresh data on next fetch
      await _cacheService.clearMealHistoryCache();
      debugPrint('🗑️ Cleared meal history cache after rating update');
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
      final result = await _dataSource.updateMealHistory(updates);
      // Clear cache to force fresh data on next fetch
      await _cacheService.clearMealHistoryCache();
      debugPrint('🗑️ Cleared meal history cache after bulk update');
      return result;
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<List<Meal>> getMealHistory({int page = 0, int size = 10}) async {
    try {
      final meals = await _dataSource.getMealHistory(page: page, size: size);
      // Cache in background without blocking (only first page)
      if (page == 0 && meals.isNotEmpty) {
        _cacheService.cacheMealHistory(meals).catchError((e) {
          debugPrint('Failed to cache meal history: $e');
        });
      }
      return meals;
    } catch (e) {
      debugPrint('API Error: $e');
      // Only use cache if offline (API failed) and requesting first page
      if (page == 0) {
        final cached = await _cacheService.getCachedMealHistory();
        if (cached != null && cached.isNotEmpty) {
          debugPrint('Using cached meal history (${cached.length} meals)');
          return cached;
        }
      }
      throw Exception(_mapError(e));
    }
  }
}
