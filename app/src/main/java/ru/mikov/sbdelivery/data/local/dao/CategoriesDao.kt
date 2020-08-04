package ru.mikov.sbdelivery.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import ru.mikov.sbdelivery.data.local.entities.Category

@Dao
interface CategoriesDao : BaseDao<Category> {

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>
}