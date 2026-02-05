import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../core/theme/app_colors.dart';
import '../../core/theme/app_text_styles.dart';
import '../../core/theme/glass_styles.dart';
import '../../data/models/user.dart';
import 'glass_button.dart';
import 'glass_text_field.dart';

// Mock Provider for Search Results
final searchResultsProvider = StateProvider<List<User>>((ref) => []);
final isSearchingProvider = StateProvider<bool>((ref) => false);

class AddFamilyMemberModal extends ConsumerStatefulWidget {
  const AddFamilyMemberModal({super.key});

  @override
  ConsumerState<AddFamilyMemberModal> createState() => _AddFamilyMemberModalState();
}

class _AddFamilyMemberModalState extends ConsumerState<AddFamilyMemberModal> {
  final TextEditingController _searchController = TextEditingController();

  void _performSearch(String query) async {
    if (query.isEmpty) {
      ref.read(searchResultsProvider.notifier).state = [];
      return;
    }

    ref.read(isSearchingProvider.notifier).state = true;
    
    // Simulate API delay
    await Future.delayed(const Duration(milliseconds: 800));

    // Mock Results
    if (query.toLowerCase() == 'found') {
        ref.read(searchResultsProvider.notifier).state = [
          const User(
            id: 201,
            email: 'found.user@example.com',
            firstName: 'Found',
            lastName: 'User',
            gender: 'Male',
            country: 'UK',
            dateOfBirth: '1995-05-05',
            religion: 'None',
            weightInKg: 70,
            heightInFt: 5.8,
          )
        ];
    } else {
       ref.read(searchResultsProvider.notifier).state = [];
    }
    
    ref.read(isSearchingProvider.notifier).state = false;
  }

  @override
  Widget build(BuildContext context) {
    final searchResults = ref.watch(searchResultsProvider);
    final isSearching = ref.watch(isSearchingProvider);

    return Scaffold(
      backgroundColor: Colors.transparent, // Fully transparent background
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
            if (isSearching)
               const Padding(
                 padding: EdgeInsets.only(top: 40),
                 child: CircularProgressIndicator(color: AppColors.accent),
               ),
            
            if (!isSearching && searchResults.isEmpty && _searchController.text.isNotEmpty)
               Padding(
                 padding: const EdgeInsets.only(top: 40),
                 child: Text('No users found. Try searching "found"', style: AppTextStyles.bodyMedium),
               ),

            if (!isSearching && searchResults.isNotEmpty)
              Expanded(
                child: ListView.builder(
                  padding: const EdgeInsets.all(16),
                  itemCount: searchResults.length,
                  itemBuilder: (context, index) {
                    final user = searchResults[index];
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
                                Text('${user.firstName} ${user.lastName}', style: AppTextStyles.titleMedium),
                                Text(user.email, style: AppTextStyles.bodySmall),
                              ],
                            ),
                          ),
                          GlassButton(
                            text: 'Add',
                            width: 80,
                            height: 36,
                            onPressed: () {
                              ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Added ${user.firstName} to family!')));
                              Navigator.pop(context);
                            },
                            gradient: AppColors.bgGradient2,
                          )
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
