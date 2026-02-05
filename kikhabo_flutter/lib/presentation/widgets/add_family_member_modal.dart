import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../core/theme/glass_styles.dart';
import '../../domain/providers/auth_provider.dart';
import '../../domain/providers/family_provider.dart';
import 'glass_button.dart';
import 'glass_text_field.dart';

class AddFamilyMemberModal extends ConsumerStatefulWidget {
  const AddFamilyMemberModal({super.key});

  @override
  ConsumerState<AddFamilyMemberModal> createState() => _AddFamilyMemberModalState();
}

class _AddFamilyMemberModalState extends ConsumerState<AddFamilyMemberModal> {
  final TextEditingController _searchController = TextEditingController();

  void _performSearch(String query) {
    if (query.isEmpty) {
      ref.read(userSearchProvider.notifier).clearSearch();
      return;
    }

    // Get current user ID to filter from results
    final currentUser = ref.read(authProvider).user;
    ref.read(userSearchProvider.notifier).searchUsers(
      query,
      currentUserId: currentUser?.id,
    );
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final searchState = ref.watch(userSearchProvider);
    final searchResults = searchState.results;
    final isSearching = searchState.isLoading;
    final familyState = ref.watch(familyProvider);
    final familyMemberIds = familyState.familyMembers.map((m) => m.id).toSet();

    // Filter out users already in family
    final filteredResults = searchResults
        .where((user) => !familyMemberIds.contains(user.id))
        .toList();

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: GlassStyles.glassContainer(
        blur: 25,
        borderRadius: BorderRadius.zero,
        width: double.infinity,
        height: double.infinity,
        child: Column(
          children: [
            const SizedBox(height: 40),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                children: [
                  IconButton(
                    icon: const Icon(Icons.arrow_back, color: Colors.white),
                    onPressed: () => Navigator.of(context).pop(),
                  ),
                  const SizedBox(width: 8),
                  Text('Add Family Member', style: AppTextStyles.headlineSmall),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: GlassTextField(
                controller: _searchController,
                hintText: 'Search by email or name...',
                labelText: 'User Search',
                onChanged: (value) {
                  // Simple debounce simulation
                  Future.delayed(const Duration(milliseconds: 500), () {
                    if (value == _searchController.text) {
                      _performSearch(value);
                    }
                  });
                },
                prefixIcon: Icons.search,
              ),
            ),
            if (searchState.error != null)
              Padding(
                padding: const EdgeInsets.all(16),
                child: Text(
                  searchState.error!,
                  style: AppTextStyles.bodySmall.copyWith(color: AppColors.error),
                ),
              ),
            if (isSearching)
              const Padding(
                padding: EdgeInsets.only(top: 40),
                child: CircularProgressIndicator(color: AppColors.accent),
              ),
            
            if (!isSearching && filteredResults.isEmpty && _searchController.text.isNotEmpty)
              Padding(
                padding: const EdgeInsets.only(top: 40),
                child: Text(
                  searchResults.isEmpty 
                    ? 'No users found' 
                    : 'All found users are already in your family',
                  style: AppTextStyles.bodyMedium
                ),
              ),

            if (!isSearching && filteredResults.isNotEmpty)
              Expanded(
                child: ListView.builder(
                  padding: const EdgeInsets.all(16),
                  itemCount: filteredResults.length,
                  itemBuilder: (context, index) {
                    final user = filteredResults[index];
                    return Container(
                      margin: const EdgeInsets.only(bottom: 12),
                      padding: const EdgeInsets.all(16),
                      decoration: GlassStyles.glassDecoration.copyWith(
                        color: AppColors.glass.withOpacity(0.1),
                      ),
                      child: Row(
                        children: [
                          CircleAvatar(
                            backgroundColor: AppColors.primary,
                            child: Text(user.firstName?[0] ?? 'U'),
                          ),
                          const SizedBox(width: 16),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  '${user.firstName} ${user.lastName}', 
                                  style: AppTextStyles.titleMedium
                                ),
                                Text(user.email, style: AppTextStyles.bodySmall),
                              ],
                            ),
                          ),
                          InkWell(
                            onTap: () async {
                              final success = await ref
                                  .read(familyProvider.notifier)
                                  .addFamilyMember(user.id!);
                              
                              if (context.mounted) {
                                if (success) {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    SnackBar(content: Text('Added ${user.firstName} to family!'))
                                  );
                                  Navigator.pop(context);
                                } else {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                    const SnackBar(content: Text('Failed to add family member'))
                                  );
                                }
                              }
                            },
                            borderRadius: BorderRadius.circular(20),
                            child: Container(
                              padding: const EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                gradient: AppColors.bgGradient2,
                                shape: BoxShape.circle,
                                boxShadow: [
                                  BoxShadow(
                                    color: AppColors.primary.withOpacity(0.3),
                                    blurRadius: 8,
                                    offset: const Offset(0, 2),
                                  ),
                                ],
                              ),
                              child: const Icon(
                                Icons.add,
                                color: Colors.white,
                                size: 24,
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),
              ),
          ],
        ),
      ),
    );
  }
}
