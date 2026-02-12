import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/meal.dart';
import '../../../domain/providers/meal_provider.dart';
import '../../widgets/glass_card.dart';

class MealDetailsScreen extends ConsumerStatefulWidget {
  final Meal meal;

  const MealDetailsScreen({super.key, required this.meal});

  @override
  ConsumerState<MealDetailsScreen> createState() => _MealDetailsScreenState();
}

class _MealDetailsScreenState extends ConsumerState<MealDetailsScreen> {
  late int _currentRating;
  late String _currentStatus;

  @override
  void initState() {
    super.initState();
    _currentRating = widget.meal.rating?.toInt() ?? 0;
    _currentStatus = widget.meal.mealStatus ?? 'PLANNED';
  }

  Color _getStatusColor(String status) {
    switch (status) {
      case 'TAKEN':
        return AppColors.primary;
      case 'SKIPPED':
        return AppColors.accent;
      default:
        return Colors.grey;
    }
  }

  String _getFormattedDate() {
    if (widget.meal.timestamp == null) return 'Today';
    final date = DateTime.fromMillisecondsSinceEpoch(widget.meal.timestamp! * 1000);
    return '${date.day}/${date.month}/${date.year}';
  }

  Future<void> _updateMealStatus() async {
    if (widget.meal.id == null) {
      _showSnackBar('Cannot update meal without ID', isError: true);
      return;
    }

    final success = await ref.read(mealPlanningProvider.notifier).updateMealStatus(
      mealId: widget.meal.id!,
      status: _currentStatus,
      rating: _currentRating,
    );

    if (mounted) {
      if (success) {
        // Invalidate meal history provider to force refresh with updated data
        ref.invalidate(mealHistoryProvider);
      }
      
      _showSnackBar(
        success ? 'Meal updated successfully!' : 'Failed to update meal',
        isError: !success,
      );
    }
  }

  void _showSnackBar(String message, {bool isError = false}) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: isError ? AppColors.error : AppColors.primary,
        behavior: SnackBarBehavior.floating,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final hasGroceries = widget.meal.groceries != null && widget.meal.groceries!.isNotEmpty;
    final hasGroceryNames = widget.meal.groceryNames != null && widget.meal.groceryNames!.isNotEmpty;

