import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

/// Centralized API error handler
class ApiErrorHandler {
  /// Handles API errors and shows appropriate user feedback
  /// 
  /// For 4xx INVALID_ARGUMENTS errors, shows validation messages in toast
  /// For other errors, shows generic error message
  static void handleError(DioException error, BuildContext? context) {
    if (context == null || !context.mounted) return;

    final statusCode = error.response?.statusCode;
    final responseData = error.response?.data;

    debugPrint('API Error - Status: $statusCode');
    debugPrint('API Error - Response Data Type: ${responseData.runtimeType}');
    debugPrint('API Error - Response Data: $responseData');

    // Handle 4xx validation errors with INVALID_ARGUMENTS code
    if (statusCode != null && statusCode >= 400 && statusCode < 500) {
      if (responseData is Map<String, dynamic>) {
        final code = responseData['code'];
        final message = responseData['message'];

        debugPrint('API Error - Code: $code');
        debugPrint('API Error - Message Type: ${message.runtimeType}');
        debugPrint('API Error - Message: $message');

        if (code == 'INVALID_ARGUMENTS') {
          // Show validation errors
          _showValidationErrors(context, message);
          return;
        }
      }
    }

    // Show generic error for all other cases
    _showGenericError(context, error);
  }

  /// Shows validation error messages in a toast
  static void _showValidationErrors(BuildContext context, dynamic message) {
    String errorText;

    try {
      if (message is List) {
        // Join multiple validation messages, converting each to String
        final messages = <String>[];
        for (var item in message) {
          messages.add(item.toString());
        }
        errorText = messages.join('\n');
      } else if (message is String) {
        errorText = message;
      } else {
        errorText = message?.toString() ?? 'Validation error occurred';
      }
    } catch (e) {
      debugPrint('Error converting validation message: $e');
      errorText = 'Validation error occurred';
    }

    debugPrint('Showing validation error toast: $errorText');
    _showToast(context, errorText, isError: true);
  }

  /// Shows a generic error message
  static void _showGenericError(BuildContext context, DioException error) {
    String errorText;

    switch (error.type) {
      case DioExceptionType.connectionTimeout:
      case DioExceptionType.sendTimeout:
      case DioExceptionType.receiveTimeout:
        errorText = 'Connection timeout. Please try again.';
        break;
      case DioExceptionType.badResponse:
        errorText = 'An error occurred. Please try again.';
        break;
      case DioExceptionType.cancel:
        errorText = 'Request cancelled';
        break;
      case DioExceptionType.connectionError:
        errorText = 'No internet connection';
        break;
      default:
        errorText = 'An error occurred. Please try again.';
    }

    _showToast(context, errorText, isError: true);
  }

  /// Shows a toast message using SnackBar
  static void _showToast(BuildContext context, String message, {bool isError = false}) {
    try {
      final messenger = ScaffoldMessenger.of(context);
      
      // Clear any existing snackbars
      messenger.clearSnackBars();
      
      messenger.showSnackBar(
        SnackBar(
          content: Text(message),
          backgroundColor: isError ? Colors.red.shade700 : Colors.green.shade700,
          behavior: SnackBarBehavior.floating,
          duration: const Duration(seconds: 4),
          action: SnackBarAction(
            label: 'Dismiss',
            textColor: Colors.white,
            onPressed: () => messenger.hideCurrentSnackBar(),
          ),
        ),
      );
    } catch (e) {
      debugPrint('Error showing toast: $e');
    }
  }

  /// Extracts error message from DioException for logging or display
  static String getErrorMessage(DioException error) {
    final responseData = error.response?.data;
    
    if (responseData is Map<String, dynamic>) {
      final message = responseData['message'];
      
      if (message is List) {
        final messages = <String>[];
        for (var item in message) {
          messages.add(item.toString());
        }
        return messages.join(', ');
      } else if (message is String) {
        return message;
      }
    }
    
    return error.message ?? 'Unknown error occurred';
  }
}
