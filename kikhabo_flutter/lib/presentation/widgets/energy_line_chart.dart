import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../data/models/analytics_models.dart';

class EnergyLineChart extends StatelessWidget {
  final List<EnergyData> data;
  final String period; // 'daily', 'weekly', 'monthly'

  const EnergyLineChart({
    super.key,
    required this.data,
    required this.period,
  });

  @override
  Widget build(BuildContext context) {
    if (data.isEmpty) {
      return Center(
        child: Text(
          'No energy data available',
          style: TextStyle(color: AppColors.textSecondary),
        ),
      );
    }

    final spots = data.asMap().entries.map((entry) {
      return FlSpot(entry.key.toDouble(), entry.value.totalEnergy);
    }).toList();

    final maxY = data.map((e) => e.totalEnergy).reduce((a, b) => a > b ? a : b);
    final minY = data.map((e) => e.totalEnergy).reduce((a, b) => a < b ? a : b);
    final range = maxY - minY;
    
    // Ensure non-zero interval for grid lines
    final interval = range > 0 ? range / 4 : maxY / 4;
    final safeInterval = (interval > 0 ? interval : 100.0).toDouble();

    return Padding(
      padding: const EdgeInsets.only(right: 16, top: 16, bottom: 8),
      child: LineChart(
        LineChartData(
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
                interval: 1,
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
                    '${value.toInt()}',
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
          minX: 0,
          maxX: (data.length - 1).toDouble(),
          minY: minY * 0.9,
          maxY: maxY * 1.1,
          lineBarsData: [
            LineChartBarData(
              spots: spots,
              isCurved: true,
              color: AppColors.primary,
              barWidth: 3,
              isStrokeCapRound: true,
              dotData: FlDotData(
                show: true,
                getDotPainter: (spot, percent, barData, index) {
                  return FlDotCirclePainter(
                    radius: 5,
                    color: AppColors.primary,
                    strokeWidth: 2,
                    strokeColor: Colors.white,
                  );
                },
              ),
              belowBarData: BarAreaData(
                show: true,
                color: AppColors.primary.withOpacity(0.2),
              ),
            ),
          ],
          lineTouchData: LineTouchData(
            touchTooltipData: LineTouchTooltipData(
              getTooltipItems: (touchedSpots) {
                return touchedSpots.map((spot) {
                  return LineTooltipItem(
                    '${spot.y.toInt()} kcal\n${data[spot.x.toInt()].period}',
                    const TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 12,
                    ),
                  );
                }).toList();
              },
            ),
          ),
        ),
      ),
    );
  }

  String _getBottomLabel(String period) {
    // For daily: show day abbreviation (Mon, Tue, etc.)
    // For weekly: show week number (W1, W2, etc.)
    // For monthly: show month abbreviation (Jan, Feb, etc.)
    
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
      // Monthly - show first 3 chars
      return period.substring(0, period.length > 3 ? 3 : period.length);
    }
  }
}
