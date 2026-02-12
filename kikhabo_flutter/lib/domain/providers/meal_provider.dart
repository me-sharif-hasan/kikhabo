import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../core/services/cache_service.dart';
import '../../data/datasources/meal_datasource.dart';
import '../../data/models/meal.dart';
import '../../data/repositories/meal_repository.dart';
import 'auth_provider.dart';

/// Provider for MealDataSource
final mealDataSourceProvider = Provider<MealDataSource>((ref) {
  final dio = ref.watch(dioClientProvider).dio;
  return MealDataSource(dio);
});

/// Provider for CacheService
final cacheServiceProvider = Provider<CacheService>((ref) {
  return CacheService();
});

/// Provider for MealRepository
final mealRepositoryProvider = Provider<MealRepository>((ref) {
  final dataSource = ref.watch(mealDataSourceProvider);
  final cacheService = ref.watch(cacheServiceProvider);
  return MealRepository(dataSource, cacheService);
});

/// State class for meal planning
class MealPlanningState {
  final MealInformation? mealInformation;
  final bool isLoading;
  final String? error;

  MealPlanningState({
    this.mealInformation,
    this.isLoading = false,
    this.error,
  });

  MealPlanningState copyWith({
    MealInformation? mealInformation,
    bool? isLoading,
    String? error,
  }) {
    return MealPlanningState(
      mealInformation: mealInformation ?? this.mealInformation,
      isLoading: isLoading ?? this.isLoading,
      error: error,
    );
  }
}

/// Notifier for meal planning
class MealPlanningNotifier extends StateNotifier<MealPlanningState> {
  final MealRepository _repository;

  MealPlanningNotifier(this._repository) : super(MealPlanningState());

  Future<void> generateMealPlan(MealPreferenceDto preferenceDto) async {
    state = state.copyWith(isLoading: true, error: null);
    
    try {
      final mealInfo = await _repository.generateMealPlan(preferenceDto);
      state = state.copyWith(
        mealInformation: mealInfo,
        isLoading: false,
      );
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: e.toString(),
      );
      rethrow;
    }
  }

  /// Updates meal status (TAKEN/SKIPPED/PLANNED), rating, and notes
  Future<bool> updateMealStatus({
    required int mealId,
    String? status,
    int? rating,
    String? note,
  }) async {
    try {
      final update = MealRatingStatusDto(
        id: mealId,
        rating: rating ?? 0,
        userNote: note ?? '',
        mealStatus: status ?? 'PLANNED',
      );
      
      final success = await _repository.updateMealHistory([update]);
      
      if (success && state.mealInformation != null) {
        // Update local state
        final updatedMeals = state.mealInformation!.meals.map((meal) {
          if (meal.id == mealId) {
            return meal.copyWith(
              rating: rating?.toDouble(),
              mealStatus: status,
              userNote: note,
            );
          }
          return meal;
        }).toList();
        
        state = state.copyWith(
          mealInformation: state.mealInformation!.copyWith(
            meals: updatedMeals,
          ),
        );
      }
      
      return success;
    } catch (e) {
      return false;
    }
  }

  void clearError() {
    state = state.copyWith(error: null);
  }
}

/// Provider for meal planning state
final mealPlanningProvider = StateNotifierProvider<MealPlanningNotifier, MealPlanningState>((ref) {
  final repository = ref.watch(mealRepositoryProvider);
  return MealPlanningNotifier(repository);
});

/// Provider for meal history (paginated) - auto-disposes to reload data on navigation
final mealHistoryProvider = FutureProvider.autoDispose.family<List<Meal>, int>((ref, page) async {
  final repository = ref.watch(mealRepositoryProvider);
  return repository.getMealHistory(page: page, size: 10);
});
