package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY createdAt DESC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: String): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE LOWER(title) LIKE LOWER('%' || :searchQuery || '%') OR LOWER(description) LIKE LOWER('%' || :searchQuery || '%')")
    fun searchRecipes(searchQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE difficulty = :difficulty")
    fun getRecipesByDifficulty(difficulty: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE prepTime + cookTime <= :maxTime")
    fun getQuickRecipes(maxTime: Int): Flow<List<Recipe>>
}