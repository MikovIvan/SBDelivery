package ru.mikov.sbdelivery.data.remote.res

import com.squareup.moshi.JsonClass
import ru.mikov.sbdelivery.data.local.entities.Category
import java.util.*

@JsonClass(generateAdapter = true)
data class CategoryRes(
    val categoryId: String,
    val name: String,
    val order: Int,
    val icon: String? = null,
    val parent: String? = null,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toCategory(): Category {
        with(this) {
            return Category(
                categoryId,
                name,
                order,
                icon,
                parent,
                active,
                Date(createdAt),
                Date(updatedAt),
                false
            )
        }
    }
}