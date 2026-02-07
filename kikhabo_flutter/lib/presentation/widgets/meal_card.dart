import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../core/theme/glass_styles.dart';
import '../../data/models/meal.dart';
import '../../domain/providers/meal_provider.dart';

class MealCard extends ConsumerStatefulWidget {
  final Meal meal;

  const MealCard({
    super.key,
    required this.meal,
  });

  @override
  ConsumerState<MealCard> createState() => _MealCardState();
}

class _MealCardState extends ConsumerState<MealCard> {
  int _currentRating = 0;

  @override
  void initState() {
    super.initState();
    _currentRating = widget.meal.rating?.toInt() ?? 0;
  }

  Color _getStatusColor() {
    switch (widget.meal.mealStatus) {
      case 'TAKEN':
        return AppColors.primary;
      case 'SKIPPED':
        return AppColors.accent;
      default:
        return Colors.grey;
    }
  }

  Future<void> _updateStatus(String status) async {
    if (widget.meal.id == null) return;

    final success = await ref.read(mealPlanningProvider.notifier).updateMealStatus(
      mealId: widget.meal.id!,
      status: status,
    );

    if (mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(success ? 'Meal updated!' : 'Failed to update meal'),
          backgroundColor: success ? AppColors.primary : AppColors.error,
        ),
      );
    }
  }

  Future<void> _updateRating(int rating) async {
    if (widget.meal.id == null) return;

    setState(() {
      _currentRating = rating;
    });

    // Immediately persist rating to API
    await ref.read(mealPlanningProvider.notifier).updateMealStatus(
      mealId: widget.meal.id!,
      status: widget.meal.mealStatus ?? 'PLANNED',
      rating: rating,
    );
  }

  String _getFormattedDate() {
    if (widget.meal.timestamp == null) return 'Today';
    final date = DateTime.fromMillisecondsSinceEpoch(widget.meal.timestamp! * 1000);
    return '${date.day}/${date.month}/${date.year}';
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [
            AppColors.glass.withOpacity(0.1),
            AppColors.glass.withOpacity(0.05),
          ],
        ),
        borderRadius: BorderRadius.circular(20),
        border: Border.all(color: AppColors.primary.withOpacity(0.3), width: 1),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 10,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(20),
        child: BackdropFilter(
          filter: GlassStyles.blurFilter,
          child: Stack(
            children: [
              Material(
                color: Colors.transparent,
                child: InkWell(
                  onTap: () {
                    context.push('/dashboard/meal_details', extra: widget.meal);
                  },
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(16, 16, 16, 60),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        // Header: Meal Name + Energy + Status Badge
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Expanded(
                              child: Text(
                                widget.meal.mealName,
                                style: AppTextStyles.titleMedium.copyWith(fontWeight: FontWeight.bold),
                                maxLines: 2,
                                overflow: TextOverflow.ellipsis,
                              ),
                            ),
                        const SizedBox(width: 8),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            Container(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                              decoration: BoxDecoration(
                                color: AppColors.primary.withOpacity(0.2),
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: Text(
                                '${widget.meal.totalEnergy} kcal',
                                style: AppTextStyles.labelSmall.copyWith(color: AppColors.primaryLight),
                              ),
                            ),
                            if (widget.meal.mealStatus != null)
                              Container(
                                margin: const EdgeInsets.only(top: 4),
                                padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                                decoration: BoxDecoration(
                                  color: _getStatusColor().withOpacity(0.2),
                                  borderRadius: BorderRadius.circular(6),
                                  border: Border.all(color: _getStatusColor(), width: 1),
                                ),
                                child: Text(
                                  widget.meal.mealStatus!,
                                  style: AppTextStyles.labelSmall.copyWith(
                                    color: _getStatusColor(),
                                    fontSize: 10,
                                  ),
                                ),
                              ),
                          ],
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),

                    // Meal Date (Type removed as it's not available)
                    Row(
                      children: [
                         // Using primary color for date icon
                        Icon(Icons.calendar_today, size: 14, color: AppColors.textSecondary),
                        const SizedBox(width: 4),
                        Text(
                          _getFormattedDate(),
                          style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                        ),
                      ],
                    ),

                    // Rating
                    if (widget.meal.rating != null && widget.meal.rating! > 0) ...[
                      const SizedBox(height: 8),
                      Row(
                        children: List.generate(5, (index) {
                          return Padding(
                            padding: const EdgeInsets.only(right: 4),
                            child: Icon(
                              index < _currentRating ? Icons.star : Icons.star_border,
                              color: AppColors.accent,
                              size: 20,
                            ),
                          );
                        }),
                      ),
                    ],
                  ],
                ),
              ),
            ),
          ),
              // Floating Action Button - Bottom Right
              Positioned(
                bottom: 12,
                right: 12,
                child: GestureDetector(
                  onTap: () => _updateStatus(
                    widget.meal.mealStatus == 'TAKEN' ? 'PLANNED' : 'TAKEN',
                  ),
                  child: Container(
                    width: 52,
                    height: 52,
                    decoration: BoxDecoration(
                      gradient: widget.meal.mealStatus == 'TAKEN'
                          ? LinearGradient(
                              colors: [AppColors.primary, AppColors.primary.withOpacity(0.8)],
                              begin: Alignment.topLeft,
                              end: Alignment.bottomRight,
                            )
                          : null,
                      color: widget.meal.mealStatus != 'TAKEN' 
                          ? AppColors.surface.withOpacity(0.3)
                          : null,
                      shape: BoxShape.circle,
                      border: Border.all(
                        color: AppColors.primary,
                        width: 2.5,
                      ),
                      boxShadow: [
                        BoxShadow(
                          color: AppColors.primary.withOpacity(0.4),
                          blurRadius: 12,
                          offset: const Offset(0, 3),
                        ),
                      ],
                    ),
                    child: Icon(
                      widget.meal.mealStatus == 'TAKEN' 
                          ? Icons.check_circle 
                          : Icons.check_circle_outline,
                      color: widget.meal.mealStatus == 'TAKEN' 
                          ? Colors.white 
                          : AppColors.primaryLight,
                      size: 30,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
