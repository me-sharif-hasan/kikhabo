import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/meal.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/meal_card.dart';
import '../../widgets/shopping_list_modal.dart';

// Temporary Mock Data Provider until API integration
final mealListProvider = Provider<List<Meal>>((ref) {
  return [
    const Meal(
      id: 1,
      mealName: 'Chicken Curry with Rice',
      totalEnergy: '650',
      note: 'Spicy and savory',
      ingredients: ['Chicken', 'Rice', 'Spices', 'Onion', 'Garlic'],
      groceries: [
        Grocery(name: 'Chicken', priceRatingOutOf10: '5', amountInGm: '200'),
        Grocery(name: 'Rice', priceRatingOutOf10: '2', amountInGm: '150'),
        Grocery(name: 'Onion', priceRatingOutOf10: '1', amountInGm: '50'),
      ],
    ),
    const Meal(
      id: 2,
      mealName: 'Vegetable Stir Fry',
      totalEnergy: '350',
      note: 'Healthy and light',
      ingredients: ['Broccoli', 'Carrot', 'Bell Pepper', 'Soy Sauce'],
      groceries: [
        Grocery(name: 'Broccoli', priceRatingOutOf10: '3', amountInGm: '100'),
        Grocery(name: 'Carrot', priceRatingOutOf10: '1', amountInGm: '80'),
        Grocery(name: 'Bell Pepper', priceRatingOutOf10: '4', amountInGm: '50'),
      ],
    ),
  ];
});

class MealsScreen extends ConsumerWidget {
  const MealsScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final meals = ref.watch(mealListProvider);

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
                        Text('${meals.length} meals generated', style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)),
                      ],
                    ),
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
              child: ListView.builder(
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
