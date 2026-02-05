import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../../core/constants/api_constants.dart';
import '../../core/constants/app_constants.dart';

/// Single responsibility: Managing API connection and interceptors.
class DioClient {
  final FlutterSecureStorage _storage;
  late final Dio _dio;

  DioClient(this._storage) {
    _dio = Dio(
      BaseOptions(
        baseUrl: ApiConstants.baseUrl,
        connectTimeout: const Duration(seconds: 300), // 5 minutes for AI processing
        receiveTimeout: const Duration(seconds: 300), // 5 minutes for AI processing
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      ),
    );

    _addInterceptors();
  }

  /// Exposed Dio instance for making requests.
  Dio get dio => _dio;

  /// Adds Authorization and Logging interceptors.
  void _addInterceptors() {
    _dio.interceptors.add(
      InterceptorsWrapper(
        onRequest: (RequestOptions options, RequestInterceptorHandler handler) async {
          // Add Bearer token if available
          final token = await _storage.read(key: AppConstants.tokenKey);
          if (token != null) {
            options.headers['Authorization'] = 'Bearer $token';
          }
          return handler.next(options);
        },
        onError: (DioException e, ErrorInterceptorHandler handler) {
          // Global error handling logic can be added here
          return handler.next(e);
        },
      ),
    );

    // Logging interceptor for debugging
    _dio.interceptors.add(LogInterceptor(
      requestBody: true,
      responseBody: true,
      logPrint: (object) => print('DO_LOG: $object'),
    ));
  }
}
