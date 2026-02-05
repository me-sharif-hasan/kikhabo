import 'dart:ui';
import 'package:flutter/material.dart';
import 'app_colors.dart';

class GlassStyles {
  /// Base decoration for glass cards
  static BoxDecoration get glassDecoration => BoxDecoration(
    color: AppColors.glass,
    borderRadius: BorderRadius.circular(20),
    border: Border.all(
      color: AppColors.glassBorder,
      width: 1,
    ),
    boxShadow: [
      BoxShadow(
        color: Colors.black.withOpacity(0.1),
        blurRadius: 20,
        spreadRadius: 5,
      ),
    ],
  );

  /// Decoration for input fields
  static BoxDecoration get glassInputDecoration => BoxDecoration(
    color: AppColors.glass.withOpacity(0.05),
    borderRadius: BorderRadius.circular(15),
    border: Border.all(
      color: AppColors.glassBorder.withOpacity(0.3),
      width: 1,
    ),
  );

  /// Standard blur filter
  static ImageFilter get blurFilter => ImageFilter.blur(sigmaX: 10, sigmaY: 10);

  /// ClipRect wrapper for BackdropFilter to apply blur
  /// Use this inside a Widget to apply the blur effect
  static Widget glassContainer({
    required Widget child,
    double blur = 15,
    EdgeInsetsGeometry padding = const EdgeInsets.all(16),
    double? width,
    double? height,
    BorderRadius? borderRadius,
  }) {
    return ClipRRect(
      borderRadius: borderRadius ?? BorderRadius.circular(20),
      child: BackdropFilter(
        filter: ImageFilter.blur(sigmaX: blur, sigmaY: blur),
        child: Container(
          width: width,
          height: height,
          padding: padding,
          decoration: glassDecoration.copyWith(
            borderRadius: borderRadius ?? BorderRadius.circular(20),
          ),
          child: child,
        ),
      ),
    );
  }
}
