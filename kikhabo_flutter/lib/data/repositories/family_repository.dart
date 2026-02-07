import 'package:dio/dio.dart';
import '../../data/datasources/family_datasource.dart';
import '../../data/models/family.dart';
import '../../data/models/user.dart';

/// Repository for Family Management logic.
class FamilyRepository {
  final FamilyDataSource _dataSource;

  FamilyRepository(this._dataSource);

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

  Future<List<User>> getFamilyMembers() async {
    try {
      return await _dataSource.getFamilyMembers();
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> addFamilyMember(List<int> memberIds) async {
    try {
      await _dataSource.addFamilyMember(AddFamilyMemberCommand(familyMemberIds: memberIds));
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }

  Future<void> deleteFamilyMember(int userId) async {
    try {
      await _dataSource.deleteFamilyMember(userId);
    } catch (e) {
      throw Exception(_mapError(e));
    }
  }
}
