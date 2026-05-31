package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.MealPlan
import java.util.Date
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM meal_plans WHERE date = :date ORDER BY mealType ASC")
    fun getMealsForDate(date: Date): Flow<List<MealPlan>>

    @Query("SELECT * FROM meal_plans WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC, mealType ASC")
    fun getMealsForWeek(startDate: Date, endDate: Date): Flow<List<MealPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mealPlan: MealPlan)

    @Update
    suspend fun update(mealPlan: MealPlan)

    @Delete
    suspend fun delete(mealPlan: MealPlan)

    @Query("DELETE FROM meal_plans WHERE date = :date AND mealType = :mealType")
    suspend fun deleteByDateAndMealType(date: Date, mealType: String)

    @Query("SELECT DISTINCT date FROM meal_plans WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getPlannedDates(startDate: Date, endDate: Date): List<Date>
}