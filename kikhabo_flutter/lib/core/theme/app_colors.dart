import 'package:flutter/material.dart';

class AppColors {
  // Primary (Green Spectrum)
  static const Color primary = Color(0xFF047857);      // green-700
  static const Color primaryLight = Color(0xFF10B981); // green-500
  static const Color primaryDark = Color(0xFF065F46);  // green-800

  // Accent (Orange Spectrum)
  static const Color accent = Color(0xFFF97316);       // orange-500
  static const Color accentLight = Color(0xFFFB923C);  // orange-400
  static const Color accentDark = Color(0xFFEA580C);   // orange-600

  // Neutrals (Glass Layers)
  static Color glass = const Color(0xFFFFFFFF).withOpacity(0.1);
  static Color glassBorder = const Color(0xFFFFFFFF).withOpacity(0.2);
  static Color glassText = const Color(0xFFFFFFFF).withOpacity(0.9);
  static Color glassTextSecondary = const Color(0xFFFFFFFF).withOpacity(0.7);
  
  // Text Colors (Aliases)
  static Color get textPrimary => glassText;
  static Color get textSecondary => glassTextSecondary;

  // Background Gradients
  static const LinearGradient bgGradient1 = LinearGradient(
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
    colors: [Color(0xFF1F2937), Color(0xFF111827)], // gray-800 to gray-900
  );

  static const LinearGradient bgGradient2 = LinearGradient(
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
    colors: [Color(0xFF047857), Color(0xFF0D9488)], // green-700 to teal-600
  );

  // Surface Colors
  static const Color surface = Color(0xFF1F2937);
  static const Color background = Color(0xFF111827);
  static const Color error = Color(0xFFEF4444);
}
