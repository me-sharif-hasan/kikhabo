class ApiConstants {
  static const String baseUrl = 'http://localhost:8080';
  static const String apiVersion = '/api/v1';

  // Auth & User
  static const String login = '$apiVersion/user/login';
  static const String register = '$apiVersion/user/register';
  static const String currentUser = '$apiVersion/user/current-user';
  static const String updateUser = '$apiVersion/user/update';
  static const String searchUser = '$apiVersion/user/search';

  // Meal Planning
  static const String mealPlanning = '$apiVersion/meal-planning';
  static const String updateMeal = '$apiVersion/meal-planning/update';
  static const String updatePreference = '$apiVersion/meal-planning/update-preference';

  // Family
  static const String family = '$apiVersion/family';
}
