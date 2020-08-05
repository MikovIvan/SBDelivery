package ru.mikov.sbdelivery.data.remote.res

import ru.mikov.sbdelivery.data.local.entities.Dish
import java.util.*

data class DishRes(
    val id: String,
    val name: String,
    val description: String? = null,
    val image: String,
    val oldPrice: Int?,
    val price: Int,
    val rating: Double,
    val commentsCount: Int,
    val likes: Int,
    val category: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDish(): Dish {
        with(this) {
            return Dish(
                id,
                name,
                description,
                image,
                oldPrice,
                price,
                rating,
                commentsCount,
                likes,
                category,
                active,
                Date(createdAt),
                Date(updatedAt)
            )
        }
    }
}