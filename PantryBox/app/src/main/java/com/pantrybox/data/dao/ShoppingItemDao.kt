package com.pantrybox.data.dao

import androidx.room.*
import com.pantrybox.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shopping_items ORDER BY isChecked ASC, createdAt DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ShoppingItem>)

    @Update
    suspend fun update(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("UPDATE shopping_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateCheckedStatus(id: String, isChecked: Boolean)

    @Query("DELETE FROM shopping_items WHERE isChecked = 1")
    suspend fun deleteCheckedItems()

    @Query("DELETE FROM shopping_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM shopping_items WHERE isChecked = 0")
    fun getPendingCount(): Flow<Int>
}