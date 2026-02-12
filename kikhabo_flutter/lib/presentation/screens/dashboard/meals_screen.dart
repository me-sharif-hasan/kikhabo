import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../core/utils/shopping_list_pdf_generator.dart';
import '../../../data/models/meal.dart';
import '../../../domain/providers/meal_provider.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/meal_card.dart';

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
  bool _isDownloadingPdf = false;

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
    
    // Reset pagination state on init to force reload
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (mounted) {
        setState(() {
          _allMeals.clear();
          _currentPage = 0;
          _hasMorePages = true;
        });
      }
    });
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  bool _isSuggestedMode(BuildContext context) {
    final uri = GoRouterState.of(context).uri;
    return uri.queryParameters['view'] == 'suggested';
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

  Future<void> _refreshMeals() async {
    // Invalidate the provider to force a reload
    ref.invalidate(mealHistoryProvider);
    
    setState(() {
      _allMeals.clear();
      _currentPage = 0;
      _hasMorePages = true;
    });
  }

  Future<void> _refreshSuggestedMeals() async {
    // For suggested meals, just show a brief loading state
    await Future.delayed(const Duration(milliseconds: 500));
  }

  Future<void> _downloadShoppingListPdf(List<Meal> meals) async {
    setState(() {
      _isDownloadingPdf = true;
    });

    try {
      final file = await ShoppingListPdfGenerator.generateShoppingListPdf(meals);
      
      if (file == null) return;

      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Shopping list saved to: ${file.path}'),
            backgroundColor: AppColors.primary,
            behavior: SnackBarBehavior.floating,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Failed to generate PDF: ${e.toString()}'),
            backgroundColor: AppColors.error,
            behavior: SnackBarBehavior.floating,
          ),
        );
      }
    } finally {
      if (mounted) {
        setState(() {
          _isDownloadingPdf = false;
        });
      }
    }
  }
  @override
  Widget build(BuildContext context) {
    final isSuggested = _isSuggestedMode(context);
    
    // Get suggested meals from planning provider
    final mealPlanningState = ref.watch(mealPlanningProvider);
    final suggestedMeals = mealPlanningState.mealInformation?.meals ?? [];
    
    // If in suggested mode, show suggested meals directly
    if (isSuggested) {
      return Scaffold(
        backgroundColor: Colors.transparent,
        body: SafeArea(
          child: RefreshIndicator(
            onRefresh: _refreshSuggestedMeals,
            color: AppColors.primary,
            child: _buildMealsList(
              meals: suggestedMeals,
              title: 'Suggested Meals',
              isPaginated: false,
            ),
          ),
        ),
      );
    }
    
    // Otherwise, show history with pagination
    final mealHistoryAsync = ref.watch(mealHistoryProvider(0));

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: RefreshIndicator(
          onRefresh: _refreshMeals,
          color: AppColors.primary,
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

              // Use accumulated meals for history mode
              final meals = _allMeals.isNotEmpty ? _allMeals : suggestedMeals;
              
              return _buildMealsList(
                meals: meals,
                title: 'Meal History',
                isPaginated: true,
              );
            },
            loading: () => const Center(child: CircularProgressIndicator(color: AppColors.primary)),
            error: (error, stack) {
              // On error, fallback to suggested meals
              return _buildMealsList(
                meals: suggestedMeals,
                title: 'Suggested Meals',
                isPaginated: false,
              );
            },
          ),
        ),
      ),
    );
  }

  Widget _buildMealsList({
    required List<Meal> meals,
    required String title,
    required bool isPaginated,
  }) {
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
                      title,
                      style: AppTextStyles.titleLarge,
                    ),
                    Text(
                      meals.isEmpty
                          ? 'No meals generated yet'
                          : '${meals.length} meals${isPaginated && _hasMorePages ? '+' : ''}',
                      style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                    ),
                  ],
                ),
                if (meals.isNotEmpty)
                  IconButton(
                    icon: _isDownloadingPdf
                        ? const SizedBox(
                            width: 20,
                            height: 20,
                            child: CircularProgressIndicator(
                              strokeWidth: 2,
                              color: AppColors.accent,
                            ),
                          )
                        : const Icon(Icons.picture_as_pdf, color: AppColors.accent),
                    onPressed: _isDownloadingPdf ? null : () => _downloadShoppingListPdf(meals),
                    tooltip: 'Download Shopping List PDF',
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
                  controller: isPaginated ? _scrollController : null,
                  padding: const EdgeInsets.all(16),
                  itemCount: meals.length + (isPaginated && _isLoadingMore ? 1 : 0),
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
  }
}
