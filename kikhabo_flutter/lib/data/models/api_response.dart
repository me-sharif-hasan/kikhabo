import 'package:freezed_annotation/freezed_annotation.dart';

part 'api_response.freezed.dart';
part 'api_response.g.dart';

/// Generic API Response wrapper for standardized backend responses.
/// 
/// [T] is the type of the data field.
@Freezed(genericArgumentFactories: true)
abstract class ApiResponse<T> with _$ApiResponse<T> {
  const factory ApiResponse({
    /// Status string (e.g., "success", "error")
    required String status,
    
    /// Optional message from the server
    String? message,
    
    /// Main payload data
    T? data,
  }) = _ApiResponse;

  /// Creates an [ApiResponse] from JSON data.
  factory ApiResponse.fromJson(
    Map<String, dynamic> json,
    T Function(Object? json) fromJsonT,
  ) => _$ApiResponseFromJson(json, fromJsonT);
}
