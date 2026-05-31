package com.pantrybox.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pantrybox.data.entities.PantryItem
import com.pantrybox.data.repositories.PantryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PantryViewModel @Inject constructor(
    private val repository: PantryRepository
) : ViewModel() {
    
    private val _pantryItems = MutableStateFlow<List<PantryItem>>(emptyList())
    val pantryItems: StateFlow<List<PantryItem>> = _pantryItems.asStateFlow()
    
    private val _lowStockItems = MutableStateFlow<List<PantryItem>>(emptyList())
    val lowStockItems: StateFlow<List<PantryItem>> = _lowStockItems.asStateFlow()
    
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadPantryItems()
        loadLowStockItems()
        loadCategories()
    }
    
    private fun loadPantryItems() {
        viewModelScope.launch {
            repository.getAllItems().collectLatest { items ->
                _pantryItems.value = items
            }
        }
    }
    
    private fun loadLowStockItems() {
        viewModelScope.launch {
            repository.getLowStockItems().collectLatest { items ->
                _lowStockItems.value = items
            }
        }
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            repository.getAllCategories().collectLatest { categories ->
                _categories.value = categories
            }
        }
    }
    
    fun searchItems(query: String) {
        viewModelScope.launch {
            repository.searchItems(query).collectLatest { items ->
                _pantryItems.value = items
            }
        }
    }
    
    suspend fun addItem(item: PantryItem): Boolean {
        return try {
            repository.insertItem(item)
            true
        } catch (e: Exception) {
            _errorMessage.value = "Failed to add item: ${e.message}"
            false
        }
    }
    
    suspend fun updateItem(item: PantryItem): Boolean {
        return try {
            repository.updateItem(item)
            true
        } catch (e: Exception) {
            _errorMessage.value = "Failed to update item: ${e.message}"
            false
        }
    }
    
    suspend fun deleteItem(item: PantryItem): Boolean {
        return try {
            repository.deleteItem(item)
            true
        } catch (e: Exception) {
            _errorMessage.value = "Failed to delete item: ${e.message}"
            false
        }
    }
    
    suspend fun adjustQuantity(itemId: String, adjustment: Double): Boolean {
        return try {
            repository.adjustQuantity(itemId, adjustment)
        } catch (e: Exception) {
            _errorMessage.value = "Failed to adjust quantity: ${e.message}"
            false
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}