package com.pantrybox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pantrybox.data.entities.*
import com.pantrybox.data.dao.PantryItemDao
import com.pantrybox.data.dao.RecipeDao
import com.pantrybox.data.dao.RecipeIngredientDao
import com.pantrybox.data.dao.RecipeStepDao
import com.pantrybox.data.dao.MealPlanDao
import com.pantrybox.data.dao.ShoppingItemDao

@Database(
    entities = [
        PantryItem::class,
        Recipe::class,
        RecipeIngredient::class,
        RecipeStep::class,
        MealPlan::class,
        ShoppingItem::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PantryDatabase : RoomDatabase() {
    abstract fun pantryItemDao(): PantryItemDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao
    abstract fun recipeStepDao(): RecipeStepDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}