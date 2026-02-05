import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/datasources/meal_datasource.dart';
import '../../data/models/meal.dart';
import '../../data/repositories/meal_repository.dart';
import 'auth_provider.dart';

/// Provider for MealDataSource
final mealDataSourceProvider = Provider<MealDataSource>((ref) {
  final dio = ref.watch(dioClientProvider).dio;
  return MealDataSource(dio);
});

/// Provider for MealRepository
final mealRepositoryProvider = Provider<MealRepository>((ref) {
  final dataSource = ref.watch(mealDataSourceProvider);
  return MealRepository(dataSource);
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

  void clearError() {
    state = state.copyWith(error: null);
  }
}

/// Provider for meal planning state
final mealPlanningProvider = StateNotifierProvider<MealPlanningNotifier, MealPlanningState>((ref) {
  final repository = ref.watch(mealRepositoryProvider);
  return MealPlanningNotifier(repository);
});
