package ru.mikov.sbdelivery.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.mikov.sbdelivery.App
import ru.mikov.sbdelivery.BuildConfig
import ru.mikov.sbdelivery.data.local.dao.CategoriesDao
import ru.mikov.sbdelivery.data.local.dao.DishesDao
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish

object DbManager {
    val db = Room.databaseBuilder(
        App.applicationContext(),
        AppDb::class.java,
        AppDb.DATABASE_NAME
    ).build()
}

@Database(
    entities = [Dish::class,
        Category::class],
    version = AppDb.DATABASE_VERSION,
    exportSchema = false,
    views = []
)

@TypeConverters(DateConverter::class)
abstract class AppDb : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID + ".db"
        const val DATABASE_VERSION = 1
    }

    abstract fun categoriesDao(): CategoriesDao
    abstract fun dishesDao(): DishesDao
}