import 'package:dio/dio.dart';
import '../../data/datasources/auth_datasource.dart';
import '../../data/models/user.dart';

/// Repository for Authentication logic.
/// Handles errors and provides clean API to providers.
class AuthRepository {
  final AuthDataSource _dataSource;

  AuthRepository(this._dataSource);

  /// Helper to map exceptions to messages
  String _mapError(Object e) {
    if (e is DioException) {
      return e.response?.data['message'] ?? 'Network error occurred';
    }
    return 'Unknown error occurred';
  }

  Future<LoginResponseDto> login(String email, String password) async {
    try {
      return await _dataSource.login(CredentialsDto(email: email, password: password));
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> register(UserDto userDto) async {
    try {
      await _dataSource.register(userDto);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }
}
