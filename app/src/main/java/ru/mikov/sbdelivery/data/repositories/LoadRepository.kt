package ru.mikov.sbdelivery.data.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.mikov.sbdelivery.data.local.DbManager.db
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.data.remote.NetworkService
import ru.mikov.sbdelivery.data.remote.res.CategoryRes
import ru.mikov.sbdelivery.data.remote.res.DishRes
import java.util.*

object LoadRepository {

    private var categoriesDao = db.categoriesDao()
    private var dishesDao = db.dishesDao()

    suspend fun isNeedUpdate(): Boolean {
        return categoriesDao.getAllCategories().isEmpty()
    }

    suspend fun sync() {
        getAllCategories { categoryList ->
            categoryList.forEach {
                categoriesDao.insert(it.toCategory())
            }
        }
        getAllDishes { dishList ->
            dishList.forEach {
                dishesDao.insert(it.toDish())
            }
            if (dishesDao.isActionDish()) categoriesDao.insert(
                Category(
                    "1", "Акции", 0, null, null, true, Date(),
                    Date()
                )
            )
        }

    }

    fun getAllDishes(result: (dishes: List<DishRes>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val resList: MutableList<DishRes> = mutableListOf()
            var offset = 0
            val limit = 50
            do {
                val tmpList = NetworkService.api.getAllDishes(offset, limit)
                if (tmpList.size < limit) break
                resList.addAll(tmpList)
                offset += limit
            } while (true)

            result(resList)
        }
    }

    fun getPopular(): LiveData<List<Dish>> {
        return dishesDao.getPopularDishes()
    }

    fun getAllCategories(result: (categories: List<CategoryRes>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val resList: MutableList<CategoryRes> = mutableListOf()
            var offset = 0
            val limit = 10
            do {
                val tmpList = NetworkService.api.getAllCategories(offset, limit)
                if (tmpList.size < limit) break
                resList.addAll(tmpList)
                offset += limit
            } while (true)

            result(resList)
        }
    }

    fun getCategories(): LiveData<List<Category>> {
        return categoriesDao.getCategories()
    }
}