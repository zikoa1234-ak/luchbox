package com.pantrybox.data.sample

import com.pantrybox.data.entities.*
import java.util.*

class SampleDataProvider {
    
    fun getSamplePantryItems(): List<PantryItem> {
        return listOf(
            PantryItem(
                name = "Tomatoes",
                quantity = 5.0,
                unit = "pcs",
                category = "Vegetables",
                minThreshold = 2.0
            ),
            PantryItem(
                name = "Eggs",
                quantity = 12.0,
                unit = "pcs",
                category = "Dairy",
                minThreshold = 4.0
            ),
            PantryItem(
                name = "Onions",
                quantity = 3.0,
                unit = "pcs",
                category = "Vegetables",
                minThreshold = 1.0
            ),
            PantryItem(
                name = "Rice",
                quantity = 2.0,
                unit = "kg",
                category = "Grains",
                minThreshold = 0.5
            ),
            PantryItem(
                name = "Chicken Breast",
                quantity = 1.0,
                unit = "kg",
                category = "Meat",
                minThreshold = 0.2
            ),
            PantryItem(
                name = "Milk",
                quantity = 1.0,
                unit = "L",
                category = "Dairy",
                minThreshold = 0.5
            ),
            PantryItem(
                name = "Flour",
                quantity = 1.5,
                unit = "kg",
                category = "Grains",
                minThreshold = 0.3
            ),
            PantryItem(
                name = "Sugar",
                quantity = 0.5,
                unit = "kg",
                category = "Baking",
                minThreshold = 0.1
            ),
            PantryItem(
                name = "Salt",
                quantity = 0.2,
                unit = "kg",
                category = "Spices",
                minThreshold = 0.05
            ),
            PantryItem(
                name = "Olive Oil",
                quantity = 0.5,
                unit = "L",
                category = "Condiments",
                minThreshold = 0.1
            )
        )
    }
    
