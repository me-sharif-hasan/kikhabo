import 'dart:io';
import 'dart:typed_data';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:pdf/pdf.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:share_plus/share_plus.dart';
import '../../data/models/meal.dart';

/// PDF Generator for meal recipes
class PdfGenerator {
  /// Generates a PDF for a single recipe
  /// Returns null if user cancels, otherwise returns the saved file
  static Future<File?> generateRecipePdf(Meal meal) async {
    final pdf = pw.Document();
    
    // Load logo image
    final ByteData logoData = await rootBundle.load('assets/logo.png');
    final Uint8List logoBytes = logoData.buffer.asUint8List();
    final logo = pw.MemoryImage(logoBytes);

    // Define colors matching app theme
    final primaryColor = PdfColor.fromHex('#047857'); // green-700
    final accentColor = PdfColor.fromHex('#F97316'); // orange-500
    final lightGreen = PdfColor.fromHex('#10B981'); // green-500

    pdf.addPage(
      pw.Page(
        pageFormat: PdfPageFormat.a4,
        margin: const pw.EdgeInsets.all(32),
        build: (pw.Context context) {
          return pw.Column(
            crossAxisAlignment: pw.CrossAxisAlignment.start,
            children: [
              // Header with logo and branding
              _buildHeader(logo, primaryColor),
              pw.SizedBox(height: 24),

              // Meal name and energy
              _buildMealHeader(meal, primaryColor, accentColor),
              pw.SizedBox(height: 20),

              // Stats row
              _buildStatsRow(meal, primaryColor, lightGreen),
              pw.SizedBox(height: 24),

              // Groceries section
              if (meal.groceries != null && meal.groceries!.isNotEmpty)
                _buildGroceriesSection(meal, primaryColor, accentColor),

              if (meal.groceryNames != null && meal.groceryNames!.isNotEmpty)
                _buildGroceryNamesSection(meal, primaryColor),

              // Notes section
              if (meal.note != null || meal.userNote != null) ...[ 
                pw.SizedBox(height: 20),
                _buildNotesSection(meal, primaryColor, accentColor),
              ],

              pw.Spacer(),

              // Footer with branding
              _buildFooter(primaryColor),
            ],
          );
        },
      ),
    );

    final pdfBytes = await pdf.save();

    // On Mobile (Android/iOS): Share the file which allows saving
    if (!kIsWeb && (Platform.isAndroid || Platform.isIOS)) {
      final directory = await getTemporaryDirectory();
      // Sanitize filename
      final safeName = meal.mealName.replaceAll(RegExp(r'[^\w\s]+'), '').replaceAll(' ', '_');
      final fileName = '${safeName}_recipe.pdf';
      final file = File('${directory.path}/$fileName');
      await file.writeAsBytes(pdfBytes);
      
      // Share allowing user to save/export
      await Share.shareXFiles(
        [XFile(file.path)], 
        text: 'Recipe: ${meal.mealName}',
        subject: 'Recipe PDF'
      );
      
      return file;
    }

    // On Desktop: Use FilePicker to save
    final safeName = meal.mealName.replaceAll(RegExp(r'[^\w\s]+'), '').replaceAll(' ', '_');
    final fileName = '${safeName}_recipe.pdf';
    final String? outputPath = await FilePicker.platform.saveFile(
      dialogTitle: 'Save Recipe PDF',
      fileName: fileName,
      type: FileType.custom,
      allowedExtensions: ['pdf'],
    );

    if (outputPath == null) {
      return null;
    }

    final file = File(outputPath);
    await file.writeAsBytes(pdfBytes);

    return file;
  }

  /// Builds the header with logo and app name
  static pw.Widget _buildHeader(pw.MemoryImage logo, PdfColor primaryColor) {
    return pw.Row(
      mainAxisAlignment: pw.MainAxisAlignment.spaceBetween,
      children: [
        pw.Row(
          children: [
            pw.Image(logo, width: 40, height: 40),
            pw.SizedBox(width: 12),
            pw.Column(
              crossAxisAlignment: pw.CrossAxisAlignment.start,
              children: [
                pw.Text(
                  'Kikhabo',
                  style: pw.TextStyle(
                    fontSize: 24,
                    fontWeight: pw.FontWeight.bold,
                    color: primaryColor,
                  ),
                ),
                pw.Text(
                  'Your Meal Planning Assistant',
                  style: pw.TextStyle(
                    fontSize: 10,
                    color: PdfColors.grey700,
                  ),
                ),
              ],
            ),
          ],
        ),
        pw.Text(
          'Recipe Card',
          style: pw.TextStyle(
            fontSize: 12,
            color: PdfColors.grey600,
            fontStyle: pw.FontStyle.italic,
          ),
        ),
      ],
    );
  }

