import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/meal.dart';
import '../../../domain/providers/meal_provider.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/meal_card.dart';
import '../../widgets/shopping_list_modal.dart';

class MealsScreen extends ConsumerStatefulWidget {
  const MealsScreen({super.key});

  @override
  ConsumerState<MealsScreen> createState() => _MealsScreenState();
}

class _MealsScreenState extends ConsumerState<MealsScreen> {
  final ScrollController _scrollController = ScrollController();
  final List<Meal> _allMeals = [];
  int _currentPage = 0;
  bool _isLoadingMore = false;
  bool _hasMorePages = true;

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  void _onScroll() {
    if (_scrollController.position.pixels >= _scrollController.position.maxScrollExtent - 200) {
      _loadMoreMeals();
    }
  }

  Future<void> _loadMoreMeals() async {
    if (_isLoadingMore || !_hasMorePages) return;

    setState(() {
      _isLoadingMore = true;
    });

    try {
      final repository = ref.read(mealRepositoryProvider);
      final newMeals = await repository.getMealHistory(page: _currentPage + 1, size: 10);
      
      if (newMeals.isEmpty) {
        setState(() {
          _hasMorePages = false;
          _isLoadingMore = false;
        });
      } else {
        setState(() {
          _allMeals.addAll(newMeals);
          _currentPage++;
          _isLoadingMore = false;
        });
      }
    } catch (e) {
      setState(() {
        _isLoadingMore = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    // Fetch initial meal history from API (page 0, size 10)
    final mealHistoryAsync = ref.watch(mealHistoryProvider(0));
    
    // Fallback to current meal plan if history is empty
    final mealPlanningState = ref.watch(mealPlanningProvider);
    final currentPlanMeals = mealPlanningState.mealInformation?.meals ?? [];

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: mealHistoryAsync.when(
          data: (historyMeals) {
            // Initialize _allMeals with first page if empty
            if (_allMeals.isEmpty && historyMeals.isNotEmpty) {
              WidgetsBinding.instance.addPostFrameCallback((_) {
                if (mounted) {
                  setState(() {
                    _allMeals.addAll(historyMeals);
                  });
                }
              });
            }

            // Use accumulated meals if available, otherwise use current plan
            final meals = _allMeals.isNotEmpty ? _allMeals : currentPlanMeals;
            final isHistory = _allMeals.isNotEmpty;
            
            return Column(
              children: [
                const SizedBox(height: 20),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16.0),
                  child: GlassCard(
                    blur: 10,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              isHistory ? 'Meal History' : 'Suggested Meals',
                              style: AppTextStyles.titleLarge,
                            ),
                            Text(
                              meals.isEmpty
                                  ? 'No meals generated yet'
                                  : '${meals.length} meals${_hasMorePages ? '+' : ''}',
                              style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                            ),
                          ],
                        ),
                        if (meals.isNotEmpty)
                          IconButton(
                            icon: const Icon(Icons.download_rounded, color: AppColors.accent),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) => ShoppingListModal(meals: meals),
                              );
                            },
                          ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                Expanded(
                  child: meals.isEmpty
                      ? Center(
                          child: GlassCard(
                            blur: 10,
                            child: Column(
                              mainAxisSize: MainAxisSize.min,
                              children: [
                                Icon(Icons.restaurant_menu, size: 64, color: AppColors.textSecondary),
                                const SizedBox(height: 16),
                                Text(
                                  'No Meals Yet',
                                  style: AppTextStyles.titleMedium,
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  'Generate a meal plan to get started',
                                  style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                                ),
                              ],
                            ),
                          ),
                        )
                      : ListView.builder(
                          controller: _scrollController,
                          padding: const EdgeInsets.all(16),
                          itemCount: meals.length + (_isLoadingMore ? 1 : 0),
                          itemBuilder: (context, index) {
                            if (index == meals.length) {
                              // Loading indicator at bottom
                              return const Padding(
                                padding: EdgeInsets.all(16.0),
                                child: Center(
                                  child: CircularProgressIndicator(
                                    color: AppColors.primary,
                                  ),
                                ),
                              );
                            }
                            return MealCard(
                              meal: meals[index],
                            );
                          },
                        ),
                ),
              ],
            );
          },
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (error, stack) {
            // On error, fallback to current plan
            final meals = currentPlanMeals;
            
            return Column(
              children: [
                const SizedBox(height: 20),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16.0),
                  child: GlassCard(
                    blur: 10,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('Suggested Meals', style: AppTextStyles.titleLarge),
                            Text(
                              meals.isEmpty
                                  ? 'No meals generated yet'
                                  : '${meals.length} meals',
                              style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                            ),
                          ],
                        ),
                        if (meals.isNotEmpty)
                          IconButton(
                            icon: const Icon(Icons.download_rounded, color: AppColors.accent),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) => ShoppingListModal(meals: meals),
                              );
                            },
                          ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                Expanded(
                  child: meals.isEmpty
                      ? Center(
                          child: GlassCard(
                            blur: 10,
                            child: Column(
                              mainAxisSize: MainAxisSize.min,
                              children: [
                                Icon(Icons.restaurant_menu, size: 64, color: AppColors.textSecondary),
                                const SizedBox(height: 16),
                                Text(
                                  'No Meals Yet',
                                  style: AppTextStyles.titleMedium,
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  'Generate a meal plan to get started',
                                  style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                                ),
                              ],
                            ),
                          ),
                        )
                      : ListView.builder(
                          padding: const EdgeInsets.all(16),
                          itemCount: meals.length,
                          itemBuilder: (context, index) {
                            return MealCard(
                              meal: meals[index],
                            );
                          },
                        ),
                ),
              ],
            );
          },
        ),
      ),
    );
  }
}
