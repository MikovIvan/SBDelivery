package ru.mikov.sbdelivery.viewmodels.dishes.dish

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.data.repositories.DishRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class DishViewModel(
    handle: SavedStateHandle,
    categoryId: String
) : BaseViewModel<DishState>(handle, DishState()) {
    private val rep = DishRepository

    init {
        subscribeOnDataSource(rep.getDishesByCategory(categoryId)) { dishes, state ->
            state.copy(dishes = dishes)
        }
    }

    fun applySort(sort: Int) {
        updateState { it.copy(selectedSort = sort) }
    }

}

data class DishState(
    val dishes: List<Dish> = emptyList(),
    val selectedSort: Int = -1
) : IViewModelState