  /// Builds meal name and energy badge
  static pw.Widget _buildMealHeader(Meal meal, PdfColor primaryColor, PdfColor accentColor) {
    return pw.Container(
      padding: const pw.EdgeInsets.all(16),
      decoration: pw.BoxDecoration(
        border: pw.Border.all(color: primaryColor, width: 2),
        borderRadius: const pw.BorderRadius.all(pw.Radius.circular(12)),
      ),
      child: pw.Row(
        mainAxisAlignment: pw.MainAxisAlignment.spaceBetween,
        children: [
          pw.Expanded(
            child: pw.Text(
              meal.mealName,
              style: pw.TextStyle(
                fontSize: 20,
                fontWeight: pw.FontWeight.bold,
                color: primaryColor,
              ),
            ),
          ),
          pw.Container(
            padding: const pw.EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            decoration: pw.BoxDecoration(
              color: accentColor.shade(0.2),
              borderRadius: const pw.BorderRadius.all(pw.Radius.circular(8)),
            ),
            child: pw.Text(
              '${meal.totalEnergy} kcal',
              style: pw.TextStyle(
                fontSize: 14,
                fontWeight: pw.FontWeight.bold,
                color: accentColor,
              ),
            ),
          ),
        ],
      ),
    );
  }

  /// Builds stats row with status, rating, and date
  static pw.Widget _buildStatsRow(Meal meal, PdfColor primaryColor, PdfColor lightGreen) {
    final date = meal.timestamp != null
        ? DateTime.fromMillisecondsSinceEpoch(meal.timestamp! * 1000)
        : DateTime.now();
    final formattedDate = '${date.day}/${date.month}/${date.year}';

    return pw.Row(
      mainAxisAlignment: pw.MainAxisAlignment.spaceAround,
      children: [
        _buildStatItem('Status', meal.mealStatus ?? 'PLANNED', primaryColor),
        _buildStatItem('Rating', meal.rating != null && meal.rating! > 0 ? '${meal.rating}/5 ⭐' : 'Not rated', lightGreen),
        _buildStatItem('Date', formattedDate, PdfColors.grey700),
      ],
    );
  }

  /// Builds a single stat item
  static pw.Widget _buildStatItem(String label, String value, PdfColor color) {
    return pw.Column(
      children: [
        pw.Text(
          label,
          style: pw.TextStyle(
            fontSize: 10,
            color: PdfColors.grey600,
          ),
        ),
        pw.SizedBox(height: 4),
        pw.Text(
          value,
          style: pw.TextStyle(
            fontSize: 12,
            fontWeight: pw.FontWeight.bold,
            color: color,
          ),
        ),
      ],
    );
  }

  /// Builds groceries section with detailed info
  static pw.Widget _buildGroceriesSection(Meal meal, PdfColor primaryColor, PdfColor accentColor) {
    return pw.Column(
      crossAxisAlignment: pw.CrossAxisAlignment.start,
      children: [
        pw.Container(
          padding: const pw.EdgeInsets.symmetric(vertical: 8),
          decoration: pw.BoxDecoration(
            border: pw.Border(bottom: pw.BorderSide(color: primaryColor, width: 2)),
          ),
          child: pw.Text(
            '🛒 Groceries',
            style: pw.TextStyle(
              fontSize: 16,
              fontWeight: pw.FontWeight.bold,
              color: primaryColor,
            ),
          ),
        ),
        pw.SizedBox(height: 12),
        ...meal.groceries!.map((grocery) => pw.Padding(
          padding: const pw.EdgeInsets.only(bottom: 8),
          child: pw.Row(
            mainAxisAlignment: pw.MainAxisAlignment.spaceBetween,
            children: [
              pw.Expanded(
                child: pw.Text(
                  '• ${grocery.name}',
                  style: const pw.TextStyle(fontSize: 12),
                ),
              ),
              pw.Text(
                '${grocery.amountInGm}g',
                style: pw.TextStyle(
                  fontSize: 11,
                  color: PdfColors.grey700,
                ),
              ),
              pw.SizedBox(width: 12),
              pw.Container(
                padding: const pw.EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                decoration: pw.BoxDecoration(
                  color: accentColor.shade(0.2),
                  borderRadius: const pw.BorderRadius.all(pw.Radius.circular(4)),
                ),
                child: pw.Text(
                  '₹${grocery.priceRatingOutOf10}',
                  style: pw.TextStyle(
                    fontSize: 10,
                    fontWeight: pw.FontWeight.bold,
                    color: accentColor,
                  ),
                ),
              ),
            ],
          ),
        )),
      ],
    );
  }

