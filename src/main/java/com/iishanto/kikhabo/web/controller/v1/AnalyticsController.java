package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.usercase.analytics.*;
import com.iishanto.kikhabo.web.dto.analytics.CostDataDto;
import com.iishanto.kikhabo.web.dto.analytics.EnergyDataDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@AllArgsConstructor
@Tag(name = "Analytics", description = "Energy consumption and meal cost analytics")
public class AnalyticsController {

    // Use Cases
    GetDailyEnergyUseCase getDailyEnergyUseCase;
    GetWeeklyEnergyUseCase getWeeklyEnergyUseCase;
    GetMonthlyEnergyUseCase getMonthlyEnergyUseCase;
    GetDailyCostUseCase getDailyCostUseCase;
    GetWeeklyCostUseCase getWeeklyCostUseCase;
    GetMonthlyCostUseCase getMonthlyCostUseCase;

    // ===== Energy Endpoints =====

    @GetMapping("/energy/daily")
    public ResponseEntity<SuccessResponse<List<EnergyDataDto>>> getDailyEnergy(
            @RequestParam(required = false, defaultValue = "7") Integer days,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<EnergyDataDto> data;
        if (startDate != null && endDate != null) {
            data = EnergyDataDto.fromDailyList(getDailyEnergyUseCase.execute(startDate, endDate));
        } else {
            data = EnergyDataDto.fromDailyList(getDailyEnergyUseCase.execute(days));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Daily energy data retrieved", data),
                HttpStatus.OK);
    }

    @GetMapping("/energy/weekly")
    public ResponseEntity<SuccessResponse<List<EnergyDataDto>>> getWeeklyEnergy(
            @RequestParam(required = false, defaultValue = "4") Integer weeks,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<EnergyDataDto> data;
        if (startDate != null && endDate != null) {
            data = EnergyDataDto.fromWeeklyList(getWeeklyEnergyUseCase.execute(startDate, endDate));
        } else {
            data = EnergyDataDto.fromWeeklyList(getWeeklyEnergyUseCase.execute(weeks));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Weekly energy data retrieved", data),
                HttpStatus.OK);
    }

    @GetMapping("/energy/monthly")
    public ResponseEntity<SuccessResponse<List<EnergyDataDto>>> getMonthlyEnergy(
            @RequestParam(required = false, defaultValue = "6") Integer months,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<EnergyDataDto> data;
        if (startDate != null && endDate != null) {
            data = EnergyDataDto.fromMonthlyList(getMonthlyEnergyUseCase.execute(startDate, endDate));
        } else {
            data = EnergyDataDto.fromMonthlyList(getMonthlyEnergyUseCase.execute(months));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Monthly energy data retrieved", data),
                HttpStatus.OK);
    }

    // ===== Cost Endpoints =====

    @GetMapping("/cost/daily")
    public ResponseEntity<SuccessResponse<List<CostDataDto>>> getDailyCost(
            @RequestParam(required = false, defaultValue = "7") Integer days,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<CostDataDto> data;
        if (startDate != null && endDate != null) {
            data = CostDataDto.fromDailyList(getDailyCostUseCase.execute(startDate, endDate));
        } else {
            data = CostDataDto.fromDailyList(getDailyCostUseCase.execute(days));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Daily cost data retrieved", data), HttpStatus.OK);
    }

    @GetMapping("/cost/weekly")
    public ResponseEntity<SuccessResponse<List<CostDataDto>>> getWeeklyCost(
            @RequestParam(required = false, defaultValue = "4") Integer weeks,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<CostDataDto> data;
        if (startDate != null && endDate != null) {
            data = CostDataDto.fromWeeklyList(getWeeklyCostUseCase.execute(startDate, endDate));
        } else {
            data = CostDataDto.fromWeeklyList(getWeeklyCostUseCase.execute(weeks));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Weekly cost data retrieved", data),
                HttpStatus.OK);
    }

    @GetMapping("/cost/monthly")
    public ResponseEntity<SuccessResponse<List<CostDataDto>>> getMonthlyCost(
            @RequestParam(required = false, defaultValue = "6") Integer months,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws Exception {

        List<CostDataDto> data;
        if (startDate != null && endDate != null) {
            data = CostDataDto.fromMonthlyList(getMonthlyCostUseCase.execute(startDate, endDate));
        } else {
            data = CostDataDto.fromMonthlyList(getMonthlyCostUseCase.execute(months));
        }

        return new ResponseEntity<>(new SuccessResponse<>("success", "Monthly cost data retrieved", data),
                HttpStatus.OK);
    }
}
