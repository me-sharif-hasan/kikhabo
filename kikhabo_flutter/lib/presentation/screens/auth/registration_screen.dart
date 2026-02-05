import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_text_styles.dart';
import '../../../core/utils/validators.dart';
import '../../../data/models/user.dart';
import '../../../domain/providers/auth_provider.dart';
import '../../widgets/glass_button.dart';
import '../../widgets/glass_card.dart';
import '../../widgets/glass_text_field.dart';

class RegistrationScreen extends ConsumerStatefulWidget {
  const RegistrationScreen({super.key});

  @override
  ConsumerState<RegistrationScreen> createState() => _RegistrationScreenState();
}

class _RegistrationScreenState extends ConsumerState<RegistrationScreen> {
  final _formKey = GlobalKey<FormState>();
  
  // Controllers
  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _weightController = TextEditingController();
  final _heightController = TextEditingController();
  final _dobController = TextEditingController();
  
  // Dropdown values
  String _selectedGender = 'Male';
  String _selectedCountry = 'Bangladesh';
  String _selectedReligion = 'Islam';

  // Available Options
  final List<String> _genders = ['Male', 'Female', 'Other'];
  final List<String> _countries = ['Bangladesh', 'India', 'USA', 'UK', 'Canada'];
  final List<String> _religions = ['Islam', 'Hinduism', 'Christianity', 'Buddhism', 'Other'];

  DateTime? _selectedDate;

  @override
  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    _weightController.dispose();
    _heightController.dispose();
    _dobController.dispose();
    super.dispose();
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime(2000),
      firstDate: DateTime(1950),
      lastDate: DateTime.now(),
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: ColorScheme.dark(
              primary: AppColors.primary,
              onPrimary: Colors.white,
              surface: AppColors.surface,
              onSurface: Colors.white,
            ),
          ),
          child: child!,
        );
      },
    );
    if (picked != null && picked != _selectedDate) {
      setState(() {
        _selectedDate = picked;
        _dobController.text = DateFormat('yyyy-MM-dd').format(picked);
      });
    }
  }

  void _onRegister() async {
    if (_formKey.currentState!.validate()) {
      if (_selectedDate == null) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Please select Date of Birth')),
        );
        return;
      }

      final userDto = UserDto(
        firstName: _firstNameController.text.trim(),
        lastName: _lastNameController.text.trim(),
        email: _emailController.text.trim(),
        password: _passwordController.text,
        gender: _selectedGender,
        country: _selectedCountry,
        religion: _selectedReligion,
        dateOfBirth: _dobController.text,
        weightInKg: double.parse(_weightController.text),
        heightInFt: double.parse(_heightController.text),
      );

      await ref.read(authProvider.notifier).register(userDto);
      
      final state = ref.read(authProvider);
      if (state.error != null && mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(state.error!), backgroundColor: AppColors.error),
        );
      } else if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Registration Successful! Please Login.')),
        );
        context.pop(); // Go back to Login
      }
    }
  }

  Widget _buildDropdown(String label, String value, List<String> items, Function(String?) onChanged) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: AppTextStyles.labelLarge),
        const SizedBox(height: 8),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 16),
          decoration: BoxDecoration(
            color: AppColors.glass.withOpacity(0.05),
            borderRadius: BorderRadius.circular(15),
            border: Border.all(color: AppColors.glassBorder.withOpacity(0.3)),
          ),
          child: DropdownButtonHideUnderline(
            child: DropdownButton<String>(
              value: value,
              isExpanded: true,
              dropdownColor: AppColors.surface,
              style: AppTextStyles.bodyLarge,
              icon: const Icon(Icons.arrow_drop_down, color: AppColors.primaryLight),
              onChanged: onChanged,
              items: items.map<DropdownMenuItem<String>>((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
            ),
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          gradient: AppColors.bgGradient1,
        ),
        child: SafeArea(
          child: Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(24),
              child: GlassCard(
                blur: 20,
                child: Form(
                  key: _formKey,
                  child: Column(
                    children: [
                      Text('Create Account', style: AppTextStyles.headlineMedium),
                      const SizedBox(height: 24),
                      
                      Row(
                        children: [
                          Expanded(child: GlassTextField(
                            controller: _firstNameController,
                            hintText: 'First Name',
                            labelText: 'First Name',
                            validator: (v) => Validators.validateRequired(v, 'First Name'),
                          )),
                          const SizedBox(width: 16),
                          Expanded(child: GlassTextField(
                            controller: _lastNameController,
                            hintText: 'Last Name',
                            labelText: 'Last Name',
                            validator: (v) => Validators.validateRequired(v, 'Last Name'),
                          )),
                        ],
                      ),
                      const SizedBox(height: 16),
                      
                      GlassTextField(
                        controller: _emailController,
                        hintText: 'someone@example.com',
                        labelText: 'Email',
                        keyboardType: TextInputType.emailAddress,
                        validator: Validators.validateEmail,
                      ),
                      const SizedBox(height: 16),
                      
                      GlassTextField(
                        controller: _passwordController,
                        hintText: '********',
                        labelText: 'Password',
                        isPassword: true,
                        validator: Validators.validatePassword,
                      ),
                      const SizedBox(height: 16),
                      
                      Row(
                        children: [
                          Expanded(child: _buildDropdown('Gender', _selectedGender, _genders, (v) => setState(() => _selectedGender = v!))),
                          const SizedBox(width: 16),
                          Expanded(child: _buildDropdown('Country', _selectedCountry, _countries, (v) => setState(() => _selectedCountry = v!))),
                        ],
                      ),
                      const SizedBox(height: 16),
                      
                      Row(
                        children: [
                          Expanded(child: _buildDropdown('Religion', _selectedReligion, _religions, (v) => setState(() => _selectedReligion = v!))),
                          const SizedBox(width: 16),
                          Expanded(child: GestureDetector(
                            onTap: () => _selectDate(context),
                            child: AbsorbPointer(
                              child: GlassTextField(
                                controller: _dobController,
                                hintText: 'YYYY-MM-DD',
                                labelText: 'Date of Birth',
                                suffixIcon: const Icon(Icons.calendar_today, color: AppColors.primaryLight),
                                validator: (v) => Validators.validateRequired(v, 'DOB'),
                              ),
                            ),
                          )),
                        ],
                      ),
                      const SizedBox(height: 16),
                      
                      Row(
                        children: [
                          Expanded(child: GlassTextField(
                            controller: _weightController,
                            hintText: 'kg',
                            labelText: 'Weight (kg)',
                            keyboardType: TextInputType.number,
                            validator: (v) => Validators.validateNumber(v, 'Weight'),
                          )),
                          const SizedBox(width: 16),
                          Expanded(child: GlassTextField(
                            controller: _heightController,
                            hintText: 'ft',
                            labelText: 'Height (ft)',
                            keyboardType: TextInputType.number,
                            validator: (v) => Validators.validateNumber(v, 'Height'),
                          )),
                        ],
                      ),
                      const SizedBox(height: 32),
                      
                      Consumer(builder: (context, ref, _) {
                        final authState = ref.watch(authProvider);
                        return GlassButton(
                          text: 'Register',
                          onPressed: _onRegister,
                          isLoading: authState.isLoading,
                          gradient: AppColors.bgGradient2,
                        );
                      }),
                      
                      const SizedBox(height: 16),
                      TextButton(
                        onPressed: () => context.pop(),
                        child: Text(
                          'Already have an account? Login',
                          style: AppTextStyles.bodyMedium.copyWith(color: AppColors.accentLight),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
