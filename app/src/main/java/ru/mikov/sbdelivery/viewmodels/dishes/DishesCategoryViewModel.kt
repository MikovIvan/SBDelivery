package ru.mikov.sbdelivery.viewmodels.dishes

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.repositories.DishRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class DishesCategoryViewModel(
    handle: SavedStateHandle,
    categoryId: String
) : BaseViewModel<DishesCategoryState>(handle, DishesCategoryState()) {
    private val rep = DishRepository

    init {
        subscribeOnDataSource(rep.getDishesSubcategory(categoryId)) { category, state ->
            state.copy(
                dishes = category
            )
        }
    }
}

data class DishesCategoryState(
    val dishes: List<Category> = emptyList()
) : IViewModelState