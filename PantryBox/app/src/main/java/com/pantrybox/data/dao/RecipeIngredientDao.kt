package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.RecipeIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeIngredientDao {
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: String): Flow<List<RecipeIngredient>>

    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipeSync(recipeId: String): List<RecipeIngredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: RecipeIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<RecipeIngredient>)

    @Delete
    suspend fun delete(ingredient: RecipeIngredient)

    @Query("DELETE FROM recipe_ingredients WHERE recipeId = :recipeId")
    suspend fun deleteByRecipeId(recipeId: String)
}