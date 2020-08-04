package ru.mikov.sbdelivery.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
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
}