package com.pantrybox.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pantrybox.data.repositories.RecipeRepository
import com.pantrybox.data.repositories.RecipeWithMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _allRecipes = MutableStateFlow<List<com.pantrybox.data.entities.Recipe>>(emptyList())
    val allRecipes: StateFlow<List<com.pantrybox.data.entities.Recipe>> = _allRecipes.asStateFlow()
    
    private val _matchingRecipes = MutableStateFlow<List<RecipeWithMatch>>(emptyList())
    val matchingRecipes: StateFlow<List<RecipeWithMatch>> = _matchingRecipes.asStateFlow()
    
    private val _favoriteRecipes = MutableStateFlow<List<com.pantrybox.data.entities.Recipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<com.pantrybox.data.entities.Recipe>> = _favoriteRecipes.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _selectedRecipe = MutableStateFlow<com.pantrybox.data.repositories.RecipeWithDetails?>(null)
    val selectedRecipe: StateFlow<com.pantrybox.data.repositories.RecipeWithDetails?> = _selectedRecipe.asStateFlow()
    
    init {
        loadAllRecipes()
        loadMatchingRecipes()
        loadFavoriteRecipes()
    }
    
    private fun loadAllRecipes() {
        viewModelScope.launch {
            repository.getAllRecipes().collectLatest { recipes ->
                _allRecipes.value = recipes
            }
        }
    }
    
    private fun loadMatchingRecipes() {
        viewModelScope.launch {
            repository.getMatchingRecipes().collectLatest { recipes ->
                _matchingRecipes.value = recipes
            }
        }
    }
    
    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            repository.getFavoriteRecipes().collectLatest { recipes ->
                _favoriteRecipes.value = recipes
            }
        }
    }
    
    fun searchRecipes(query: String) {
        viewModelScope.launch {
            repository.searchRecipes(query).collectLatest { recipes ->
                _allRecipes.value = recipes
            }
        }
    }
    
    fun loadRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val recipeDetails = repository.getRecipeById(recipeId)
                _selectedRecipe.value = recipeDetails
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load recipe details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    suspend fun cookRecipe(recipeId: String): Boolean {
        return try {
            val success = repository.cookRecipe(recipeId)
            if (success) {
                // Refresh matching recipes after cooking
                loadMatchingRecipes()
            }
            success
        } catch (e: Exception) {
            _errorMessage.value = "Failed to cook recipe: ${e.message}"
            false
        }
    }
    
    fun clearSelectedRecipe() {
        _selectedRecipe.value = null
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}