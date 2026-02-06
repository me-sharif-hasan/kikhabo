import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../core/theme/glass_styles.dart';
import '../../data/models/meal.dart';
import '../../domain/providers/meal_provider.dart';
import 'glass_button.dart';

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
  bool _isExpanded = false;
  bool _showNoteField = false;
  int _currentRating = 0;
  final TextEditingController _noteController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _currentRating = widget.meal.rating?.toInt() ?? 0;
    _noteController.text = widget.meal.userNote ?? '';
  }

  @override
  void dispose() {
    _noteController.dispose();
    super.dispose();
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
          rating: _currentRating > 0 ? _currentRating : null,
          note: _noteController.text.isNotEmpty ? _noteController.text : null,
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
      rating: rating,
      status: widget.meal.mealStatus,
      note: _noteController.text.isNotEmpty ? _noteController.text : null,
    );
  }

  @override
  Widget build(BuildContext context) {
    // Handle both groceries (from meal planning) and groceryNames (from meal history)
    final hasGroceries = widget.meal.groceries != null && widget.meal.groceries!.isNotEmpty;
    final hasGroceryNames = widget.meal.groceryNames != null && widget.meal.groceryNames!.isNotEmpty;
    
    final groceriesToShow = hasGroceries
        ? (_isExpanded ? widget.meal.groceries! : widget.meal.groceries!.take(3).toList())
        : [];
    final groceryNamesToShow = hasGroceryNames
        ? (_isExpanded ? widget.meal.groceryNames! : widget.meal.groceryNames!.take(3).toList())
        : [];
    
    final hasMoreItems = hasGroceries
        ? (widget.meal.groceries!.length > 3)
        : (hasGroceryNames && widget.meal.groceryNames!.length > 3);
    final totalItems = hasGroceries
        ? widget.meal.groceries!.length
        : (hasGroceryNames ? widget.meal.groceryNames!.length : 0);

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
          child: Padding(
            padding: const EdgeInsets.all(16),
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

                // Ingredients
                if (widget.meal.ingredients != null && widget.meal.ingredients!.isNotEmpty)
                  Text(
                    'Ingredients: ${widget.meal.ingredients}',
                    style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                    maxLines: _isExpanded ? null : 2,
                    overflow: _isExpanded ? null : TextOverflow.ellipsis,
                  ),
                const SizedBox(height: 12),
                const Divider(color: Colors.white10),
                const SizedBox(height: 12),

                // Rating Stars
                Row(
                  children: [
                    Text(
                      'Rating: ',
                      style: AppTextStyles.labelMedium.copyWith(color: AppColors.textSecondary),
                    ),
                    ...List.generate(5, (index) {
                      return GestureDetector(
                        onTap: () => _updateRating(index + 1),
                        child: Icon(
                          index < _currentRating ? Icons.star : Icons.star_border,
                          color: AppColors.accent,
                          size: 24,
                        ),
                      );
                    }),
                  ],
                ),
                const SizedBox(height: 12),

                // Note Field (Expandable)
                InkWell(
                  onTap: () {
                    setState(() {
                      _showNoteField = !_showNoteField;
                    });
                  },
                  child: Row(
                    children: [
                      Icon(
                        _showNoteField ? Icons.expand_less : Icons.expand_more,
                        color: AppColors.primaryLight,
                        size: 20,
                      ),
                      const SizedBox(width: 4),
                      Text(
                        _showNoteField ? 'Hide Note' : 'Add Note',
                        style: AppTextStyles.labelSmall.copyWith(
                          color: AppColors.primaryLight,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ],
                  ),
                ),
                if (_showNoteField) ...[
                  const SizedBox(height: 8),
                  TextField(
                    controller: _noteController,
                    maxLines: 3,
                    style: AppTextStyles.bodySmall,
                    decoration: InputDecoration(
                      hintText: 'Add your feedback...',
                      hintStyle: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                      filled: true,
                      fillColor: AppColors.glass.withOpacity(0.1),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(12),
                        borderSide: BorderSide(color: AppColors.glassBorder),
                      ),
                      enabledBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(12),
                        borderSide: BorderSide(color: AppColors.glassBorder),
                      ),
                      focusedBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(12),
                        borderSide: BorderSide(color: AppColors.primary, width: 2),
                      ),
                    ),
                  ),
                ],
                const SizedBox(height: 12),

                // Status Buttons
                Row(
                  children: [
                    Expanded(
                      child: GlassButton(
                        text: widget.meal.mealStatus == 'TAKEN' ? 'Unmark as Taken' : 'Mark as Taken',
                        height: 40,
                        gradient: const LinearGradient(
                          colors: [AppColors.primary, AppColors.primaryLight],
                        ),
                        onPressed: () => _updateStatus(
                          widget.meal.mealStatus == 'TAKEN' ? 'PLANNED' : 'TAKEN',
                        ),
                      ),
                    ),
                    const SizedBox(width: 8),
                    Expanded(
                      child: GlassButton(
                        text: 'Skip',
                        height: 40,
                        gradient: const LinearGradient(
                          colors: [AppColors.accent, AppColors.accentDark],
                        ),
                        onPressed: () => _updateStatus('SKIPPED'),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 12),
                const Divider(color: Colors.white10),
                const SizedBox(height: 8),

                // Groceries
                Text(
                  'Groceries:',
                  style: AppTextStyles.labelMedium.copyWith(color: AppColors.primaryLight),
                ),
                const SizedBox(height: 4),
                // Display groceries with amounts (from meal planning)
                ...groceriesToShow.map((g) => Padding(
                      padding: const EdgeInsets.only(bottom: 2),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Expanded(
                            child: Text(
                              g.name,
                              style: AppTextStyles.bodySmall,
                              overflow: TextOverflow.ellipsis,
                            ),
                          ),
                          Text(
                            '${g.amountInGm}g',
                            style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                          ),
                        ],
                      ),
                    )),
                // Display grocery names only (from meal history)
                ...groceryNamesToShow.map((name) => Padding(
                      padding: const EdgeInsets.only(bottom: 2),
                      child: Row(
                        children: [
                          Text(
                            '• ',
                            style: AppTextStyles.bodySmall.copyWith(color: AppColors.primaryLight),
                          ),
                          Expanded(
                            child: Text(
                              name,
                              style: AppTextStyles.bodySmall,
                              overflow: TextOverflow.ellipsis,
                            ),
                          ),
                        ],
                      ),
                    )),
                if (hasMoreItems)
                  InkWell(
                    onTap: () {
                      setState(() {
                        _isExpanded = !_isExpanded;
                      });
                    },
                    child: Padding(
                      padding: const EdgeInsets.only(top: 4),
                      child: Row(
                        children: [
                          Text(
                            _isExpanded
                                ? 'Show less'
                                : '+ ${totalItems - 3} more items',
                            style: AppTextStyles.labelSmall.copyWith(
                              color: AppColors.primaryLight,
                              fontWeight: FontWeight.w600,
                            ),
                          ),
                          Icon(
                            _isExpanded ? Icons.expand_less : Icons.expand_more,
                            color: AppColors.primaryLight,
                            size: 16,
                          ),
                        ],
                      ),
                    ),
                  ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
