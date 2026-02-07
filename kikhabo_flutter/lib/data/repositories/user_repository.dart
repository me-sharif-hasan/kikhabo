import 'package:dio/dio.dart';
import '../../data/datasources/user_datasource.dart';
import '../../data/models/user.dart';

/// Repository for User profile logic.
class UserRepository {
  final UserDataSource _dataSource;

  UserRepository(this._dataSource);

  String _mapError(Object e) {
    if (e is DioException) {
      final message = e.response?.data['message'];
      
      // Handle List of error messages
      if (message is List) {
        return message.map((e) => e.toString()).join('\n');
      } else if (message is String) {
        return message;
      }
      
      return 'Network error occurred';
    }
    return 'Unknown error occurred';
  }

  Future<GetUserResponse> getCurrentUser() async {
    try {
      return await _dataSource.getCurrentUser();
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> updateUser(UserUpdateDto updateDto) async {
    try {
      await _dataSource.updateUser(updateDto);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<List<User>> searchUsers(String query) async {
    try {
      return await _dataSource.searchUsers(query);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }
}
