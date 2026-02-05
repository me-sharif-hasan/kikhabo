import 'package:dio/dio.dart';
import '../models/family.dart';
import '../models/user.dart';
import '../../core/constants/api_constants.dart';

/// Responsible for Family Management related API calls.
class FamilyDataSource {
  final Dio _dio;

  FamilyDataSource(this._dio);

  /// Fetches the list of family members.
  Future<List<User>> getFamilyMembers() async {
    try {
      final response = await _dio.get(ApiConstants.family);
      
      // API returns { "status": "success", "data": { "members": [...] } }
      if (response.data is Map<String, dynamic>) {
        final data = response.data['data'];
        
        // Check if data has 'members' key
        if (data is Map<String, dynamic> && data.containsKey('members')) {
          return GetFamilyMembersDto.fromJson(data).members;
        }
        // Fallback if data is direct list
        else if (data is List) {
          return data.map((e) => User.fromJson(e as Map<String, dynamic>)).toList();
        }
      }
      // Fallback if API returns direct list
      else if (response.data is List) {
        return (response.data as List).map((e) => User.fromJson(e as Map<String, dynamic>)).toList();
      }
      
      return [];
    } catch (e) {
      rethrow;
    }
  }

  /// Adds new family members.
  Future<void> addFamilyMember(AddFamilyMemberCommand command) async {
    try {
      await _dio.post(
        ApiConstants.family,
        data: command.toJson(),
      );
    } catch (e) {
      rethrow;
    }
  }

  /// Deletes a family member by ID.
  Future<void> deleteFamilyMember(int userId) async {
    try {
      // DELETE /api/v1/family/{id}
      await _dio.delete('${ApiConstants.family}/$userId');
    } catch (e) {
      rethrow;
    }
  }
}
