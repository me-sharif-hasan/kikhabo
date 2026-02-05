import 'package:flutter/material.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../core/theme/glass_styles.dart';
import '../../../data/models/preference.dart';
import '../../widgets/custom_slider.dart';
import '../../widgets/glass_button.dart';
import '../../widgets/glass_text_field.dart';

class EditPreferencesModal extends StatefulWidget {
  final Preference? currentPreference;

  const EditPreferencesModal({super.key, this.currentPreference});

  @override
  State<EditPreferencesModal> createState() => _EditPreferencesModalState();
}

class _EditPreferencesModalState extends State<EditPreferencesModal> {
  late double _spicyRating;
  late double _saltRating;
  late double _budgetRating;
  late bool _hasDiabetics;
  late bool _pregnant;
  late TextEditingController _notesController;

  @override
  void initState() {
    super.initState();
    _spicyRating = widget.currentPreference?.spicyRating ?? 5.0;
    _saltRating = widget.currentPreference?.saltRating ?? 5.0;
    _budgetRating = widget.currentPreference?.budgetRating ?? 5.0;
    _hasDiabetics = widget.currentPreference?.hasDiabetics ?? false;
    _pregnant = widget.currentPreference?.isPregnant ?? false;
    _notesController = TextEditingController(text: widget.currentPreference?.specialNotes ?? '');
  }

  @override
  void dispose() {
    _notesController.dispose();
    super.dispose();
  }

  void _savePreferences() {
    // Return updated preference object
    final updatedPreference = Preference(
      id: widget.currentPreference?.id ?? 0,
      spicyRating: _spicyRating,
      saltRating: _saltRating,
      budgetRating: _budgetRating,
      hasDiabetics: _hasDiabetics,
      isPregnant: _pregnant,
      specialNotes: _notesController.text,
    );
    Navigator.of(context).pop(updatedPreference);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      body: GlassStyles.glassContainer(
        blur: 25,
        borderRadius: BorderRadius.zero,
        width: double.infinity,
        height: double.infinity,
        child: Column(
          children: [
            const SizedBox(height: 40),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                children: [
                  IconButton(
                    icon: const Icon(Icons.close, color: Colors.white),
                    onPressed: () => Navigator.of(context).pop(),
                  ),
                  const SizedBox(width: 8),
                  Text('Edit Preferences', style: AppTextStyles.headlineSmall),
                ],
              ),
            ),
            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.all(24),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    CustomSlider(
                      label: 'Spicy Level',
                      value: _spicyRating,
                      min: 0,
                      max: 10,
                      onChanged: (val) => setState(() => _spicyRating = val),
                    ),
                    const SizedBox(height: 24),
                    CustomSlider(
                      label: 'Saltiness',
                      value: _saltRating,
                      min: 0,
                      max: 10,
                      onChanged: (val) => setState(() => _saltRating = val),
                    ),
                    const SizedBox(height: 24),
                    CustomSlider(
                      label: 'Budget (Price)',
                      value: _budgetRating,
                      min: 0,
                      max: 10,
                      onChanged: (val) => setState(() => _budgetRating = val),
                    ),
                    const SizedBox(height: 24),
                    _buildSwitch('Has Diabetics?', _hasDiabetics, (val) => setState(() => _hasDiabetics = val)),
                    const SizedBox(height: 16),
                    _buildSwitch('Is Pregnant?', _pregnant, (val) => setState(() => _pregnant = val)),
                    const SizedBox(height: 24),
                    GlassTextField(
                      controller: _notesController,
                      hintText: 'Any allergies or special requests?',
                      labelText: 'Special Notes',
                      maxLines: 3,
                    ),
                    const SizedBox(height: 40),
                    GlassButton(
                      text: 'Save Preferences',
                      onPressed: _savePreferences,
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

  Widget _buildSwitch(String label, bool value, ValueChanged<bool> onChanged) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
      decoration: BoxDecoration(
        color: AppColors.glass.withOpacity(0.05),
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: AppColors.glassBorder.withOpacity(0.3)),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label, style: AppTextStyles.titleMedium),
          Switch(
            value: value,
            onChanged: onChanged,
            activeColor: AppColors.primary,
            activeTrackColor: AppColors.primary.withOpacity(0.3),
            inactiveThumbColor: Colors.grey,
            inactiveTrackColor: Colors.black12,
          ),
        ],
      ),
    );
  }
}