    fun getSampleRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                title = "Tomato Egg Stir Fry",
                description = "A simple and delicious Chinese dish that's ready in minutes.",
                prepTime = 10,
                cookTime = 10,
                servings = 2,
                calories = 320,
                protein = 18.0,
                carbs = 25.0,
                fat = 15.0,
                difficulty = "Easy",
                cuisine = "Chinese"
            ),
            Recipe(
                title = "Chicken Fried Rice",
                description = "Classic fried rice with tender chicken and vegetables.",
                prepTime = 15,
                cookTime = 15,
                servings = 4,
                calories = 450,
                protein = 25.0,
                carbs = 55.0,
                fat = 12.0,
                difficulty = "Medium",
                cuisine = "Chinese"
            ),
            Recipe(
                title = "Simple Omelette",
                description = "Fluffy omelette perfect for breakfast or a quick meal.",
                prepTime = 5,
                cookTime = 5,
                servings = 1,
                calories = 280,
                protein = 18.0,
                carbs = 2.0,
                fat = 22.0,
                difficulty = "Easy",
                cuisine = "French"
            ),
            Recipe(
                title = "Tomato Soup",
                description = "Creamy tomato soup with fresh herbs.",
                prepTime = 15,
                cookTime = 25,
                servings = 4,
                calories = 180,
                protein = 4.0,
                carbs = 22.0,
                fat = 8.0,
                difficulty = "Easy",
                cuisine = "American"
            ),
            Recipe(
                title = "Chicken Curry",
                description = "Spicy chicken curry with aromatic spices.",
                prepTime = 20,
                cookTime = 40,
                servings = 4,
                calories = 380,
                protein = 35.0,
                carbs = 18.0,
                fat = 20.0,
                difficulty = "Medium",
                cuisine = "Indian"
            )
        )
    }
    
    fun getSampleRecipeIngredients(): Map<String, List<RecipeIngredient>> {
        return mapOf(
            // Tomato Egg Stir Fry ingredients
            "tomato-egg-stir-fry" to listOf(
                RecipeIngredient(
                    recipeId = "tomato-egg-stir-fry",
                    ingredientName = "Tomatoes",
                    quantity = 3.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "tomato-egg-stir-fry",
                    ingredientName = "Eggs",
                    quantity = 4.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "tomato-egg-stir-fry",
                    ingredientName = "Onions",
                    quantity = 1.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "tomato-egg-stir-fry",
                    ingredientName = "Salt",
                    quantity = 0.005,
                    unit = "kg"
                ),
                RecipeIngredient(
                    recipeId = "tomato-egg-stir-fry",
                    ingredientName = "Olive Oil",
                    quantity = 0.02,
                    unit = "L"
                )
            ),
            
            // Chicken Fried Rice ingredients
            "chicken-fried-rice" to listOf(
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Rice",
                    quantity = 0.5,
                    unit = "kg"
                ),
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Chicken Breast",
                    quantity = 0.3,
                    unit = "kg"
                ),
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Eggs",
                    quantity = 2.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Onions",
                    quantity = 1.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Salt",
                    quantity = 0.005,
                    unit = "kg"
                ),
                RecipeIngredient(
                    recipeId = "chicken-fried-rice",
                    ingredientName = "Olive Oil",
                    quantity = 0.03,
                    unit = "L"
                )
            ),
            
            // Simple Omelette ingredients
            "simple-omelette" to listOf(
                RecipeIngredient(
                    recipeId = "simple-omelette",
                    ingredientName = "Eggs",
                    quantity = 3.0,
                    unit = "pcs"
                ),
                RecipeIngredient(
                    recipeId = "simple-omelette",
                    ingredientName = "Salt",
                    quantity = 0.002,
                    unit = "kg"
                ),
                RecipeIngredient(
                    recipeId = "simple-omelette",
                    ingredientName = "Olive Oil",
                    quantity = 0.01,
                    unit = "L"
                )
            )
        )
    }
    
    fun getSampleRecipeSteps(): Map<String, List<RecipeStep>> {
        return mapOf(
            // Tomato Egg Stir Fry steps
            "tomato-egg-stir-fry" to listOf(
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 1,
                    description = "Chop tomatoes and onions into small pieces."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 2,
                    description = "Beat eggs in a bowl with a pinch of salt."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 3,
                    description = "Heat oil in a pan over medium heat."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 4,
                    description = "Add onions and cook until translucent."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 5,
                    description = "Add tomatoes and cook until soft."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 6,
                    description = "Pour beaten eggs over the vegetables."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 7,
                    description = "Cook until eggs are set, stirring occasionally."
                ),
                RecipeStep(
                    recipeId = "tomato-egg-stir-fry",
                    stepNumber = 8,
                    description = "Season with salt to taste and serve hot."
                )
            ),
            
            // Chicken Fried Rice steps
            "chicken-fried-rice" to listOf(
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 1,
                    description = "Cook rice according to package instructions and let cool."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 2,
                    description = "Cut chicken breast into small cubes."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 3,
                    description = "Heat oil in a wok or large pan over high heat."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 4,
                    description = "Add chicken and cook until browned and cooked through."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 5,
                    description = "Push chicken to one side, add beaten eggs to the other side."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 6,
                    description = "Scramble eggs, then mix with chicken."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 7,
                    description = "Add chopped onions and cook for 2 minutes."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 8,
                    description = "Add cooked rice and stir fry until heated through."
                ),
                RecipeStep(
                    recipeId = "chicken-fried-rice",
                    stepNumber = 9,
                    description = "Season with salt and serve immediately."
                )
            )
        )
    }
    
    fun getSampleShoppingItems(): List<ShoppingItem> {
        return listOf(
            ShoppingItem(
                name = "Bread",
                quantity = 1.0,
                unit = "loaf",
                category = "Bakery"
            ),
            ShoppingItem(
                name = "Cheese",
                quantity = 0.2,
                unit = "kg",
                category = "Dairy"
            ),
            ShoppingItem(
                name = "Potatoes",
                quantity = 2.0,
                unit = "kg",
                category = "Vegetables"
            )
        )
    }
}