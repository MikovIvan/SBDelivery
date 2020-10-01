package ru.mikov.sbdelivery.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import ru.mikov.sbdelivery.data.local.DbManager
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish

object DishRepository {
    private var dishesDao = DbManager.db.dishesDao()

    fun getDishesSubcategory(categoryId: String): LiveData<List<Category>> {
        return dishesDao.getDishesSubcategory(categoryId)
    }

    fun rawQueryDishes(categoryId: String, filter: DishesSort): DataSource.Factory<Int, Dish> {
        return when (categoryId) {
            "1" -> dishesDao.findDishesByRaw(SimpleSQLiteQuery(filter.toQuery("1")))
            else -> dishesDao.findDishesByRaw(SimpleSQLiteQuery(filter.toQuery(categoryId)))
        }
    }
}

class DishesSort(
    val sort: String
) {
    fun toQuery(id: String): String {
        val qb = QueryBuilder()
        qb.table("dishes")
        if (id == "1") qb.appendWhere("old_price IS NOT null") else qb.appendWhere("category = '$id'")
        when (sort) {
            "A to Z" -> qb.orderBy("name", false)
            "Z to A" -> qb.orderBy("name", true)
            "More popular" -> qb.orderBy("likes", false)
            "Less popular" -> qb.orderBy("likes", true)
            "Higher rating" -> qb.orderBy("rating", false)
            "Lower rating" -> qb.orderBy("rating", true)
        }

        return qb.build()
    }
}

class QueryBuilder() {
    private var table: String? = null
    private var selectColumns: String = "*"
    private var joinTables: String? = null
    private var whereCondition: String? = null
    private var order: String? = null

    fun build(): String {
        check(table != null) { "table must be not null" }
        val strBuilder = StringBuilder("SELECT ")
            .append("$selectColumns ")
            .append("FROM $table ")

        if (joinTables != null) strBuilder.append(joinTables)
        if (whereCondition != null) strBuilder.append(whereCondition)
        if (order != null) strBuilder.append(order)
        return strBuilder.toString()
    }

    fun table(table: String): QueryBuilder {
        this.table = table
        return this
    }

    fun orderBy(column: String, isDesc: Boolean = true): QueryBuilder {
        order = "ORDER BY $column ${if (isDesc) "DESC" else "ASC"}"
        return this
    }

    fun appendWhere(condition: String, logic: String = "AND"): QueryBuilder {
        if (whereCondition.isNullOrEmpty()) whereCondition = "WHERE $condition "
        else whereCondition += "$logic $condition "
        return this
    }
}