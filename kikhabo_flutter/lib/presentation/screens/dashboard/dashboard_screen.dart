import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../core/theme/glass_styles.dart';
import '../../../domain/providers/auth_provider.dart';

class DashboardScreen extends ConsumerStatefulWidget {
  final Widget child;

  const DashboardScreen({super.key, required this.child});

  @override
  ConsumerState<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends ConsumerState<DashboardScreen> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    final authState = ref.watch(authProvider);
    final user = authState.user;

    return Scaffold(
      key: _scaffoldKey,
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: IconButton(
          icon: Icon(Icons.menu, color: AppColors.textPrimary),
          onPressed: () => _scaffoldKey.currentState?.openDrawer(),
        ),
        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 16.0),
            child: CircleAvatar(
              backgroundColor: AppColors.glass,
              child: Text(
                user?.firstName?.substring(0, 1).toUpperCase() ?? 'U',
                style: const TextStyle(color: AppColors.primaryLight),
              ),
            ),
          ),
        ],
        flexibleSpace: GlassStyles.glassContainer(
          child: const SizedBox.expand(),
          blur: 10,
          borderRadius: BorderRadius.only(
            bottomLeft: Radius.circular(20),
            bottomRight: Radius.circular(20),
          ),
        ),
      ),
      drawer: Drawer(
        backgroundColor: Colors.transparent,
        elevation: 0,
        width: 280,
        child: Container(
          decoration: BoxDecoration(
            gradient: AppColors.bgGradient1,
            border: Border(right: BorderSide(color: AppColors.glassBorder.withOpacity(0.3))),
          ),
          child: GlassStyles.glassContainer(
            borderRadius: BorderRadius.zero,
            blur: 20,
            child: Column(
              children: [
                DrawerHeader(
                  decoration: BoxDecoration(
                    color: AppColors.primary.withOpacity(0.1),
                    border: Border(bottom: BorderSide(color: AppColors.glassBorder.withOpacity(0.3))),
                  ),
                  child: Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Icon(Icons.restaurant_menu, size: 48, color: AppColors.primaryLight),
                        const SizedBox(height: 8),
                        Text('Kikhabo', style: AppTextStyles.headlineMedium),
                      ],
                    ),
                  ),
                ),
                _buildDrawerItem(
                  icon: Icons.home_rounded,
                  title: 'Dashboard',
                  onTap: () => context.go('/dashboard/home'),
                ),
                _buildDrawerItem(
                  icon: Icons.list_alt_rounded,
                  title: 'Meal List',
                  onTap: () => context.go('/dashboard/meals'),
                ),
                _buildDrawerItem(
                  icon: Icons.people_rounded,
                  title: 'Family Members',
                  onTap: () => context.go('/dashboard/family'),
                ),
                _buildDrawerItem(
                  icon: Icons.settings_rounded,
                  title: 'Preferences',
                  onTap: () => context.go('/dashboard/preferences'),
                ),
                _buildDrawerItem(
                  icon: Icons.bar_chart_rounded,
                  title: 'Statistics',
                  onTap: () => context.go('/dashboard/statistics'),
                ),
                const Spacer(),
                Divider(color: AppColors.glassBorder.withOpacity(0.3)),
                _buildDrawerItem(
                  icon: Icons.logout_rounded,
                  title: 'Logout',
                  onTap: () async {
                    await ref.read(authProvider.notifier).logout();
                    if (mounted) context.go('/');
                  },
                ),
                const SizedBox(height: 24),
              ],
            ),
          ),
        ),
      ),
      body: Container(
        decoration: const BoxDecoration(
          gradient: AppColors.bgGradient2, 
        ),
        child: widget.child,
      ),
    );
  }

  Widget _buildDrawerItem({
    required IconData icon,
    required String title,
    required VoidCallback onTap,
  }) {
    return ListTile(
      leading: Icon(icon, color: AppColors.textSecondary),
      title: Text(title, style: AppTextStyles.bodyMedium),
      onTap: () {
        // Close drawer then navigate
        _scaffoldKey.currentState?.closeDrawer();
        onTap();
      },
      hoverColor: AppColors.primary.withOpacity(0.1),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
    );
  }
}
