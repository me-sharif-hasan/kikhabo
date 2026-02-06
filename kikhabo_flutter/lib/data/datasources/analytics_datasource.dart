import 'package:dio/dio.dart';
import '../../core/constants/api_constants.dart';
import '../models/analytics_models.dart';

/// Data source for analytics-related API calls
class AnalyticsDataSource {
  final Dio _dio;

  AnalyticsDataSource(this._dio);

  /// Get daily energy consumption data
  Future<List<EnergyData>> getEnergyDaily({
    int? days,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (days != null) params['days'] = days;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.energyDaily,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => EnergyData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Get weekly energy consumption data
  Future<List<EnergyData>> getEnergyWeekly({
    int? weeks,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (weeks != null) params['weeks'] = weeks;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.energyWeekly,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => EnergyData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Get monthly energy consumption data
  Future<List<EnergyData>> getEnergyMonthly({
    int? months,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (months != null) params['months'] = months;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.energyMonthly,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => EnergyData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Get daily cost data
  Future<List<CostData>> getCostDaily({
    int? days,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (days != null) params['days'] = days;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.costDaily,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => CostData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Get weekly cost data
  Future<List<CostData>> getCostWeekly({
    int? weeks,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (weeks != null) params['weeks'] = weeks;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.costWeekly,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => CostData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Get monthly cost data
  Future<List<CostData>> getCostMonthly({
    int? months,
    String? startDate,
    String? endDate,
  }) async {
    try {
      final params = <String, dynamic>{};
      if (months != null) params['months'] = months;
      if (startDate != null) params['startDate'] = startDate;
      if (endDate != null) params['endDate'] = endDate;

      final response = await _dio.get(
        ApiConstants.costMonthly,
        queryParameters: params,
      );

      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        if (data is List) {
          return data.map((e) => CostData.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }
}
