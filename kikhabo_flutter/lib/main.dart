import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'core/theme/app_theme.dart';
import 'core/constants/app_constants.dart';
import 'domain/providers/auth_provider.dart';
import 'presentation/screens/auth/login_screen.dart';
import 'presentation/screens/auth/registration_screen.dart';
import 'presentation/screens/dashboard/dashboard_screen.dart';
import 'presentation/screens/dashboard/home_screen.dart';
import 'presentation/screens/dashboard/meals_screen.dart';
import 'presentation/screens/dashboard/manage_family_screen.dart';
import 'presentation/screens/dashboard/manage_preferences_screen.dart';
import 'presentation/screens/dashboard/meal_statistics_screen.dart';
import 'presentation/screens/dashboard/meal_details_screen.dart';
import 'presentation/screens/dashboard/profile_screen.dart';
import 'presentation/screens/dashboard/edit_profile_screen.dart';
import 'data/models/meal.dart';


void main() {
  runApp(const ProviderScope(child: KikhaboApp()));
}

class KikhaboApp extends ConsumerStatefulWidget {
  const KikhaboApp({super.key});

  @override
  ConsumerState<KikhaboApp> createState() => _KikhaboAppState();
}

class _KikhaboAppState extends ConsumerState<KikhaboApp> {
  @override
  void initState() {
    super.initState();
    // Check auth status on app start
    ref.read(authProvider.notifier).checkAuthStatus();
  }

  @override
  Widget build(BuildContext context) {
    // Watch auth state to redirect if needed
    final authState = ref.watch(authProvider);

    final router = GoRouter(
      initialLocation: '/',
      redirect: (context, state) {
        final isLoggedIn = authState.isAuthenticated;
        final isLoggingIn = state.uri.toString() == '/';
        final isRegistering = state.uri.toString() == '/register';

        if (!isLoggedIn && !isLoggingIn && !isRegistering) {
          return '/';
        }
        if (isLoggedIn && isLoggingIn) {
          return '/dashboard/home';
        }
        return null;
      },
      routes: [
        GoRoute(
          path: '/',
          builder: (context, state) => const LoginScreen(),
        ),
        GoRoute(
          path: '/register',
          builder: (context, state) => const RegistrationScreen(),
        ),
        GoRoute(
          path: '/dashboard',
          // ShellRoute equivalent using nested builder in GoRouter for persistent UI
          builder: (context, state) {
            // This is a simplified approach. Better to use ShellRoute for true persistent UI.
            // But since GoRouter structure in main.dart was nested routes, we can adapt.
            // Actually, for proper Drawer persistence, we need ShellRoute or wrapping here.
            
            // NOTE: GoRouter nested routes `builder` wraps the sub-routes ONLY if using ShellRoute.
            // With simple nested routes, the parent builder matches /dashboard exactly.
            // To ensure DashboardScreen wraps child routes, we should use ShellRoute or StatefulShellRoute.
            // BUT, for quick fix: we can't easily wrap child routes without ShellRoute structure change.
            // Let's assume /dashboard/home renders DashboardScreen(child: HomeScreen).
            // We'll refactor logic slightly to inject `child` via path check or re-structure to ShellRoute in next step if needed.
            // For now, let's simpler: Direct routes mapping to DashboardScreen(child: Content)
             return const SizedBox(); // This path shouldn't be valid directly usually, redirect to home
          }, 
          redirect: (context, state) {
             if (state.uri.toString() == '/dashboard') return '/dashboard/home';
             return null;
          },
          routes: [
            GoRoute(
              path: 'home',
              builder: (context, state) => const DashboardScreen(child: HomeScreen()),
            ),
            GoRoute(
              path: 'meals',
              builder: (context, state) => const DashboardScreen(child: MealsScreen()),
            ),
            GoRoute(
              path: 'family',
              builder: (context, state) => const DashboardScreen(child: ManageFamilyScreen()),
            ),
            GoRoute(
              path: 'manage_family',
              builder: (context, state) => const DashboardScreen(child: ManageFamilyScreen()),
            ),
            GoRoute(
              path: 'preferences',
              builder: (context, state) => const DashboardScreen(child: ManagePreferencesScreen()),
            ),
            GoRoute(
              path: 'statistics',
              builder: (context, state) => const DashboardScreen(child: MealStatisticsScreen()),
            ),
            GoRoute(
              path: 'meal_details',
              builder: (context, state) {
                final meal = state.extra as Meal;
                return DashboardScreen(child: MealDetailsScreen(meal: meal));
              },
            ),
            GoRoute(
              path: 'profile',
              builder: (context, state) => const DashboardScreen(child: ProfileScreen()),
              routes: [
                GoRoute(
                  path: 'edit',
                  builder: (context, state) => const EditProfileScreen(),
                ),
              ],
            ),
          ],
        ),
      ],
    );

    return MaterialApp.router(
      title: AppConstants.appName,
      theme: AppTheme.liquidGlassTheme,
      routerConfig: router,
      debugShowCheckedModeBanner: false,
    );
  }
}
