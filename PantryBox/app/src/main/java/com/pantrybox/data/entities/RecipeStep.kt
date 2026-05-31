package com.pantrybox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "recipe_steps",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recipeId", "stepNumber"])]
)
data class RecipeStep(
    val recipeId: String,
    val stepNumber: Int,
    val description: String,
    val imageUrl: String? = null
) {
    val id: String = "$recipeId-$stepNumber"
}