package com.pantrybox.data.repositories

import com.pantrybox.data.dao.RecipeDao
import com.pantrybox.data.dao.RecipeIngredientDao
import com.pantrybox.data.dao.RecipeStepDao
import com.pantrybox.data.entities.Recipe
import com.pantrybox.data.entities.RecipeIngredient
import com.pantrybox.data.entities.RecipeStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeIngredientDao: RecipeIngredientDao,
    private val recipeStepDao: RecipeStepDao,
    private val pantryRepository: PantryRepository
) {
    // Basic CRUD operations
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun getRecipeById(id: String): RecipeWithDetails? {
        val recipe = recipeDao.getRecipeById(id) ?: return null
        val ingredients = recipeIngredientDao.getIngredientsForRecipeSync(id)
        val steps = recipeStepDao.getStepsForRecipeSync(id)
        return RecipeWithDetails(recipe, ingredients, steps)
    }

    suspend fun insertRecipe(recipe: Recipe, ingredients: List<RecipeIngredient>, steps: List<RecipeStep>) {
        recipeDao.insert(recipe)
        recipeIngredientDao.insertAll(ingredients)
        recipeStepDao.insertAll(steps)
    }

    suspend fun updateRecipe(recipe: Recipe, ingredients: List<RecipeIngredient>, steps: List<RecipeStep>) {
        recipeDao.update(recipe)
        recipeIngredientDao.deleteByRecipeId(recipe.id)
        recipeStepDao.deleteByRecipeId(recipe.id)
        recipeIngredientDao.insertAll(ingredients)
        recipeStepDao.insertAll(steps)
    }

    suspend fun deleteRecipe(recipeId: String) {
        recipeDao.delete(recipeDao.getRecipeById(recipeId) ?: return)
        recipeIngredientDao.deleteByRecipeId(recipeId)
        recipeStepDao.deleteByRecipeId(recipeId)
    }

    // Search and filter
    fun searchRecipes(query: String): Flow<List<Recipe>> = recipeDao.searchRecipes(query)
    fun getFavoriteRecipes(): Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()
    fun getQuickRecipes(maxTime: Int): Flow<List<Recipe>> = recipeDao.getQuickRecipes(maxTime)

    // Recipe matching logic
    suspend fun getMatchingRecipes(): Flow<List<RecipeWithMatch>> {
        return combine(
            recipeDao.getAllRecipes(),
            pantryRepository.getAllItems()
        ) { recipes, pantryItems ->
            recipes.map { recipe ->
                val matchResult = calculateRecipeMatch(recipe.id, pantryItems)
                RecipeWithMatch(recipe, matchResult.matchPercentage, matchResult.missingIngredients)
            }.sortedByDescending { it.matchPercentage }
        }
    }

    private suspend fun calculateRecipeMatch(recipeId: String, pantryItems: List<com.pantrybox.data.entities.PantryItem>): MatchResult {
        val recipeIngredients = recipeIngredientDao.getIngredientsForRecipeSync(recipeId)
        
        if (recipeIngredients.isEmpty()) return MatchResult(0.0, emptyList())

        var matchedCount = 0
        val missingIngredients = mutableListOf<MissingIngredient>()

        for (recipeIngredient in recipeIngredients) {
            val pantryItem = pantryItems.find { 
                it.name.equals(recipeIngredient.ingredientName, ignoreCase = true) &&
                it.unit.equals(recipeIngredient.unit, ignoreCase = true) &&
                it.quantity >= recipeIngredient.quantity
            }
            
            if (pantryItem != null) {
                matchedCount++
            } else {
                val partialMatch = pantryItems.find { 
                    it.name.equals(recipeIngredient.ingredientName, ignoreCase = true)
                }
                missingIngredients.add(
                    MissingIngredient(
                        name = recipeIngredient.ingredientName,
                        requiredQuantity = recipeIngredient.quantity,
                        requiredUnit = recipeIngredient.unit,
                        availableQuantity = partialMatch?.quantity ?: 0.0,
                        availableUnit = partialMatch?.unit ?: ""
                    )
                )
            }
        }

        val matchPercentage = (matchedCount.toDouble() / recipeIngredients.size) * 100
        return MatchResult(matchPercentage, missingIngredients)
    }

    suspend fun cookRecipe(recipeId: String): Boolean {
        val recipeIngredients = recipeIngredientDao.getIngredientsForRecipeSync(recipeId)
        
        // First check if all ingredients are available
        val allAvailable = recipeIngredients.all { ingredient ->
            val pantryItem = pantryRepository.getAllItems().first().find { 
                it.name.equals(ingredient.ingredientName, ignoreCase = true) &&
                it.unit.equals(ingredient.unit, ignoreCase = true) &&
                it.quantity >= ingredient.quantity
            }
            pantryItem != null
        }

        if (!allAvailable) return false

        // Deduct all ingredients
        var allDeducted = true
        for (ingredient in recipeIngredients) {
            val success = pantryRepository.deductIngredient(
                ingredient.ingredientName,
                ingredient.quantity,
                ingredient.unit
            )
            if (!success) allDeducted = false
        }

        return allDeducted
    }
}

data class RecipeWithDetails(
    val recipe: Recipe,
    val ingredients: List<RecipeIngredient>,
    val steps: List<RecipeStep>
)

data class RecipeWithMatch(
    val recipe: Recipe,
    val matchPercentage: Double,
    val missingIngredients: List<MissingIngredient>
)

data class MissingIngredient(
    val name: String,
    val requiredQuantity: Double,
    val requiredUnit: String,
    val availableQuantity: Double,
    val availableUnit: String
)

data class MatchResult(
    val matchPercentage: Double,
    val missingIngredients: List<MissingIngredient>
)