    return Scaffold(
      backgroundColor: Colors.transparent,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () => Navigator.of(context).pop(),
        ),
        title: Text(
          'Meal Details',
          style: AppTextStyles.titleLarge,
        ),
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Header Card
              GlassCard(
                blur: 10,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Expanded(
                          child: Text(
                            widget.meal.mealName,
                            style: AppTextStyles.headlineSmall,
                          ),
                        ),
                        const SizedBox(width: 8),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                          decoration: BoxDecoration(
                            color: AppColors.primary.withOpacity(0.2),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Text(
                            '${widget.meal.totalEnergy} kcal',
                            style: AppTextStyles.labelMedium.copyWith(
                              color: AppColors.primaryLight,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    Row(
                      children: [
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                          decoration: BoxDecoration(
                            color: _getStatusColor(_currentStatus).withOpacity(0.2),
                            borderRadius: BorderRadius.circular(8),
                            border: Border.all(
                              color: _getStatusColor(_currentStatus),
                              width: 1,
                            ),
                          ),
                          child: Text(
                            _currentStatus,
                            style: AppTextStyles.labelSmall.copyWith(
                              color: _getStatusColor(_currentStatus),
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        const SizedBox(width: 12),
                        Icon(Icons.calendar_today, size: 16, color: AppColors.textSecondary),
                        const SizedBox(width: 4),
                        Text(
                          _getFormattedDate(),
                          style: AppTextStyles.bodySmall.copyWith(
                            color: AppColors.textSecondary,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 16),

              // Stats Grid
              GridView.count(
                crossAxisCount: 2,
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                crossAxisSpacing: 16,
                mainAxisSpacing: 16,
                childAspectRatio: 1.5,
                children: [
                  _buildStatCard(
                    'Energy',
                    '${widget.meal.totalEnergy} kcal',
                    Icons.local_fire_department,
                  ),
                  _buildStatCard(
                    'Rating',
                    _currentRating > 0 ? '$_currentRating/5' : 'Not rated',
                    Icons.star,
                  ),
                  _buildStatCard(
                    'Status',
                    _currentStatus,
                    Icons.check_circle_outline,
                  ),
                  _buildStatCard(
                    'Date',
                    _getFormattedDate(),
                    Icons.calendar_today,
                  ),
                ],
              ),
              const SizedBox(height: 16),

              // Groceries Section
              if (hasGroceries || hasGroceryNames) ...[
                GlassCard(
                  blur: 10,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(
                            Icons.shopping_basket,
                            color: AppColors.primaryLight,
                            size: 24,
                          ),
                          const SizedBox(width: 8),
                          Text(
                            'Groceries',
                            style: AppTextStyles.titleMedium,
                          ),
                        ],
                      ),
                      const SizedBox(height: 12),
                      const Divider(color: Colors.white10),
                      const SizedBox(height: 8),
                      
                      // Display detailed groceries (from meal planning)
                      if (hasGroceries)
                        ...widget.meal.groceries!.map((grocery) => Padding(
                          padding: const EdgeInsets.only(bottom: 8),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Expanded(
                                child: Text(
                                  grocery.name,
                                  style: AppTextStyles.bodyMedium,
                                ),
                              ),
                              const SizedBox(width: 8),
                              Text(
                                '${grocery.amountInGm}g',
                                style: AppTextStyles.bodySmall.copyWith(
                                  color: AppColors.textSecondary,
                                ),
                              ),
                              const SizedBox(width: 8),
                              Container(
                                padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                                decoration: BoxDecoration(
                                  color: AppColors.accent.withOpacity(0.2),
                                  borderRadius: BorderRadius.circular(6),
                                ),
                                child: Text(
                                  '₹${grocery.priceRatingOutOf10}',
                                  style: AppTextStyles.labelSmall.copyWith(
                                    color: AppColors.accent,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ))
                      
                      // Display grocery names only (from meal history)
                      else if (hasGroceryNames)
                        ...widget.meal.groceryNames!.map((name) => Padding(
                          padding: const EdgeInsets.only(bottom: 6),
                          child: Row(
                            children: [
                              Text(
                                '• ',
                                style: AppTextStyles.bodyMedium.copyWith(
                                  color: AppColors.primaryLight,
                                ),
                              ),
                              Expanded(
                                child: Text(
                                  name,
                                  style: AppTextStyles.bodyMedium,
                                ),
                              ),
                            ],
                          ),
                        )),
                    ],
                  ),
                ),
                const SizedBox(height: 16),
              ],

              // Notes Section
              if (widget.meal.note != null || widget.meal.userNote != null) ...[
                GlassCard(
                  blur: 10,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(
                            Icons.note_alt_outlined,
                            color: AppColors.primaryLight,
                            size: 24,
                          ),
                          const SizedBox(width: 8),
                          Text(
                            'Notes',
                            style: AppTextStyles.titleMedium,
                          ),
                        ],
                      ),
                      const SizedBox(height: 12),
                      const Divider(color: Colors.white10),
                      const SizedBox(height: 8),
                      
                      if (widget.meal.note != null) ...[
                        Text(
                          'AI Suggestion:',
                          style: AppTextStyles.labelMedium.copyWith(
                            color: AppColors.primaryLight,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          widget.meal.note!,
                          style: AppTextStyles.bodyMedium,
                        ),
                        if (widget.meal.userNote != null) const SizedBox(height: 12),
                      ],
                      
                      if (widget.meal.userNote != null) ...[
                        Text(
                          'Your Note:',
                          style: AppTextStyles.labelMedium.copyWith(
                            color: AppColors.accent,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          widget.meal.userNote!,
                          style: AppTextStyles.bodyMedium,
                        ),
                      ],
                    ],
                  ),
                ),
                const SizedBox(height: 16),
              ],

              // Rating Section
              GlassCard(
                blur: 10,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Rate this meal',
                      style: AppTextStyles.titleMedium,
                    ),
                    const SizedBox(height: 12),
                    Row(
                      children: List.generate(5, (index) {
                        return GestureDetector(
                          onTap: () {
                            setState(() {
                              _currentRating = index + 1;
                            });
                          },
                          child: Padding(
                            padding: const EdgeInsets.only(right: 8),
                            child: Icon(
                              index < _currentRating ? Icons.star : Icons.star_border,
                              color: AppColors.accent,
                              size: 32,
                            ),
                          ),
                        );
                      }),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 16),

              // Status Toggle Buttons
              GlassCard(
                blur: 10,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Meal Status',
                      style: AppTextStyles.titleMedium,
                    ),
                    const SizedBox(height: 12),
                    Row(
                      children: [
                        Expanded(
                          child: _buildStatusButton(
                            'TAKEN',
                            Icons.check_circle,
                            AppColors.primary,
                          ),
                        ),
                        const SizedBox(width: 12),
                        Expanded(
                          child: _buildStatusButton(
                            'SKIPPED',
                            Icons.cancel,
                            AppColors.accent,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 16),

              // Update Button
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: _updateMealStatus,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    padding: const EdgeInsets.symmetric(vertical: 16),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                  ),
                  child: Text(
                    'Save Changes',
                    style: AppTextStyles.titleMedium.copyWith(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildStatCard(String label, String value, IconData icon) {
    return GlassCard(
      blur: 10,
      padding: const EdgeInsets.all(16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(icon, color: AppColors.primaryLight, size: 24),
          const SizedBox(height: 8),
          Text(
            label,
            style: AppTextStyles.labelSmall.copyWith(
              color: AppColors.textSecondary,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            value,
            style: AppTextStyles.bodyMedium,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          ),
        ],
      ),
    );
  }

  Widget _buildStatusButton(String status, IconData icon, Color color) {
    final isSelected = _currentStatus == status;
    
    return InkWell(
      onTap: () {
        setState(() {
          _currentStatus = status;
        });
      },
      borderRadius: BorderRadius.circular(12),
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 12),
        decoration: BoxDecoration(
          gradient: isSelected
              ? LinearGradient(
                  colors: [color, color.withOpacity(0.7)],
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                )
              : null,
          color: isSelected ? null : AppColors.surface.withOpacity(0.3),
          borderRadius: BorderRadius.circular(12),
          border: Border.all(
            color: isSelected ? color : AppColors.glassBorder,
            width: 2,
          ),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              icon,
              color: isSelected ? Colors.white : color,
              size: 20,
            ),
            const SizedBox(width: 8),
            Text(
              status,
              style: AppTextStyles.labelMedium.copyWith(
                color: isSelected ? Colors.white : AppColors.textSecondary,
                fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
