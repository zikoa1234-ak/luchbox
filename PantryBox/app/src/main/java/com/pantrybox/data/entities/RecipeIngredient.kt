package com.pantrybox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "recipe_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recipeId"])]
)
data class RecipeIngredient(
    val recipeId: String,
    val ingredientName: String,
    val quantity: Double,
    val unit: String,
    val notes: String = ""
) {
    val id: String = "$recipeId-$ingredientName"
}