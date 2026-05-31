package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.RecipeStep
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeStepDao {
    @Query("SELECT * FROM recipe_steps WHERE recipeId = :recipeId ORDER BY stepNumber ASC")
    fun getStepsForRecipe(recipeId: String): Flow<List<RecipeStep>>

    @Query("SELECT * FROM recipe_steps WHERE recipeId = :recipeId ORDER BY stepNumber ASC")
    suspend fun getStepsForRecipeSync(recipeId: String): List<RecipeStep>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(step: RecipeStep)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(steps: List<RecipeStep>)

    @Delete
    suspend fun delete(step: RecipeStep)

    @Query("DELETE FROM recipe_steps WHERE recipeId = :recipeId")
    suspend fun deleteByRecipeId(recipeId: String)
}