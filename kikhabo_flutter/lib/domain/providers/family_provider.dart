import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/datasources/family_datasource.dart';
import '../../data/datasources/user_datasource.dart';
import '../../data/models/family.dart';
import '../../data/models/user.dart';
import 'auth_provider.dart';

/// Provider for FamilyDataSource
final familyDataSourceProvider = Provider<FamilyDataSource>((ref) {
  final dio = ref.watch(dioClientProvider).dio;
  return FamilyDataSource(dio);
});

/// Provider for UserDataSource
final userDataSourceProvider = Provider<UserDataSource>((ref) {
  final dio = ref.watch(dioClientProvider).dio;
  return UserDataSource(dio);
});

/// State for family management
class FamilyState {
  final List<User> familyMembers;
  final bool isLoading;
  final String? error;

  FamilyState({
    this.familyMembers = const [],
    this.isLoading = false,
    this.error,
  });

  FamilyState copyWith({
    List<User>? familyMembers,
    bool? isLoading,
    String? error,
  }) {
    return FamilyState(
      familyMembers: familyMembers ?? this.familyMembers,
      isLoading: isLoading ?? this.isLoading,
      error: error,
    );
  }
}

/// Notifier for managing family members
class FamilyNotifier extends StateNotifier<FamilyState> {
  final FamilyDataSource _dataSource;

  FamilyNotifier(this._dataSource) : super(FamilyState()) {
    loadFamilyMembers();
  }

  Future<void> loadFamilyMembers() async {
    state = state.copyWith(isLoading: true, error: null);
    try {
      final members = await _dataSource.getFamilyMembers();
      state = state.copyWith(familyMembers: members, isLoading: false);
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: 'Failed to load family members: ${e.toString()}',
      );
    }
  }

  Future<bool> addFamilyMember(int userId) async {
    try {
      final command = AddFamilyMemberCommand(familyMemberIds: [userId]);
      await _dataSource.addFamilyMember(command);
      await loadFamilyMembers(); // Reload the list
      return true;
    } catch (e) {
      state = state.copyWith(error: 'Failed to add family member: ${e.toString()}');
      return false;
    }
  }

  Future<bool> removeFamilyMember(int userId) async {
    try {
      await _dataSource.deleteFamilyMember(userId);
      await loadFamilyMembers(); // Reload the list
      return true;
    } catch (e) {
      state = state.copyWith(error: 'Failed to remove family member: ${e.toString()}');
      return false;
    }
  }
}

/// Provider for family state
final familyProvider = StateNotifierProvider<FamilyNotifier, FamilyState>((ref) {
  final dataSource = ref.watch(familyDataSourceProvider);
  return FamilyNotifier(dataSource);
});

/// State for user search
class UserSearchState {
  final List<User> results;
  final bool isLoading;
  final String? error;

  UserSearchState({
    this.results = const [],
    this.isLoading = false,
    this.error,
  });

  UserSearchState copyWith({
    List<User>? results,
    bool? isLoading,
    String? error,
  }) {
    return UserSearchState(
      results: results ?? this.results,
      isLoading: isLoading ?? this.isLoading,
      error: error,
    );
  }
}

/// Notifier for user search
class UserSearchNotifier extends StateNotifier<UserSearchState> {
  final UserDataSource _dataSource;

  UserSearchNotifier(this._dataSource) : super(UserSearchState());

  Future<void> searchUsers(String query, {int? currentUserId}) async {
    if (query.isEmpty) {
      state = UserSearchState();
      return;
    }

    state = state.copyWith(isLoading: true, error: null);
    try {
      final results = await _dataSource.searchUsers(query);
      
      // Filter out current user if provided
      final filteredResults = currentUserId != null
          ? results.where((user) => user.id != currentUserId).toList()
          : results;
      
      state = state.copyWith(results: filteredResults, isLoading: false);
    } catch (e) {
      state = state.copyWith(
        isLoading: false,
        error: 'Search failed: ${e.toString()}',
      );
    }
  }

  void clearSearch() {
    state = UserSearchState();
  }
}

/// Provider for user search
final userSearchProvider = StateNotifierProvider<UserSearchNotifier, UserSearchState>((ref) {
  final dataSource = ref.watch(userDataSourceProvider);
  return UserSearchNotifier(dataSource);
});
