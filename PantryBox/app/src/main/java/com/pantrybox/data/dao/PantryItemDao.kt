package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.PantryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryItemDao {
    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    fun getAllItems(): Flow<List<PantryItem>>

    @Query("SELECT * FROM pantry_items WHERE id = :id")
    suspend fun getItemById(id: String): PantryItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PantryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PantryItem>)

    @Update
    suspend fun update(item: PantryItem)

    @Delete
    suspend fun delete(item: PantryItem)

    @Query("DELETE FROM pantry_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM pantry_items WHERE quantity <= minThreshold AND minThreshold > 0")
    fun getLowStockItems(): Flow<List<PantryItem>>

    @Query("SELECT * FROM pantry_items WHERE LOWER(name) LIKE LOWER('%' || :searchQuery || '%')")
    fun searchItems(searchQuery: String): Flow<List<PantryItem>>

    @Query("SELECT DISTINCT category FROM pantry_items WHERE category IS NOT NULL AND category != ''")
    fun getAllCategories(): Flow<List<String>>
}