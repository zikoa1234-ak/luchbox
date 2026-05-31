package com.pantrybox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val imageUrl: String? = null,
    val description: String = "",
    val prepTime: Int, // in minutes
    val cookTime: Int, // in minutes
    val servings: Int = 2,
    val calories: Int = 0,
    val protein: Double = 0.0, // in grams
    val carbs: Double = 0.0, // in grams
    val fat: Double = 0.0, // in grams
    val difficulty: String = "Easy", // Easy, Medium, Hard
    val cuisine: String = "General",
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)