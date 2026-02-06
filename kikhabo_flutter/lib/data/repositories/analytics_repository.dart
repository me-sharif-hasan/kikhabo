import 'package:dio/dio.dart';
import '../datasources/analytics_datasource.dart';
import '../models/analytics_models.dart';

/// Repository for analytics data
class AnalyticsRepository {
  final AnalyticsDataSource _dataSource;

  AnalyticsRepository(this._dataSource);

  /// Get energy consumption data based on period
  Future<List<EnergyData>> getEnergyData({
    required String period, // 'daily', 'weekly', 'monthly'
    int? count,
    String? startDate,
    String? endDate,
  }) async {
    try {
      switch (period) {
        case 'daily':
          return await _dataSource.getEnergyDaily(
            days: count ?? 7,
            startDate: startDate,
            endDate: endDate,
          );
        case 'weekly':
          return await _dataSource.getEnergyWeekly(
            weeks: count ?? 4,
            startDate: startDate,
            endDate: endDate,
          );
        case 'monthly':
          return await _dataSource.getEnergyMonthly(
            months: count ?? 6,
            startDate: startDate,
            endDate: endDate,
          );
        default:
          throw Exception('Invalid period: $period');
      }
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  /// Get cost data based on period
  Future<List<CostData>> getCostData({
    required String period, // 'daily', 'weekly', 'monthly'
    int? count,
    String? startDate,
    String? endDate,
  }) async {
    try {
      switch (period) {
        case 'daily':
          return await _dataSource.getCostDaily(
            days: count ?? 7,
            startDate: startDate,
            endDate: endDate,
          );
        case 'weekly':
          return await _dataSource.getCostWeekly(
            weeks: count ?? 4,
            startDate: startDate,
            endDate: endDate,
          );
        case 'monthly':
          return await _dataSource.getCostMonthly(
            months: count ?? 6,
            startDate: startDate,
            endDate: endDate,
          );
        default:
          throw Exception('Invalid period: $period');
      }
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  String _mapError(dynamic error) {
    if (error is DioException) {
      switch (error.type) {
        case DioExceptionType.connectionTimeout:
        case DioExceptionType.sendTimeout:
        case DioExceptionType.receiveTimeout:
          return 'Connection timeout. Please check your internet connection.';
        case DioExceptionType.badResponse:
          return error.response?.data['message'] ?? 'Server error occurred';
        case DioExceptionType.cancel:
          return 'Request cancelled';
        default:
          return 'Network error occurred';
      }
    }
    return error.toString();
  }
}
