import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../data/models/analytics_models.dart';

class CostBarChart extends StatelessWidget {
  final List<CostData> data;
  final String period; // 'daily', 'weekly', 'monthly'

  const CostBarChart({
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

    final barGroups = data.asMap().entries.map((entry) {
      final isOverBudget = entry.value.costPercentage > 100;
      return BarChartGroupData(
        x: entry.key,
        barRods: [
          BarChartRodData(
            toY: entry.value.costPercentage,
            color: isOverBudget ? Colors.red.shade400 : AppColors.accent,
            width: 20,
            borderRadius: const BorderRadius.vertical(top: Radius.circular(6)),
          ),
        ],
      );
    }).toList();

    final maxY = data.map((e) => e.costPercentage).reduce((a, b) => a > b ? a : b);
    final chartMaxY = (maxY > 100 ? maxY * 1.1 : 120.0).toDouble();
    
    // Ensure clean interval - minimum 20 for readability
    final safeInterval = 20.0;

    return Padding(
      padding: const EdgeInsets.only(right: 16, top: 16, bottom: 8),
      child: BarChart(
        BarChartData(
          alignment: BarChartAlignment.spaceAround,
          maxY: chartMaxY,
          minY: 0,
          gridData: FlGridData(
            show: true,
            drawVerticalLine: false,
            horizontalInterval: safeInterval,
            getDrawingHorizontalLine: (value) {
              return FlLine(
                color: AppColors.glassBorder.withOpacity(0.3),
                strokeWidth: 1,
              );
            },
          ),
          titlesData: FlTitlesData(
            show: true,
            rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            bottomTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                reservedSize: 30,
                getTitlesWidget: (value, meta) {
                  if (value.toInt() >= 0 && value.toInt() < data.length) {
                    final label = _getBottomLabel(data[value.toInt()].period);
                    return Padding(
                      padding: const EdgeInsets.only(top: 8.0),
                      child: Text(
                        label,
                        style: AppTextStyles.labelSmall.copyWith(
                          color: AppColors.textSecondary,
                        ),
                      ),
                    );
                  }
                  return const SizedBox();
                },
              ),
            ),
            leftTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                reservedSize: 50,
                interval: safeInterval,
                getTitlesWidget: (value, meta) {
                  return Text(
                    '${value.toInt()}%',
                    style: AppTextStyles.labelSmall.copyWith(
                      color: AppColors.textSecondary,
                    ),
                  );
                },
              ),
            ),
          ),
          borderData: FlBorderData(
            show: true,
            border: Border(
              bottom: BorderSide(color: AppColors.glassBorder.withOpacity(0.5)),
              left: BorderSide(color: AppColors.glassBorder.withOpacity(0.5)),
            ),
          ),
          barGroups: barGroups,
          // Budget reference line at 100%
          extraLinesData: ExtraLinesData(
            horizontalLines: [
              HorizontalLine(
                y: 100,
                color: Colors.red.withOpacity(0.6),
                strokeWidth: 2,
                dashArray: [5, 5],
                label: HorizontalLineLabel(
                  show: true,
                  alignment: Alignment.topRight,
                  padding: const EdgeInsets.only(right: 5, bottom: 5),
                  style: AppTextStyles.labelSmall.copyWith(
                    color: Colors.red,
                    fontWeight: FontWeight.bold,
                  ),
                  labelResolver: (line) => 'Budget',
                ),
              ),
            ],
          ),
          barTouchData: BarTouchData(
            touchTooltipData: BarTouchTooltipData(
              getTooltipItem: (group, groupIndex, rod, rodIndex) {
                return BarTooltipItem(
                  '${rod.toY.toStringAsFixed(1)}%\n${data[group.x].period}',
                  const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 12,
                  ),
                );
              },
            ),
          ),
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
