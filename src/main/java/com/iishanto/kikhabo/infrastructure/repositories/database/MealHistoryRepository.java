package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.MealEntity;
import com.iishanto.kikhabo.infrastructure.model.MealHistoryEntity;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MealHistoryRepository extends JpaRepository<MealHistoryEntity, Long> {
        @Query(nativeQuery = true, value = "select * from meal_histories where user_id=:user_id order by timestamp desc limit :limit")
        List<MealHistoryEntity> findAllByUserOrderByTimestampDesc(long user_id, int limit);

        // Paginated query for meal history
        @Query("SELECT mh FROM MealHistoryEntity mh WHERE mh.user.id = :userId ORDER BY mh.timestamp DESC")
        Page<MealHistoryEntity> findAllByUserIdOrderByTimestampDesc(@Param("userId") Long userId, Pageable pageable);

        // Analytics Queries - Energy
        @Query(value = "SELECT DATE(FROM_UNIXTIME(mh.timestamp/1000)) as date, " +
                        "COALESCE(SUM(m.total_energy), 0) as totalEnergy " +
                        "FROM meal_histories mh " +
                        "JOIN meals m ON mh.meal_id = m.id " +
                        "WHERE mh.user_id = :userId " +
                        "AND mh.meal_status = 'TAKEN' " +
                        "AND mh.timestamp >= :startTimestamp " +
                        "AND mh.timestamp <= :endTimestamp " +
                        "GROUP BY DATE(FROM_UNIXTIME(mh.timestamp/1000)) " +
                        "ORDER BY date", nativeQuery = true)
        List<Map<String, Object>> getDailyEnergyStats(@Param("userId") Long userId,
                        @Param("startTimestamp") Long startTimestamp,
                        @Param("endTimestamp") Long endTimestamp);

        // Analytics Queries - Cost
        @Query(value = "SELECT DATE(FROM_UNIXTIME(mh.timestamp/1000)) as date, " +
                        "COALESCE(SUM(CAST(g.price_rating_out_of10 AS DECIMAL(10,2))), 0) as totalCost " +
                        "FROM meal_histories mh " +
                        "JOIN groceries g ON g.meal_history_id = mh.id " +
                        "WHERE mh.user_id = :userId " +
                        "AND mh.meal_status = 'TAKEN' " +
                        "AND mh.timestamp >= :startTimestamp " +
                        "AND mh.timestamp <= :endTimestamp " +
                        "GROUP BY DATE(FROM_UNIXTIME(mh.timestamp/1000)) " +
                        "ORDER BY date", nativeQuery = true)
        List<Map<String, Object>> getDailyCostStats(@Param("userId") Long userId,
                        @Param("startTimestamp") Long startTimestamp,
                        @Param("endTimestamp") Long endTimestamp);
}
