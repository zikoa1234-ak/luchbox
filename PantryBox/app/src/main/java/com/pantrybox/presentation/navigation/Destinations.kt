package com.pantrybox.presentation.navigation

sealed class Destination(val route: String) {
    // Onboarding
    object Onboarding : Destination("onboarding")
    
    // Main screens
    object Home : Destination("home")
    object Pantry : Destination("pantry")
    object AddEditPantryItem : Destination("add_edit_pantry/{itemId}") {
        const val ARG_ITEM_ID = "itemId"
        fun createRoute(itemId: String? = null) = "add_edit_pantry/${itemId ?: "new"}"
    }
    object Recipes : Destination("recipes")
    object RecipeDetails : Destination("recipe_details/{recipeId}") {
        const val ARG_RECIPE_ID = "recipeId"
        fun createRoute(recipeId: String) = "recipe_details/$recipeId"
    }
    object WeeklyPlanner : Destination("weekly_planner")
    object ShoppingList : Destination("shopping_list")
    object Progress : Destination("progress")
    object Settings : Destination("settings")
}

val mainDestinations = listOf(
    Destination.Home,
    Destination.Pantry,
    Destination.Recipes,
    Destination.WeeklyPlanner,
    Destination.ShoppingList,
    Destination.Progress,
    Destination.Settings
)