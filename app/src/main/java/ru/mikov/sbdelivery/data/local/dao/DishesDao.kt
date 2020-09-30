package ru.mikov.sbdelivery.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
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
    fun getDishesByCategory(categoryId: String): DataSource.Factory<Int, Dish>

    @Query(
        """
            SELECT * FROM dishes
            WHERE old_price IS NOT null
        """
    )
    fun getAllPromoDishes(): DataSource.Factory<Int, Dish>

    @Query(
        """
            SELECT * FROM category
            WHERE parent = :categoryId
        """
    )
    fun getDishesSubcategory(categoryId: String): LiveData<List<Category>>

    @RawQuery(observedEntities = [Dish::class])
    fun findDishesByRaw(simpleSQLiteQuery: SimpleSQLiteQuery): DataSource.Factory<Int, Dish>
}