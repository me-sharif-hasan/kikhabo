import 'package:flutter/material.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/preference.dart';
import '../../widgets/glass_card.dart';
import 'edit_preferences_modal.dart';

class ManagePreferencesScreen extends StatefulWidget {
  const ManagePreferencesScreen({super.key});

  @override
  State<ManagePreferencesScreen> createState() => _ManagePreferencesScreenState();
}

class _ManagePreferencesScreenState extends State<ManagePreferencesScreen> {
  // Mock Preference Data
  Preference? _preference = const Preference(
    id: 1,
    spicyRating: 7.0,
    saltRating: 5.0,
    budgetRating: 6.0,
    hasDiabetics: false,
    isPregnant: true,
    specialNotes: 'No peanuts please.',
  );

  void _openEditModal() async {
    final result = await showDialog<Preference>(
      context: context,
      barrierColor: Colors.black.withOpacity(0.5),
      builder: (context) => EditPreferencesModal(currentPreference: _preference),
    );

    if (result != null) {
      setState(() {
        _preference = result;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Preferences updated successfully!')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      floatingActionButton: FloatingActionButton(
        onPressed: _openEditModal,
        backgroundColor: AppColors.accent,
        child: const Icon(Icons.edit, color: Colors.white),
      ),
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
                        Text('My Preferences', style: AppTextStyles.titleLarge),
                        Text('Customize your dietary needs', style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Expanded(
              child: _preference == null
                  ? Center(
                      child: GlassCard(
                        padding: const EdgeInsets.all(24),
                        child: Column(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text(
                              "You have no preferences!",
                              style: AppTextStyles.titleMedium,
                              textAlign: TextAlign.center,
                            ),
                            const SizedBox(height: 8),
                            Text(
                              "Please add your preferences to get personalized suggestions.",
                              style: AppTextStyles.bodyMedium.copyWith(color: AppColors.textSecondary),
                              textAlign: TextAlign.center,
                            ),
                            const SizedBox(height: 16),
                            ElevatedButton(
                              onPressed: _openEditModal,
                              style: ElevatedButton.styleFrom(backgroundColor: AppColors.primary),
                              child: const Text('Add Preferences', style: TextStyle(color: Colors.white)),
                            )
                          ],
                        ),
                      ),
                    )
                  : SingleChildScrollView(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          GridView.count(
                            crossAxisCount: 2,
                            shrinkWrap: true,
                            physics: const NeverScrollableScrollPhysics(),
                            crossAxisSpacing: 16,
                            mainAxisSpacing: 16,
                            childAspectRatio: 1.3,
                            children: [
                              _buildStatCard('Spiciness', '${_preference!.spicyRating?.toInt() ?? 0}/10', '🌶️'),
                              _buildStatCard('Saltiness', '${_preference!.saltRating?.toInt() ?? 0}/10', '🧂'),
                              _buildStatCard('Budget', '${_preference!.budgetRating?.toInt() ?? 0}/10', '💰'),
                              _buildStatCard('Diabetic', (_preference!.hasDiabetics ?? false) ? 'Yes' : 'No', '🩺'),
                              _buildStatCard('Pregnant', (_preference!.isPregnant ?? false) ? 'Yes' : 'No', '🤰'),
                            ],
                          ),
                          const SizedBox(height: 16),
                          GlassCard(
                            child: SizedBox(
                              width: double.infinity,
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text('Special Notes', style: AppTextStyles.titleMedium),
                                  const SizedBox(height: 8),
                                  Text(
                                    (_preference!.specialNotes?.isEmpty ?? true) ? 'None' : _preference!.specialNotes!,
                                    style: AppTextStyles.bodyMedium.copyWith(color: AppColors.textSecondary),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatCard(String label, String value, String icon) {
    return GlassCard(
      padding: const EdgeInsets.all(16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(icon, style: const TextStyle(fontSize: 32)),
          const SizedBox(height: 8),
          Text(value, style: AppTextStyles.headlineSmall.copyWith(color: AppColors.primaryLight)),
          Text(label, style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)),
        ],
      ),
    );
  }
}
