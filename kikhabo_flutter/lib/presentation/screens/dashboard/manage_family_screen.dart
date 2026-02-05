import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../domain/providers/family_provider.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/family_member_card.dart';
import '../../widgets/add_family_member_modal.dart';

class ManageFamilyScreen extends ConsumerWidget {
  const ManageFamilyScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final familyState = ref.watch(familyProvider);
    final familyMembers = familyState.familyMembers;
    final isLoading = familyState.isLoading;

    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: Column(
          children: [
            const SizedBox(height: 20),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: GlassCard(
                blur: 10,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('Family Members', style: AppTextStyles.titleLarge),
                        Text(
                          '${familyMembers.length} members', 
                          style: AppTextStyles.bodySmall.copyWith(color: AppColors.textSecondary)
                        ),
                      ],
                    ),
                    Container(
                      decoration: BoxDecoration(
                        color: AppColors.primary.withOpacity(0.2),
                        shape: BoxShape.circle,
                        border: Border.all(color: AppColors.primary.withOpacity(0.4)),
                      ),
                      child: IconButton(
                        icon: const Icon(Icons.add, color: AppColors.primaryLight),
                        onPressed: () {
                           showDialog(
                             context: context,
                             barrierColor: Colors.black.withOpacity(0.5),
                             builder: (context) => const AddFamilyMemberModal(),
                           );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Expanded(
              child: isLoading
                ? const Center(child: CircularProgressIndicator(color: AppColors.accent))
                : familyMembers.isEmpty
                  ? Center(
                      child: GlassCard(
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Text("No family members added yet.", style: AppTextStyles.bodyMedium),
                        ),
                      ),
                    )
                  : ListView.builder(
                      padding: const EdgeInsets.all(16),
                      itemCount: familyMembers.length,
                      itemBuilder: (context, index) {
                        return FamilyMemberCard(
                          member: familyMembers[index], 
                          onDelete: () {
                            showDialog(
                              context: context,
                              builder: (context) => AlertDialog(
                                backgroundColor: AppColors.surface,
                                title: Text('Remove Member?', style: AppTextStyles.titleMedium),
                                content: Text(
                                  'Are you sure you want to remove ${familyMembers[index].firstName}?', 
                                  style: AppTextStyles.bodyMedium
                                ),
                                actions: [
                                  TextButton(
                                    onPressed: () => Navigator.pop(context), 
                                    child: const Text('Cancel')
                                  ),
                                  TextButton(
                                    onPressed: () async {
                                      Navigator.pop(context);
                                      final success = await ref
                                          .read(familyProvider.notifier)
                                          .removeFamilyMember(familyMembers[index].id!);
                                      
                                      if (context.mounted) {
                                        ScaffoldMessenger.of(context).showSnackBar(
                                          SnackBar(
                                            content: Text(
                                              success 
                                                ? 'Family member removed' 
                                                : 'Failed to remove family member'
                                            ),
                                          ),
                                        );
                                      }
                                    }, 
                                    child: const Text('Remove', style: TextStyle(color: AppColors.error))
                                  ),
                                ],
                              ),
                            );
                          }
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
