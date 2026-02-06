class ApiConstants {
  static const String baseUrl = 'http://localhost:8080';
  // static const String baseUrl = 'https://kikhabo.iishanto.com';
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
  static const String mealHistoryUpdate = '$apiVersion/meal-history/update';
  static const String mealHistory = '$apiVersion/meal-history';

  // Analytics
  static const String energyDaily = '$apiVersion/analytics/energy/daily';
  static const String energyWeekly = '$apiVersion/analytics/energy/weekly';
  static const String energyMonthly = '$apiVersion/analytics/energy/monthly';
  static const String costDaily = '$apiVersion/analytics/cost/daily';
  static const String costWeekly = '$apiVersion/analytics/cost/weekly';
  static const String costMonthly = '$apiVersion/analytics/cost/monthly';

  // Family
  static const String family = '$apiVersion/family';
}
