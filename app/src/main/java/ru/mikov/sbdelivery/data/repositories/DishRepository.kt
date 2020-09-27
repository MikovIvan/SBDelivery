package ru.mikov.sbdelivery.data.repositories

import androidx.lifecycle.LiveData
import ru.mikov.sbdelivery.data.local.DbManager
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish

object DishRepository {
    private var dishesDao = DbManager.db.dishesDao()

    fun getDishesByCategory(categoryId: String): LiveData<List<Dish>> {
        return dishesDao.getDishesByCategory(categoryId)
    }

    fun getDishesSubcategory(categoryId: String): LiveData<List<Category>> {
        return dishesDao.getDishesSubcategory(categoryId)
    }
}