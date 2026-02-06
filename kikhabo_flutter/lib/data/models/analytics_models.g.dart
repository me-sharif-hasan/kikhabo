// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'analytics_models.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_EnergyData _$EnergyDataFromJson(Map<String, dynamic> json) => _EnergyData(
  period: json['period'] as String,
  totalEnergy: (json['totalEnergy'] as num).toDouble(),
);

Map<String, dynamic> _$EnergyDataToJson(_EnergyData instance) =>
    <String, dynamic>{
      'period': instance.period,
      'totalEnergy': instance.totalEnergy,
    };

_CostData _$CostDataFromJson(Map<String, dynamic> json) => _CostData(
  period: json['period'] as String,
  costPercentage: (json['costPercentage'] as num).toDouble(),
);

Map<String, dynamic> _$CostDataToJson(_CostData instance) => <String, dynamic>{
  'period': instance.period,
  'costPercentage': instance.costPercentage,
};
