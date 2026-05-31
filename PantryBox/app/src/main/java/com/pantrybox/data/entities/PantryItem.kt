package com.pantrybox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "pantry_items")
data class PantryItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Double,
    val unit: String, // e.g., "kg", "g", "ml", "L", "pcs"
    val category: String, // e.g., "Vegetables", "Dairy", "Meat", "Spices"
    val expiryDate: Date? = null,
    val minThreshold: Double = 0.0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)