import 'package:flutter/material.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../data/models/user.dart';
import '../../widgets/glass_card.dart';
import 'package:go_router/go_router.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  // Mock User Data
  final User _user = const User(
    id: 1,
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@example.com',
    country: 'Bangladesh',
    gender: 'Male',
    religion: 'Islam',
    dateOfBirth: '1990-05-15',
    weightInKg: 75.0,
    heightInFt: 5.9,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              const SizedBox(height: 20),
              
              // Avatar Section
              Container(
                width: 120,
                height: 120,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  gradient: LinearGradient(
                    colors: [AppColors.primary, AppColors.primaryLight],
                    begin: Alignment.topLeft,
                    end: Alignment.bottomRight,
                  ),
                  border: Border.all(color: AppColors.glassBorder, width: 2),
                ),
                child: const Center(
                  child: Icon(Icons.person, size: 60, color: Colors.white),
                ),
              ),
              
              const SizedBox(height: 16),
              Text(
                '${_user.firstName} ${_user.lastName}',
                style: AppTextStyles.headlineSmall,
              ),
              Text(
                _user.email,
                style: AppTextStyles.bodyMedium.copyWith(color: AppColors.textSecondary),
              ),
              
              const SizedBox(height: 32),
              
              // Info Grid
              GridView.count(
                crossAxisCount: 2,
                shrinkWrap: true,
                physics: const NeverScrollableScrollPhysics(),
                crossAxisSpacing: 16,
                mainAxisSpacing: 16,
                childAspectRatio: 1.5,
                children: [
                  _buildInfoCard('Country', _user.country ?? 'N/A', Icons.flag),
                  _buildInfoCard('Gender', _user.gender ?? 'N/A', Icons.person_outline),
                  _buildInfoCard('Religion', _user.religion ?? 'N/A', Icons.wb_sunny_outlined),
                  _buildInfoCard('Birth Date', _user.dateOfBirth ?? 'N/A', Icons.calendar_today),
                  _buildInfoCard('Weight', '${_user.weightInKg?.toStringAsFixed(1) ?? 'N/A'} kg', Icons.monitor_weight_outlined),
                  _buildInfoCard('Height', '${_user.heightInFt?.toStringAsFixed(1) ?? 'N/A'} ft', Icons.height),
                ],
              ),
              
              const SizedBox(height: 24),
              
              // Edit Profile Button
              GlassCard(
                padding: const EdgeInsets.all(0),
                child: InkWell(
                  onTap: () {
                    context.push('/dashboard/profile/edit');
                  },
                  borderRadius: BorderRadius.circular(16),
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16),
                    decoration: BoxDecoration(
                      gradient: LinearGradient(
                        colors: [AppColors.primary, AppColors.primaryDark],
                        begin: Alignment.centerLeft,
                        end: Alignment.centerRight,
                      ),
                      borderRadius: BorderRadius.circular(16),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Icon(Icons.edit, color: Colors.white),
                        const SizedBox(width: 8),
                        Text('Edit Profile', style: AppTextStyles.titleMedium.copyWith(color: Colors.white)),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInfoCard(String label, String value, IconData icon) {
    return GlassCard(
      padding: const EdgeInsets.all(16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(icon, color: AppColors.primaryLight, size: 24),
          const SizedBox(height: 8),
          Text(label, style: AppTextStyles.labelSmall.copyWith(color: AppColors.textSecondary)),
          const SizedBox(height: 4),
          Text(
            value,
            style: AppTextStyles.bodyMedium,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          ),
        ],
      ),
    );
  }
}
