package com.pantrybox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pantrybox.presentation.screens.pantry.AddEditPantryItemScreen
import com.pantrybox.presentation.screens.pantry.PantryScreen
import com.pantrybox.presentation.screens.recipes.RecipeDetailsScreen
import com.pantrybox.presentation.screens.recipes.RecipesScreen

@Composable
fun PantryBoxNavHost(
    navController: NavHostController,
    startDestination: String = Destination.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding
        composable(Destination.Onboarding.route) {
            // TODO: Implement OnboardingScreen
        }
        
        // Home
        composable(Destination.Home.route) {
            // TODO: Implement HomeScreen
        }
        
        // Pantry screens
        composable(Destination.Pantry.route) {
            PantryScreen(
                onAddItem = {
                    navController.navigate(Destination.AddEditPantryItem.createRoute())
                },
                onEditItem = { itemId ->
                    navController.navigate(Destination.AddEditPantryItem.createRoute(itemId))
                }
            )
        }
        
        composable(
            route = Destination.AddEditPantryItem.route,
            arguments = listOf(
                navArgument(Destination.AddEditPantryItem.ARG_ITEM_ID) {
                    defaultValue = "new"
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Destination.AddEditPantryItem.ARG_ITEM_ID)
            AddEditPantryItemScreen(
                itemId = if (itemId == "new") null else itemId,
                onBack = { navController.popBackStack() }
            )
        }
        
        // Recipe screens
        composable(Destination.Recipes.route) {
            RecipesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            )
        }
        
        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                navArgument(Destination.RecipeDetails.ARG_RECIPE_ID) {
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(Destination.RecipeDetails.ARG_RECIPE_ID)
                ?: return@composable
            RecipeDetailsScreen(
                recipeId = recipeId,
                onBack = { navController.popBackStack() }
            )
        }
        
        // Other screens
        composable(Destination.WeeklyPlanner.route) {
            // TODO: Implement WeeklyPlannerScreen
        }
        
        composable(Destination.ShoppingList.route) {
            // TODO: Implement ShoppingListScreen
        }
        
        composable(Destination.Progress.route) {
            // TODO: Implement ProgressScreen
        }
        
        composable(Destination.Settings.route) {
            // TODO: Implement SettingsScreen
        }
    }
}