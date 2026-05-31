package com.pantrybox.data.repositories

import com.pantrybox.data.dao.PantryItemDao
import com.pantrybox.data.entities.PantryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PantryRepository @Inject constructor(
    private val pantryItemDao: PantryItemDao
) {
    fun getAllItems(): Flow<List<PantryItem>> = pantryItemDao.getAllItems()

    suspend fun getItemById(id: String): PantryItem? = pantryItemDao.getItemById(id)

    suspend fun insertItem(item: PantryItem) = pantryItemDao.insert(item)

    suspend fun updateItem(item: PantryItem) = pantryItemDao.update(item)

    suspend fun deleteItem(item: PantryItem) = pantryItemDao.delete(item)

    fun getLowStockItems(): Flow<List<PantryItem>> = pantryItemDao.getLowStockItems()

    fun searchItems(query: String): Flow<List<PantryItem>> = pantryItemDao.searchItems(query)

    fun getAllCategories(): Flow<List<String>> = pantryItemDao.getAllCategories()

    suspend fun adjustQuantity(itemId: String, adjustment: Double): Boolean {
        val item = pantryItemDao.getItemById(itemId) ?: return false
        val newQuantity = maxOf(0.0, item.quantity + adjustment)
        val updatedItem = item.copy(quantity = newQuantity)
        pantryItemDao.update(updatedItem)
        return true
    }

    suspend fun deductIngredient(name: String, quantity: Double, unit: String): Boolean {
        // Find matching items (case-insensitive, partial match)
        val allItems = pantryItemDao.getAllItems().first()
        val matchingItem = allItems.find { 
            it.name.equals(name, ignoreCase = true) && 
            it.unit.equals(unit, ignoreCase = true) 
        } ?: return false

        if (matchingItem.quantity < quantity) return false
        
        return adjustQuantity(matchingItem.id, -quantity)
    }
}

// Extension function to get first value from Flow
suspend fun <T> Flow<T>.first(): T {
    var value: T? = null
    collect { value = it }
    return value!!
}