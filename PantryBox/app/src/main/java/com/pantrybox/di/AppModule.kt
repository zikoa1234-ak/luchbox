package com.pantrybox.di

import android.content.Context
import androidx.room.Room
import com.pantrybox.data.database.PantryDatabase
import com.pantrybox.data.dao.*
import com.pantrybox.data.repositories.PantryRepository
import com.pantrybox.data.repositories.RecipeRepository
import com.pantrybox.data.sample.SampleDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun providePantryDatabase(@ApplicationContext context: Context): PantryDatabase {
        return Room.databaseBuilder(
            context,
            PantryDatabase::class.java,
            "pantry_database"
        ).fallbackToDestructiveMigration()
         .build()
    }
    
    @Provides
    fun providePantryItemDao(database: PantryDatabase): PantryItemDao {
        return database.pantryItemDao()
    }
    
    @Provides
    fun provideRecipeDao(database: PantryDatabase): RecipeDao {
        return database.recipeDao()
    }
    
    @Provides
    fun provideRecipeIngredientDao(database: PantryDatabase): RecipeIngredientDao {
        return database.recipeIngredientDao()
    }
    
    @Provides
    fun provideRecipeStepDao(database: PantryDatabase): RecipeStepDao {
        return database.recipeStepDao()
    }
    
    @Provides
    fun provideMealPlanDao(database: PantryDatabase): MealPlanDao {
        return database.mealPlanDao()
    }
    
    @Provides
    fun provideShoppingItemDao(database: PantryDatabase): ShoppingItemDao {
        return database.shoppingItemDao()
    }
    
    @Provides
    @Singleton
    fun providePantryRepository(
        pantryItemDao: PantryItemDao
    ): PantryRepository {
        return PantryRepository(pantryItemDao)
    }
    
    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        recipeIngredientDao: RecipeIngredientDao,
        recipeStepDao: RecipeStepDao,
        pantryRepository: PantryRepository
    ): RecipeRepository {
        return RecipeRepository(recipeDao, recipeIngredientDao, recipeStepDao, pantryRepository)
    }
    
    @Provides
    @Singleton
    fun provideSampleDataProvider(): SampleDataProvider {
        return SampleDataProvider()
    }
}