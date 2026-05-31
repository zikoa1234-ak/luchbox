package com.pantrybox.presentation.screens.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pantrybox.presentation.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    onBack: () -> Unit,
    viewModel: RecipeViewModel = viewModel()
) {
    val recipeDetails by viewModel.selectedRecipe.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var showCookDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails(recipeId)
    }
    
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedRecipe()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipeDetails?.recipe?.title ?: "Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Toggle favorite */ }) {
                        Icon(
                            if (recipeDetails?.recipe?.isFavorite == true) 
                                Icons.Default.Favorite 
                            else 
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                    IconButton(onClick = { /* TODO: Share recipe */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        },
        bottomBar = {
            if (recipeDetails != null) {
                BottomAppBar {
                    Button(
                        onClick = { showCookDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("I Cooked This Recipe")
                    }
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (recipeDetails == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Recipe not found",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "The recipe you're looking for doesn't exist",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Recipe image placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // TODO: Use Coil to load image from recipeDetails.recipe.imageUrl
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.RestaurantMenu,
                            contentDescription = "Recipe image",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        )
                    }
                }
                
                // Basic info
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = recipeDetails!!.recipe.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = recipeDetails!!.recipe.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Quick stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatItem(
                            icon = Icons.Default.Timer,
                            value = "${recipeDetails!!.recipe.prepTime + recipeDetails!!.recipe.cookTime}",
                            label = "Minutes"
                        )
                        StatItem(
                            icon = Icons.Default.People,
                            value = "${recipeDetails!!.recipe.servings}",
                            label = "Servings"
                        )
                        StatItem(
                            icon = Icons.Default.LocalFireDepartment,
                            value = "${recipeDetails!!.recipe.calories}",
                            label = "Calories"
                        )
                        StatItem(
                            icon = Icons.Default.Tag,
                            value = recipeDetails!!.recipe.difficulty,
                            label = "Difficulty"
                        )
                    }
                }
                
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                
                // Ingredients
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recipeDetails!!.ingredients.forEach { ingredient ->
                        IngredientItem(
                            name = ingredient.ingredientName,
                            quantity = ingredient.quantity,
                            unit = ingredient.unit,
                            notes = ingredient.notes
                        )
                    }
                }
                
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                
                // Steps
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    recipeDetails!!.steps.forEachIndexed { index, step ->
                        StepItem(
                            stepNumber = index + 1,
                            description = step.description
                        )
                    }
                }
                
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                
                // Nutrition
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Nutrition",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    NutritionInfo(recipeDetails!!.recipe)
                }
            }
        }
        
        // Cook confirmation dialog
        if (showCookDialog) {
            AlertDialog(
                onDismissRequest = { showCookDialog = false },
                title = { Text("Confirm Cooking") },
                text = { 
                    Text("This will deduct all used ingredients from your pantry stock. Continue?") 
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.cookRecipe(recipeId)
                            showCookDialog = false
                            onBack()
                        }
                    ) {
                        Text("Yes, I cooked it")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showCookDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
        
        // Error message snackbar
        if (errorMessage != null) {
            LaunchedEffect(errorMessage) {
                viewModel.clearError()
            }
        }
    }
}

@Composable
fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun IngredientItem(
    name: String,
    quantity: Double,
    unit: String,
    notes: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
            if (notes.isNotEmpty()) {
                Text(
                    text = notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        Text(
            text = "$quantity $unit",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StepItem(
    stepNumber: Int,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = stepNumber.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NutritionInfo(recipe: com.pantrybox.data.entities.Recipe) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NutritionRow(
            name = "Calories",
            value = "${recipe.calories} kcal"
        )
        NutritionRow(
            name = "Protein",
            value = "${recipe.protein}g"
        )
        NutritionRow(
            name = "Carbohydrates",
            value = "${recipe.carbs}g"
        )
        NutritionRow(
            name = "Fat",
            value = "${recipe.fat}g"
        )
    }
}

@Composable
fun NutritionRow(name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}