package com.pantrybox.presentation.screens.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pantrybox.presentation.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onRecipeClick: (String) -> Unit,
    viewModel: RecipeViewModel = viewModel()
) {
    val matchingRecipes by viewModel.matchingRecipes.collectAsState()
    val allRecipes by viewModel.allRecipes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchRecipes(searchQuery)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipes") },
                actions = {
                    IconButton(onClick = { /* TODO: Open search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search recipes...") },
                singleLine = true
            )
            
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                ) {
                    Text(
                        text = "Available (${matchingRecipes.count { it.matchPercentage >= 90 }})",
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                ) {
                    Text(
                        text = "Partial (${matchingRecipes.count { it.matchPercentage in 50.0..89.9 }})",
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                ) {
                    Text(
                        text = "All Recipes",
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
            
            // Recipe list based on selected tab
            val recipesToShow = when (selectedTab) {
                0 -> matchingRecipes.filter { it.matchPercentage >= 90 }
                1 -> matchingRecipes.filter { it.matchPercentage in 50.0..89.9 }
                else -> matchingRecipes
            }
            
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (recipesToShow.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = when (selectedTab) {
                                0 -> "No fully available recipes"
                                1 -> "No partially available recipes"
                                else -> "No recipes found"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = when (selectedTab) {
                                0 -> "Add more ingredients to your pantry to see available recipes"
                                1 -> "You need more ingredients for these recipes"
                                else -> "Try searching for something else"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recipesToShow) { recipeWithMatch ->
                        RecipeCard(
                            recipe = recipeWithMatch.recipe,
                            matchPercentage = recipeWithMatch.matchPercentage,
                            missingIngredients = recipeWithMatch.missingIngredients,
                            onClick = { onRecipeClick(recipeWithMatch.recipe.id) }
                        )
                    }
                }
            }
            
            // Error message snackbar
            if (errorMessage != null) {
                LaunchedEffect(errorMessage) {
                    viewModel.clearError()
                }
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: com.pantrybox.data.entities.Recipe,
    matchPercentage: Double,
    missingIngredients: List<com.pantrybox.data.repositories.MissingIngredient>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                
                // Match percentage indicator
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = matchPercentage / 100,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(36.dp),
                        color = when {
                            matchPercentage >= 90 -> Color.Green
                            matchPercentage >= 50 -> Color.Yellow
                            else -> Color.Red
                        }
                    )
                    Text(
                        text = "${matchPercentage.toInt()}%",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Recipe info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Timer,
                        contentDescription = "Time",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${recipe.prepTime + recipe.cookTime} min",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                // Difficulty
                Chip(
                    onClick = {},
                    label = { Text(recipe.difficulty) },
                    colors = ChipDefaults.chipColors(
                        containerColor = when (recipe.difficulty) {
                            "Easy" -> Color.Green.copy(alpha = 0.2f)
                            "Medium" -> Color.Yellow.copy(alpha = 0.2f)
                            else -> Color.Red.copy(alpha = 0.2f)
                        }
                    )
                )
                
                // Calories
                Text(
                    text = "${recipe.calories} kcal",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            // Missing ingredients warning
            if (missingIngredients.isNotEmpty() && matchPercentage < 100) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Missing: ${missingIngredients.joinToString { it.name }}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            // Favorite indicator
            if (recipe.isFavorite) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Favorite",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}