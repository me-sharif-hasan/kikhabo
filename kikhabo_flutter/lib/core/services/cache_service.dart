import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../data/models/meal.dart';

/// Service for caching meal data locally
class CacheService {
  static const String _mealHistoryKey = 'cached_meal_history';
  static const String _suggestedMealsKey = 'cached_suggested_meals';
  static const String _mealHistoryTimestampKey = 'cached_meal_history_timestamp';
  static const String _suggestedMealsTimestampKey = 'cached_suggested_meals_timestamp';
  
  // 3 months in milliseconds
  static const int _cacheExpirationMs = 90 * 24 * 60 * 60 * 1000;

  /// Cache meal history
  Future<void> cacheMealHistory(List<Meal> meals) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final jsonList = meals.map((meal) => meal.toJson()).toList();
      await prefs.setString(_mealHistoryKey, jsonEncode(jsonList));
      await prefs.setInt(_mealHistoryTimestampKey, DateTime.now().millisecondsSinceEpoch);
      debugPrint('✅ Cached ${meals.length} meal history items');
    } catch (e) {
      debugPrint('❌ Failed to cache meal history: $e');
      rethrow;
    }
  }

  /// Get cached meal history (returns null if expired or not found)
  Future<List<Meal>?> getCachedMealHistory() async {
    final prefs = await SharedPreferences.getInstance();
    
    // Check if cache exists
    final jsonString = prefs.getString(_mealHistoryKey);
    if (jsonString == null) {
      debugPrint('📭 No cached meal history found');
      return null;
    }

    // Check if cache is expired
    final timestamp = prefs.getInt(_mealHistoryTimestampKey);
    if (timestamp == null) {
      debugPrint('⏰ No timestamp for cached meal history');
      return null;
    }
    
    final now = DateTime.now().millisecondsSinceEpoch;
    if (now - timestamp > _cacheExpirationMs) {
      debugPrint('⏰ Cached meal history expired');
      await clearMealHistoryCache();
      return null;
    }

    // Parse and return cached data
    try {
      final List<dynamic> jsonList = jsonDecode(jsonString);
      final meals = jsonList.map((json) => Meal.fromJson(json)).toList();
      debugPrint('📦 Retrieved ${meals.length} cached meal history items');
      return meals;
    } catch (e) {
      debugPrint('❌ Failed to parse cached meal history: $e');
      await clearMealHistoryCache();
      return null;
    }
  }

  /// Cache suggested meals
  Future<void> cacheSuggestedMeals(List<Meal> meals) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final jsonList = meals.map((meal) => meal.toJson()).toList();
      await prefs.setString(_suggestedMealsKey, jsonEncode(jsonList));
      await prefs.setInt(_suggestedMealsTimestampKey, DateTime.now().millisecondsSinceEpoch);
      debugPrint('✅ Cached ${meals.length} suggested meals');
    } catch (e) {
      debugPrint('❌ Failed to cache suggested meals: $e');
      rethrow;
    }
  }

  /// Get cached suggested meals (returns null if expired or not found)
  Future<List<Meal>?> getCachedSuggestedMeals() async {
    final prefs = await SharedPreferences.getInstance();
    
    // Check if cache exists
    final jsonString = prefs.getString(_suggestedMealsKey);
    if (jsonString == null) return null;

    // Check if cache is expired
    final timestamp = prefs.getInt(_suggestedMealsTimestampKey);
    if (timestamp == null) return null;
    
    final now = DateTime.now().millisecondsSinceEpoch;
    if (now - timestamp > _cacheExpirationMs) {
      // Cache expired, clear it
      await clearSuggestedMealsCache();
      return null;
    }

    // Parse and return cached data
    try {
      final List<dynamic> jsonList = jsonDecode(jsonString);
      return jsonList.map((json) => Meal.fromJson(json)).toList();
    } catch (e) {
      // Invalid cache data, clear it
      await clearSuggestedMealsCache();
      return null;
    }
  }

  /// Clear meal history cache
  Future<void> clearMealHistoryCache() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_mealHistoryKey);
    await prefs.remove(_mealHistoryTimestampKey);
  }

  /// Clear suggested meals cache
  Future<void> clearSuggestedMealsCache() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_suggestedMealsKey);
    await prefs.remove(_suggestedMealsTimestampKey);
  }

  /// Clear all cached data
  Future<void> clearAllCache() async {
    await clearMealHistoryCache();
    await clearSuggestedMealsCache();
  }
}
