import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../domain/providers/meal_provider.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/meal_card.dart';
import '../../widgets/shopping_list_modal.dart';

class MealsScreen extends ConsumerWidget {
  const MealsScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final mealPlanningState = ref.watch(mealPlanningProvider);
    final meals = mealPlanningState.mealInformation?.meals ?? [];

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: Column(
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
                            : '${meals.length} meals generated', 
                          style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)
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
            Expanded(
              child: meals.isEmpty
                ? Center(
                    child: GlassCard(
                      padding: const EdgeInsets.all(32),
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Icon(
                            Icons.restaurant_menu,
                            size: 64,
                            color: AppColors.textSecondary.withOpacity(0.5),
                          ),
                          const SizedBox(height: 16),
                          Text(
                            'No Meals Yet',
                            style: AppTextStyles.titleLarge,
                          ),
                          const SizedBox(height: 8),
                          Text(
                            'Generate a meal plan from the\nDashboard to see suggestions here',
                            style: AppTextStyles.bodyMedium.copyWith(color: AppColors.textSecondary),
                            textAlign: TextAlign.center,
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
                        onRate: () {
                          showDialog(
                            context: context,
                            builder: (context) => AlertDialog(
                              backgroundColor: AppColors.surface.withOpacity(0.9),
                              title: Text('Rate Meal', style: AppTextStyles.titleMedium),
                              content: Text('Rating feature coming soon!', style: AppTextStyles.bodyMedium),
                              actions: [
                                TextButton(
                                  onPressed: () => Navigator.pop(context),
                                  child: Text('Close', style: TextStyle(color: AppColors.primaryLight)),
                                ),
                              ],
                            ),
                          );
                        },
                      );
                    },
                  ),
            ),
          ],
        ),
      ),
    );
  }
}
