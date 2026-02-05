import 'package:flutter/material.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../core/theme/glass_styles.dart';
import '../../data/models/meal.dart';

class MealCard extends StatefulWidget {
  final Meal meal;
  final VoidCallback? onTap;
  final VoidCallback? onRate;

  const MealCard({
    super.key,
    required this.meal,
    this.onTap,
    this.onRate,
  });

  @override
  State<MealCard> createState() => _MealCardState();
}

class _MealCardState extends State<MealCard> {
  bool _isExpanded = false;

  @override
  Widget build(BuildContext context) {
    final groceriesToShow = _isExpanded 
        ? (widget.meal.groceries ?? []) 
        : (widget.meal.groceries ?? []).take(3).toList();
    final hasMoreItems = (widget.meal.groceries?.length ?? 0) > 3;

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
          child: InkWell(
            onTap: widget.onTap,
            borderRadius: BorderRadius.circular(20),
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
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
                    ],
                  ),
                  const SizedBox(height: 8),
                  if (widget.meal.ingredients != null && widget.meal.ingredients!.isNotEmpty)
                    Text(
                      'Ingredients: ${widget.meal.ingredients}',
                      style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary),
                      maxLines: _isExpanded ? null : 2,
                      overflow: _isExpanded ? null : TextOverflow.ellipsis,
                    ),
                  const SizedBox(height: 12),
                  const Divider(color: Colors.white10),
                  const SizedBox(height: 8),
                  Text(
                    'Groceries:',
                    style: AppTextStyles.labelMedium.copyWith(color: AppColors.primaryLight),
                  ),
                  const SizedBox(height: 4),
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
                              style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)
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
                                  : '+ ${(widget.meal.groceries?.length ?? 0) - 3} more items',
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
                  const SizedBox(height: 12),
                  Row(
                     mainAxisAlignment: MainAxisAlignment.end,
                     children: [
                        if (widget.onRate != null)
                          IconButton(
                            icon: const Icon(Icons.star_border, color: AppColors.accent),
                            onPressed: widget.onRate,
                            tooltip: 'Rate Meal',
                          ),
                     ],
                  )
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
