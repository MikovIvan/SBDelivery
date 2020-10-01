package ru.mikov.sbdelivery.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: String,
    val name: String,
    val order: Int,
    val icon: String?,
    val parent: String?,
    val active: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date,
    @ColumnInfo(name = "is_parent")
    val isParent: Boolean
)