package com.pantrybox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "meal_plans",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["date", "mealType"])]
)
data class MealPlan(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val date: Date,
    val mealType: String, // Breakfast, Lunch, Dinner, Snack
    val recipeId: String? = null,
    val customMeal: String = "",
    val isCompleted: Boolean = false
)