  /// Builds grocery names section (for history meals)
  static pw.Widget _buildGroceryNamesSection(Meal meal, PdfColor primaryColor) {
    return pw.Column(
      crossAxisAlignment: pw.CrossAxisAlignment.start,
      children: [
        pw.Container(
          padding: const pw.EdgeInsets.symmetric(vertical: 8),
          decoration: pw.BoxDecoration(
            border: pw.Border(bottom: pw.BorderSide(color: primaryColor, width: 2)),
          ),
          child: pw.Text(
            '🛒 Groceries',
            style: pw.TextStyle(
              fontSize: 16,
              fontWeight: pw.FontWeight.bold,
              color: primaryColor,
            ),
          ),
        ),
        pw.SizedBox(height: 12),
        ...meal.groceryNames!.map((name) => pw.Padding(
          padding: const pw.EdgeInsets.only(bottom: 6),
          child: pw.Text(
            '• $name',
            style: const pw.TextStyle(fontSize: 12),
          ),
        )),
      ],
    );
  }

  /// Builds notes section
  static pw.Widget _buildNotesSection(Meal meal, PdfColor primaryColor, PdfColor accentColor) {
    return pw.Column(
      crossAxisAlignment: pw.CrossAxisAlignment.start,
      children: [
        pw.Container(
          padding: const pw.EdgeInsets.symmetric(vertical: 8),
          decoration: pw.BoxDecoration(
            border: pw.Border(bottom: pw.BorderSide(color: primaryColor, width: 2)),
          ),
          child: pw.Text(
            '📝 Notes',
            style: pw.TextStyle(
              fontSize: 16,
              fontWeight: pw.FontWeight.bold,
              color: primaryColor,
            ),
          ),
        ),
        pw.SizedBox(height: 12),
        if (meal.note != null) ...[
          pw.Text(
            'AI Suggestion:',
            style: pw.TextStyle(
              fontSize: 11,
              fontWeight: pw.FontWeight.bold,
              color: primaryColor,
            ),
          ),
          pw.SizedBox(height: 4),
          pw.Text(
            meal.note!,
            style: const pw.TextStyle(fontSize: 11),
          ),
          if (meal.userNote != null) pw.SizedBox(height: 12),
        ],
        if (meal.userNote != null) ...[
          pw.Text(
            'Your Note:',
            style: pw.TextStyle(
              fontSize: 11,
              fontWeight: pw.FontWeight.bold,
              color: accentColor,
            ),
          ),
          pw.SizedBox(height: 4),
          pw.Text(
            meal.userNote!,
            style: const pw.TextStyle(fontSize: 11),
          ),
        ],
      ],
    );
  }

  /// Builds footer with branding
  static pw.Widget _buildFooter(PdfColor primaryColor) {
    return pw.Container(
      padding: const pw.EdgeInsets.symmetric(vertical: 12),
      decoration: pw.BoxDecoration(
        border: pw.Border(top: pw.BorderSide(color: primaryColor, width: 1)),
      ),
      child: pw.Center(
        child: pw.Text(
          'Generated by Kikhabo - Your Meal Planning Assistant',
          style: pw.TextStyle(
            fontSize: 10,
            color: PdfColors.grey600,
            fontStyle: pw.FontStyle.italic,
          ),
        ),
      ),
    );
  }
}
