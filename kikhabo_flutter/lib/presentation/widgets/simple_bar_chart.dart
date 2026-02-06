import 'package:flutter/material.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../data/models/analytics_models.dart';

class SimpleBarChart extends StatelessWidget {
  final List<CostData> data;
  final String period;

  const SimpleBarChart({
    super.key,
    required this.data,
    required this.period,
  });

  @override
  Widget build(BuildContext context) {
    if (data.isEmpty) {
      return Center(
        child: Text(
          'No cost data available',
          style: TextStyle(color: AppColors.textSecondary),
        ),
      );
    }

    final maxY = data.map((e) => e.costPercentage).reduce((a, b) => a > b ? a : b);
    final chartMaxY = maxY > 100 ? maxY * 1.1 : 120.0;

    return Padding(
      padding: const EdgeInsets.only(top: 16, bottom: 8, right: 16),
      child: Column(
        children: [
          // Chart area
          Expanded(
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                // Y-axis labels
                SizedBox(
                  width: 50,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      _buildYLabel('${chartMaxY.toInt()}%'),
                      _buildYLabel('100%'),
                      _buildYLabel('80%'),
                      _buildYLabel('60%'),
                      _buildYLabel('40%'),
                      _buildYLabel('20%'),
                      _buildYLabel('0%'),
                    ],
                  ),
                ),
                const SizedBox(width: 8),
                // Chart with grid
                Expanded(
                  child: Stack(
                    children: [
                      // Grid lines
                      Column(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: List.generate(7, (index) {
                          final isHundred = index == (chartMaxY > 100 ? 1 : 2);
                          return Container(
                            height: 1,
                            color: isHundred
                                ? Colors.red.withOpacity(0.6)
                                : AppColors.glassBorder.withOpacity(0.3),
                          );
                        }),
                      ),
                      // Bars
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: data.map((item) {
                          final heightPercent = item.costPercentage / chartMaxY;
                          final isOverBudget = item.costPercentage > 100;
                          
                          return Expanded(
                            child: Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 4),
                              child: GestureDetector(
                                onTap: () {
                                  // Show tooltip
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    SnackBar(
                                      content: Text(
                                        '${item.period}: ${item.costPercentage.toStringAsFixed(1)}%',
                                      ),
                                      duration: const Duration(seconds: 1),
                                      backgroundColor: AppColors.primary,
                                    ),
                                  );
                                },
                                child: FractionallySizedBox(
                                  heightFactor: heightPercent.clamp(0.0, 1.0),
                                  alignment: Alignment.bottomCenter,
                                  child: Container(
                                    decoration: BoxDecoration(
                                      color: isOverBudget
                                          ? Colors.red.shade400
                                          : AppColors.accent,
                                      borderRadius: const BorderRadius.vertical(
                                        top: Radius.circular(6),
                                      ),
                                    ),
                                  ),
                                ),
                              ),
                            ),
                          );
                        }).toList(),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(height: 8),
          // X-axis labels
          Row(
            children: [
              const SizedBox(width: 58),
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: data.map((item) {
                    return Expanded(
                      child: Center(
                        child: Text(
                          _getBottomLabel(item.period),
                          style: AppTextStyles.labelSmall.copyWith(
                            color: AppColors.textSecondary,
                          ),
                        ),
                      ),
                    );
                  }).toList(),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildYLabel(String text) {
    return Padding(
      padding: const EdgeInsets.only(right: 4),
      child: Text(
        text,
        style: AppTextStyles.labelSmall.copyWith(
          color: AppColors.textSecondary,
        ),
      ),
    );
  }

  String _getBottomLabel(String period) {
    if (this.period == 'daily') {
      try {
        final date = DateTime.parse(period);
        const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
        return days[date.weekday - 1];
      } catch (e) {
        return period.substring(period.length > 3 ? period.length - 3 : 0);
      }
    } else if (this.period == 'weekly') {
      return period.replaceAll('Week ', 'W');
    } else {
      return period.substring(0, period.length > 3 ? 3 : period.length);
    }
  }
}
