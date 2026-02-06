import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/analytics_models.dart';
import '../../../domain/providers/analytics_provider.dart';
import '../../widgets/simple_bar_chart.dart';
import '../../widgets/energy_line_chart.dart';
import '../../widgets/glass_card.dart';

class MealStatisticsScreen extends ConsumerStatefulWidget {
  const MealStatisticsScreen({super.key});

  @override
  ConsumerState<MealStatisticsScreen> createState() => _MealStatisticsScreenState();
}

class _MealStatisticsScreenState extends ConsumerState<MealStatisticsScreen> {
  String _selectedPeriod = 'daily'; // 'daily', 'weekly', 'monthly'

  AnalyticsParams get _currentParams {
    switch (_selectedPeriod) {
      case 'daily':
        return const AnalyticsParams(period: 'daily', count: 7);
      case 'weekly':
        return const AnalyticsParams(period: 'weekly', count: 4);
      case 'monthly':
        return const AnalyticsParams(period: 'monthly', count: 6);
      default:
        return const AnalyticsParams(period: 'daily', count: 7);
    }
  }

  @override
  Widget build(BuildContext context) {
    final energyAsync = ref.watch(energyAnalyticsProvider(_currentParams));
    final costAsync = ref.watch(costAnalyticsProvider(_currentParams));

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: Column(
          children: [
            const SizedBox(height: 20),
            // Header
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: GlassCard(
                blur: 10,
                child: Row(
                  children: [
                    Text('Statistics', style: AppTextStyles.titleLarge),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 16),
            
            // Period Selector
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Row(
                children: [
                  _PeriodChip(
                    label: 'Daily',
                    isSelected: _selectedPeriod == 'daily',
                    onTap: () => setState(() => _selectedPeriod = 'daily'),
                  ),
                  const SizedBox(width: 8),
                  _PeriodChip(
                    label: 'Weekly',
                    isSelected: _selectedPeriod == 'weekly',
                    onTap: () => setState(() => _selectedPeriod = 'weekly'),
                  ),
                  const SizedBox(width: 8),
                  _PeriodChip(
                    label: 'Monthly',
                    isSelected: _selectedPeriod == 'monthly',
                    onTap: () => setState(() => _selectedPeriod = 'monthly'),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 16),
            
            // Charts
            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Energy Chart
                    GlassCard(
                      blur: 10,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            children: [
                              Icon(Icons.local_fire_department, 
                                color: AppColors.primary, size: 20),
                              const SizedBox(width: 8),
                              Text(
                                'Energy Consumption',
                                style: AppTextStyles.titleMedium,
                              ),
                            ],
                          ),
                          const SizedBox(height: 8),
                          Text(
                            _getPeriodSubtitle(_selectedPeriod),
                            style: AppTextStyles.bodySmall.copyWith(
                              color: AppColors.textSecondary,
                            ),
                          ),
                          const SizedBox(height: 16),
                          SizedBox(
                            height: 200,
                            child: energyAsync.when(
                              data: (data) => EnergyLineChart(
                                data: data,
                                period: _selectedPeriod,
                              ),
                              loading: () => const Center(
                                child: CircularProgressIndicator(
                                  color: AppColors.primary,
                                ),
                              ),
                              error: (error, _) => Center(
                                child: Text(
                                  'Failed to load energy data',
                                  style: AppTextStyles.bodySmall.copyWith(
                                    color: AppColors.error,
                                  ),
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                    
                    // Cost Chart
                    GlassCard(
                      blur: 10,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            children: [
                              Icon(Icons.attach_money, 
                                color: AppColors.accent, size: 20),
                              const SizedBox(width: 8),
                              Text(
                                'Meal Cost (% of Budget)',
                                style: AppTextStyles.titleMedium,
                              ),
                            ],
                          ),
                          const SizedBox(height: 8),
                          Text(
                            _getPeriodSubtitle(_selectedPeriod),
                            style: AppTextStyles.bodySmall.copyWith(
                              color: AppColors.textSecondary,
                            ),
                          ),
                          const SizedBox(height: 16),
                          SizedBox(
                            height: 200,
                            child: costAsync.when(
                              data: (data) => SimpleBarChart(
                                data: data,
                                period: _selectedPeriod,
                              ),
                              loading: () => const Center(
                                child: CircularProgressIndicator(
                                  color: AppColors.accent,
                                ),
                              ),
                              error: (error, _) => Center(
                                child: Text(
                                  'Failed to load cost data',
                                  style: AppTextStyles.bodySmall.copyWith(
                                    color: AppColors.error,
                                  ),
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                    
                    // Info Card
                    GlassCard(
                      blur: 10,
                      child: Row(
                        children: [
                          Icon(Icons.info_outline, 
                            color: AppColors.primaryLight, size: 20),
                          const SizedBox(width: 12),
                          Expanded(
                            child: Text(
                              'Statistics are based on meals marked as "TAKEN"',
                              style: AppTextStyles.bodySmall.copyWith(
                                color: AppColors.textSecondary,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 16),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  String _getPeriodSubtitle(String period) {
    switch (period) {
      case 'daily':
        return 'Last 7 days';
      case 'weekly':
        return 'Last 4 weeks';
      case 'monthly':
        return 'Last 6 months';
      default:
        return '';
    }
  }
}

class _PeriodChip extends StatelessWidget {
  final String label;
  final bool isSelected;
  final VoidCallback onTap;

  const _PeriodChip({
    required this.label,
    required this.isSelected,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(20),
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        decoration: BoxDecoration(
          gradient: isSelected
              ? const LinearGradient(
                  colors: [AppColors.primary, AppColors.primaryLight],
                )
              : null,
          color: isSelected ? null : AppColors.surface.withOpacity(0.3),
          borderRadius: BorderRadius.circular(20),
          border: Border.all(
            color: isSelected ? AppColors.primary : AppColors.glassBorder,
            width: 1,
          ),
        ),
        child: Text(
          label,
          style: AppTextStyles.labelMedium.copyWith(
            color: isSelected ? Colors.white : AppColors.textSecondary,
            fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
          ),
        ),
      ),
    );
  }
}
