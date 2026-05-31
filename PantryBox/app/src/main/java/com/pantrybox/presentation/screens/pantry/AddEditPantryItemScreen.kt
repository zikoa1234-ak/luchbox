package com.pantrybox.presentation.screens.pantry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pantrybox.data.entities.PantryItem
import com.pantrybox.presentation.viewmodels.PantryViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPantryItemScreen(
    itemId: String?,
    onBack: () -> Unit,
    viewModel: PantryViewModel = viewModel()
) {
    val existingItem = remember(itemId) {
        if (itemId != null) {
            // TODO: Load existing item
            null
        } else {
            null
        }
    }
    
    var name by remember { mutableStateOf(existingItem?.name ?: "") }
    var quantity by remember { mutableStateOf(existingItem?.quantity?.toString() ?: "1") }
    var unit by remember { mutableStateOf(existingItem?.unit ?: "pcs") }
    var category by remember { mutableStateOf(existingItem?.category ?: "Other") }
    var minThreshold by remember { mutableStateOf(existingItem?.minThreshold?.toString() ?: "0") }
    
    val units = listOf("pcs", "kg", "g", "L", "ml", "oz", "lb", "cup", "tbsp", "tsp")
    val categories = listOf(
        "Vegetables", "Fruits", "Dairy", "Meat", "Fish", "Grains", 
        "Spices", "Condiments", "Beverages", "Snacks", "Other"
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (existingItem != null) "Edit Item" else "Add Item") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (existingItem != null) {
                        IconButton(
                            onClick = {
                                // TODO: Delete item
                                onBack()
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                    IconButton(
                        onClick = {
                            // TODO: Save item
                            onBack()
                        }
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Item Name") },
                placeholder = { Text("e.g., Tomatoes, Rice, Milk") },
                singleLine = true
            )
            
            // Quantity and unit
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { newValue ->
                        if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                            quantity = newValue
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Quantity") },
                    singleLine = true
                )
                
                OutlinedTextField(
                    value = unit,
                    onValueChange = { unit = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Unit") },
                    readOnly = true,
                    onClick = {
                        // TODO: Show unit picker
                    },
                    singleLine = true
                )
            }
            
            // Category
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Category") },
                readOnly = true,
                onClick = {
                    // TODO: Show category picker
                },
                singleLine = true
            )
            
            // Minimum threshold
            OutlinedTextField(
                value = minThreshold,
                onValueChange = { newValue ->
                    if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                        minThreshold = newValue
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Minimum Threshold") },
                placeholder = { Text("Alert when below this quantity") },
                singleLine = true,
                supportingText = {
                    Text("Set to 0 to disable low stock alerts")
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Quick add common items
            Text(
                text = "Quick Add Common Items",
                style = MaterialTheme.typography.titleSmall
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickAddButton(
                    text = "Tomatoes",
                    onClick = {
                        name = "Tomatoes"
                        unit = "pcs"
                        category = "Vegetables"
                    }
                )
                QuickAddButton(
                    text = "Eggs",
                    onClick = {
                        name = "Eggs"
                        unit = "pcs"
                        category = "Dairy"
                    }
                )
                QuickAddButton(
                    text = "Rice",
                    onClick = {
                        name = "Rice"
                        unit = "kg"
                        category = "Grains"
                    }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickAddButton(
                    text = "Milk",
                    onClick = {
                        name = "Milk"
                        unit = "L"
                        category = "Dairy"
                    }
                )
                QuickAddButton(
                    text = "Onions",
                    onClick = {
                        name = "Onions"
                        unit = "pcs"
                        category = "Vegetables"
                    }
                )
                QuickAddButton(
                    text = "Chicken",
                    onClick = {
                        name = "Chicken"
                        unit = "kg"
                        category = "Meat"
                    }
                )
            }
        }
    }
}

@Composable
fun QuickAddButton(
    text: String,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier.weight(1f)
    ) {
        Text(text)
    }
}