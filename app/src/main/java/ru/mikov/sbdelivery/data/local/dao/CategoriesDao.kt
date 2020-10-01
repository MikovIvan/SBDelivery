package ru.mikov.sbdelivery.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.mikov.sbdelivery.data.local.entities.Category

@Dao
interface CategoriesDao : BaseDao<Category> {

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>

    @Query(
        """
        SELECT * FROM category
        WHERE parent IS null
        ORDER BY `order` ASC
    """
    )
    fun getCategories(): LiveData<List<Category>>

    @Query(
        """
            SELECT * FROM category
            WHERE parent = :categoryId
        """
    )
    fun getDishesSubcategory(categoryId: String): List<Category>
}