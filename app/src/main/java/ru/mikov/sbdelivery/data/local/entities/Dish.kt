package ru.mikov.sbdelivery.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    @ColumnInfo(name = "old_price")
    val oldPrice: Int?,
    val price: Int,
    val rating: Double,
    @ColumnInfo(name = "comments_count")
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val active: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date
)