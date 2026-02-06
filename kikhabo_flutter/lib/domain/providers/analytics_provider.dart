import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/datasources/analytics_datasource.dart';
import '../../data/models/analytics_models.dart';
import '../../data/repositories/analytics_repository.dart';
import 'auth_provider.dart';

/// Provider for analytics data source
final analyticsDataSourceProvider = Provider<AnalyticsDataSource>((ref) {
  final dio = ref.watch(dioClientProvider).dio;
  return AnalyticsDataSource(dio);
});

/// Provider for analytics repository
final analyticsRepositoryProvider = Provider<AnalyticsRepository>((ref) {
  final dataSource = ref.watch(analyticsDataSourceProvider);
  return AnalyticsRepository(dataSource);
});

/// Provider for energy analytics data
final energyAnalyticsProvider = FutureProvider.family<List<EnergyData>, AnalyticsParams>((ref, params) async {
  final repository = ref.watch(analyticsRepositoryProvider);
  return repository.getEnergyData(
    period: params.period,
    count: params.count,
    startDate: params.startDate,
    endDate: params.endDate,
  );
});

/// Provider for cost analytics data
final costAnalyticsProvider = FutureProvider.family<List<CostData>, AnalyticsParams>((ref, params) async {
  final repository = ref.watch(analyticsRepositoryProvider);
  return repository.getCostData(
    period: params.period,
    count: params.count,
    startDate: params.startDate,
    endDate: params.endDate,
  );
});
