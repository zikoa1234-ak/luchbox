package com.pantrybox.presentation.screens.pantry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pantrybox.presentation.viewmodels.PantryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryScreen(
    onAddItem: () -> Unit,
    onEditItem: (String) -> Unit,
    viewModel: PantryViewModel = viewModel()
) {
    val pantryItems by viewModel.pantryItems.collectAsState()
    val lowStockItems by viewModel.lowStockItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var searchQuery by remember { mutableStateOf("") }
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchItems(searchQuery)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Pantry") },
                actions = {
                    IconButton(onClick = { /* TODO: Open search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Low stock warning banner
            if (lowStockItems.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${lowStockItems.size} items low on stock",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search pantry items...") },
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Pantry items list
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (pantryItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Your pantry is empty",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Add some ingredients to get started",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pantryItems) { item ->
                        PantryItemCard(
                            item = item,
                            onEdit = { onEditItem(item.id) },
                            onAdjustQuantity = { adjustment ->
                                // TODO: Implement quantity adjustment
                            }
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
fun PantryItemCard(
    item: com.pantrybox.data.entities.PantryItem,
    onEdit: () -> Unit,
    onAdjustQuantity: (Double) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onEdit
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
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${item.quantity} ${item.unit}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                
                if (item.expiryDate != null) {
                    Text(
                        text = "Expires: ${item.expiryDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            // Low stock warning
            if (item.quantity <= item.minThreshold && item.minThreshold > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⚠️ Low stock (min: ${item.minThreshold} ${item.unit})",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}