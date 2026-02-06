import 'package:freezed_annotation/freezed_annotation.dart';

part 'analytics_models.freezed.dart';
part 'analytics_models.g.dart';

/// Represents energy consumption data for a specific period
@freezed
abstract class EnergyData with _$EnergyData {
  const factory EnergyData({
    required String period, // Date or period label (e.g., "2024-02-06", "Week 1")
    required double totalEnergy, // Total energy in kcal
  }) = _EnergyData;

  factory EnergyData.fromJson(Map<String, dynamic> json) => _$EnergyDataFromJson(json);
}

/// Represents cost data for a specific period
@freezed
abstract class CostData with _$CostData {
  const factory CostData({
    required String period, // Date or period label
    required double costPercentage, // Cost as percentage of budget
  }) = _CostData;

  factory CostData.fromJson(Map<String, dynamic> json) => _$CostDataFromJson(json);
}

/// Parameters for analytics queries
class AnalyticsParams {
  final String period; // 'daily', 'weekly', 'monthly'
  final int? count; // Number of periods (days, weeks, months)
  final String? startDate; // YYYY-MM-DD
  final String? endDate; // YYYY-MM-DD

  const AnalyticsParams({
    required this.period,
    this.count,
    this.startDate,
    this.endDate,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AnalyticsParams &&
          runtimeType == other.runtimeType &&
          period == other.period &&
          count == other.count &&
          startDate == other.startDate &&
          endDate == other.endDate;

  @override
  int get hashCode =>
      period.hashCode ^ count.hashCode ^ startDate.hashCode ^ endDate.hashCode;
}
