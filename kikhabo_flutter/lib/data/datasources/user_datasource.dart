import 'package:dio/dio.dart';
import '../models/user.dart';
import '../../core/constants/api_constants.dart';

/// Responsible for User profile related API calls.
class UserDataSource {
  final Dio _dio;

  UserDataSource(this._dio);

  /// Fetches the currently authenticated user's profile.
  Future<GetUserResponse> getCurrentUser() async {
    try {
      final response = await _dio.get(ApiConstants.currentUser);
      return GetUserResponse.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  /// Updates the user's profile information.
  Future<void> updateUser(UserUpdateDto updateDto) async {
    try {
      await _dio.put(
        ApiConstants.updateUser,
        data: updateDto.toJson(),
      );
    } catch (e) {
      rethrow;
    }
  }

  /// Searches for users by query string.
  Future<List<User>> searchUsers(String query) async {
    try {
      final response = await _dio.get(
        ApiConstants.searchUser,
        queryParameters: {'query': query},
      );
      
      // Assuming response is List<User> or ApiResponse<List<User>>
      // Based on analysis, it likely returns a list of users directly
      if (response.data is List) {
        return (response.data as List)
            .map((e) => User.fromJson(e))
            .toList();
      }
      return [];
    } catch (e) {
      rethrow;
    }
  }
}
