// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'meal.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_Grocery _$GroceryFromJson(Map<String, dynamic> json) => _Grocery(
  name: json['name'] as String,
  priceRatingOutOf10: json['priceRatingOutOf10'] as String,
  amountInGm: json['amountInGm'] as String,
);

Map<String, dynamic> _$GroceryToJson(_Grocery instance) => <String, dynamic>{
  'name': instance.name,
  'priceRatingOutOf10': instance.priceRatingOutOf10,
  'amountInGm': instance.amountInGm,
};

_Meal _$MealFromJson(Map<String, dynamic> json) => _Meal(
  id: (json['id'] as num?)?.toInt(),
  mealName: json['mealName'] as String,
  totalEnergy: json['totalEnergy'] as String,
  ingredients: (json['ingredients'] as List<dynamic>?)
      ?.map((e) => e as String)
      .toList(),
  groceries: (json['groceries'] as List<dynamic>?)
      ?.map((e) => Grocery.fromJson(e as Map<String, dynamic>))
      .toList(),
  note: json['note'] as String?,
  rating: (json['rating'] as num?)?.toDouble(),
);

Map<String, dynamic> _$MealToJson(_Meal instance) => <String, dynamic>{
  'id': instance.id,
  'mealName': instance.mealName,
  'totalEnergy': instance.totalEnergy,
  'ingredients': instance.ingredients,
  'groceries': instance.groceries,
  'note': instance.note,
  'rating': instance.rating,
};

_MealPreferenceDto _$MealPreferenceDtoFromJson(Map<String, dynamic> json) =>
    _MealPreferenceDto(
      spicyRating: (json['spicyRating'] as num).toDouble(),
      saltRating: (json['saltRating'] as num).toDouble(),
      dayCount: (json['dayCount'] as num).toInt(),
      priceRating: (json['priceRating'] as num).toDouble(),
      totalMealCount: (json['totalMealCount'] as num).toInt(),
      mealPerDay: (json['mealPerDay'] as num).toInt(),
      agesOfTheMembers: (json['agesOfTheMembers'] as List<dynamic>)
          .map((e) => (e as num).toInt())
          .toList(),
    );

Map<String, dynamic> _$MealPreferenceDtoToJson(_MealPreferenceDto instance) =>
    <String, dynamic>{
      'spicyRating': instance.spicyRating,
      'saltRating': instance.saltRating,
      'dayCount': instance.dayCount,
      'priceRating': instance.priceRating,
      'totalMealCount': instance.totalMealCount,
      'mealPerDay': instance.mealPerDay,
      'agesOfTheMembers': instance.agesOfTheMembers,
    };

_MealRatingStatusDto _$MealRatingStatusDtoFromJson(Map<String, dynamic> json) =>
    _MealRatingStatusDto(
      id: (json['id'] as num).toInt(),
      rating: (json['rating'] as num).toInt(),
      userNote: json['userNote'] as String,
      mealStatus: json['mealStatus'] as String,
    );

Map<String, dynamic> _$MealRatingStatusDtoToJson(
  _MealRatingStatusDto instance,
) => <String, dynamic>{
  'id': instance.id,
  'rating': instance.rating,
  'userNote': instance.userNote,
  'mealStatus': instance.mealStatus,
};

_GroceryPlanningPromptResponse _$GroceryPlanningPromptResponseFromJson(
  Map<String, dynamic> json,
) => _GroceryPlanningPromptResponse(
  status: json['status'] as String,
  message: json['message'] as String,
  data: MealInformation.fromJson(json['data'] as Map<String, dynamic>),
);

Map<String, dynamic> _$GroceryPlanningPromptResponseToJson(
  _GroceryPlanningPromptResponse instance,
) => <String, dynamic>{
  'status': instance.status,
  'message': instance.message,
  'data': instance.data,
};

_MealInformation _$MealInformationFromJson(Map<String, dynamic> json) =>
    _MealInformation(
      totalMeals: (json['totalMeals'] as num).toInt(),
      meals: (json['meals'] as List<dynamic>)
          .map((e) => Meal.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$MealInformationToJson(_MealInformation instance) =>
    <String, dynamic>{
      'totalMeals': instance.totalMeals,
      'meals': instance.meals,
    };
