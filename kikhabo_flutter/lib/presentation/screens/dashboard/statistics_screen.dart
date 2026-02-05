import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../widgets/glass_card.dart';

class StatisticsScreen extends StatefulWidget {
  const StatisticsScreen({super.key});

  @override
  State<StatisticsScreen> createState() => _StatisticsScreenState();
}

class _StatisticsScreenState extends State<StatisticsScreen> {
  // Mock Data for Calories (Line Chart)
  final List<FlSpot> _calorieData = [
    const FlSpot(1, 1800),
    const FlSpot(2, 2100),
    const FlSpot(3, 1950),
    const FlSpot(4, 2200),
    const FlSpot(5, 1750),
    const FlSpot(6, 2000),
    const FlSpot(7, 2300),
  ];

  // Mock Data for Costs (Bar Chart)
  final List<BarChartGroupData> _costData = [
    _makeBarData(0, 120),
    _makeBarData(1, 150),
    _makeBarData(2, 110),
    _makeBarData(3, 180),
    _makeBarData(4, 90),
  ];

  static BarChartGroupData _makeBarData(int x, double y) {
    return BarChartGroupData(
      x: x,
      barRods: [
        BarChartRodData(
          toY: y,
          gradient: LinearGradient(
              colors: [AppColors.accent, AppColors.accentDark],
              begin: Alignment.bottomCenter,
              end: Alignment.topCenter),
          width: 16,
          borderRadius: const BorderRadius.vertical(top: Radius.circular(6)),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(height: 20),
              GlassCard(
                blur: 10,
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Column(
                         crossAxisAlignment: CrossAxisAlignment.start,
                         children: [
                           Text('Analysis & Costs', style: AppTextStyles.titleLarge),
                           Text('Track your nutrition and spending', style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)),
                         ],
                      ),
                      const Icon(Icons.analytics_outlined, color: Colors.white54, size: 32)
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 24),
              
              // Calorie Intake Chart
              GlassCard(
                padding: const EdgeInsets.all(24),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Calorie Intake (Last 7 Days)', style: AppTextStyles.titleMedium),
                    const SizedBox(height: 24),
                    SizedBox(
                      height: 200,
                      child: LineChart(
                        LineChartData(
                          gridData: const FlGridData(show: false),
                          titlesData: FlTitlesData(
                            leftTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            bottomTitles: AxisTitles(
                              sideTitles: SideTitles(
                                showTitles: true,
                                getTitlesWidget: (value, meta) {
                                  switch (value.toInt()) {
                                    case 1: return const Text('Mon', style: TextStyle(color: Colors.white54, fontSize: 10));
                                    case 4: return const Text('Thu', style: TextStyle(color: Colors.white54, fontSize: 10));
                                    case 7: return const Text('Sun', style: TextStyle(color: Colors.white54, fontSize: 10));
                                  }
                                  return const SizedBox.shrink();
                                },
                              ),
                            ),
                          ),
                          borderData: FlBorderData(show: false),
                          lineBarsData: [
                            LineChartBarData(
                              spots: _calorieData,
                              isCurved: true,
                              gradient: const LinearGradient(colors: [AppColors.primary, AppColors.primaryLight]),
                              barWidth: 4,
                              isStrokeCapRound: true,
                              dotData: const FlDotData(show: false),
                              belowBarData: BarAreaData(
                                show: true,
                                gradient: LinearGradient(
                                  colors: [AppColors.primary.withOpacity(0.3), Colors.transparent],
                                  begin: Alignment.topCenter,
                                  end: Alignment.bottomCenter,
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
              
              const SizedBox(height: 24),

              // Monthly Costs Chart
              GlassCard(
                padding: const EdgeInsets.all(24),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text('Weekly Meal Costs', style: AppTextStyles.titleMedium),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
                          decoration: BoxDecoration(
                              color: AppColors.accent.withOpacity(0.2),
                              borderRadius: BorderRadius.circular(12),
                          ),
                          child: Text('Total: \$750', style: AppTextStyles.labelMedium.copyWith(color: AppColors.accentLight)),
                        ),
                      ],
                    ),
                    const SizedBox(height: 24),
                    SizedBox(
                      height: 200,
                      child: BarChart(
                        BarChartData(
                          gridData: const FlGridData(show: false),
                          titlesData: FlTitlesData(
                            leftTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
                            bottomTitles: AxisTitles(
                              sideTitles: SideTitles(
                                showTitles: true,
                                getTitlesWidget: (value, meta) {
                                  return Padding(
                                    padding: const EdgeInsets.only(top: 8.0),
                                    child: Text('Week ${value.toInt() + 1}', style: const TextStyle(color: Colors.white54, fontSize: 10)),
                                  );
                                },
                              ),
                            ),
                          ),
                          borderData: FlBorderData(show: false),
                          barGroups: _costData,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
