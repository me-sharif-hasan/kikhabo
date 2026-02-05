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
      
      // Based on analysis, it returns a DTO wrapping the list
      // Or sometimes directly the list. Let's assume DTO based on `GetFamilyMembersDto`
      // If the API returns direct list, we can adjust.
      // Checking `GetFamilyMembersDto` definition: it has `members` list.
      // If API returns { "members": [...] }
      if (response.data is Map<String, dynamic> && response.data.containsKey('members')) {
        return GetFamilyMembersDto.fromJson(response.data).members;
      } 
      // Fallback if API returns direct list
      else if (response.data is List) {
        return (response.data as List).map((e) => User.fromJson(e)).toList();
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
