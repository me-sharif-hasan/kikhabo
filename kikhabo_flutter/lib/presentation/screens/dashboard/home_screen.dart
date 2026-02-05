import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../widgets/glass_button.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/custom_slider.dart';

class HomeScreen extends ConsumerStatefulWidget {
  const HomeScreen({super.key});

  @override
  ConsumerState<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends ConsumerState<HomeScreen> {
  // Meal Planning State (Local for now, will move to Provider later)
  double _spicyRating = 5.0;
  double _saltRating = 5.0;
  double _priceRating = 5.0;
  double _daysCount = 1.0;
  double _mealsPerDay = 3.0;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const SizedBox(height: 60), // Space for AppBar
            
            // Header Section
            GlassCard(
              blur: 10,
              child: Column(
                children: [
                   Text(
                    'Plan Your Meals',
                    style: AppTextStyles.headlineMedium,
                  ),
                  const SizedBox(height: 8),
                  Text(
                    'Customize your preferences to get AI-generated meal suggestions.',
                    style: AppTextStyles.bodyMedium.copyWith(color: AppColors.textSecondary),
                    textAlign: TextAlign.center,
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),

            // Form Section
            GlassCard(
              blur: 20,
              padding: const EdgeInsets.all(24),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  CustomSlider(
                    label: 'Spiciness',
                    value: _spicyRating,
                    min: 1,
                    max: 10,
                    divisions: 9,
                    onChanged: (v) => setState(() => _spicyRating = v),
                    labelBuilder: (v) => '${v.toInt()}/10',
                  ),
                  const SizedBox(height: 20),

                  CustomSlider(
                    label: 'Saltiness',
                    value: _saltRating,
                    min: 1,
                    max: 10,
                    divisions: 9,
                    onChanged: (v) => setState(() => _saltRating = v),
                    labelBuilder: (v) => '${v.toInt()}/10',
                  ),
                  const SizedBox(height: 20),

                  CustomSlider(
                    label: 'Price Range',
                    value: _priceRating,
                    min: 1,
                    max: 5,
                    divisions: 4,
                    onChanged: (v) => setState(() => _priceRating = v),
                    labelBuilder: (v) {
                      if (v <= 2) return 'Budget';
                      if (v <= 4) return 'Standard';
                      return 'Premium';
                    },
                  ),
                  const SizedBox(height: 20),

                  Row(
                    children: [
                      Expanded(
                        child: CustomSlider(
                          label: 'Days',
                          value: _daysCount,
                          min: 1,
                          max: 7,
                          divisions: 6,
                          onChanged: (v) => setState(() => _daysCount = v),
                          labelBuilder: (v) => '${v.toInt()} Days',
                        ),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: CustomSlider(
                          label: 'Meals/Day',
                          value: _mealsPerDay,
                          min: 1,
                          max: 5,
                          divisions: 4,
                          onChanged: (v) => setState(() => _mealsPerDay = v),
                          labelBuilder: (v) => '${v.toInt()} Meals',
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 40),

                  GlassButton(
                    text: 'Generate Meal Plan 🪄',
                    onPressed: () {
                      // Logic to trigger API call will go here
                      ScaffoldMessenger.of(context).showSnackBar(
                         const SnackBar(content: Text('Generating plan... (API logic pending)')),
                      );
                    },
                    gradient: AppColors.bgGradient1,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
