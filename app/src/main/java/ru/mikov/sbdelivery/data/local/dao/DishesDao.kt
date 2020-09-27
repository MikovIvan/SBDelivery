package ru.mikov.sbdelivery.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish

@Dao
interface DishesDao : BaseDao<Dish> {

    @Query(
        """
        SELECT * FROM dishes
        ORDER BY rating DESC LIMIT 10
    """
    )
    fun getPopularDishes(): LiveData<List<Dish>>

    @Query(
        """
            SELECT * FROM dishes
            WHERE old_price IS NOT null
        """
    )
    fun isActionDish(): Boolean

    @Query(
        """
            SELECT * FROM dishes
            WHERE category = :categoryId
        """
    )
    fun getDishesByCategory(categoryId: String): LiveData<List<Dish>>

    @Query(
        """
            SELECT * FROM category
            WHERE parent = :categoryId
        """
    )
    fun getDishesSubcategory(categoryId: String): LiveData<List<Category>>
}