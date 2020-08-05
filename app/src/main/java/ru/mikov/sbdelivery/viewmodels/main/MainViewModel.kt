package ru.mikov.sbdelivery.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.data.repositories.LoadRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class MainViewModel(handle: SavedStateHandle) : BaseViewModel<MainState>(handle, MainState()) {
    private val rep = LoadRepository

    val popularDishes: LiveData<List<Dish>> = rep.getPopular()

    init {
        subscribeOnDataSource(popularDishes) { dishes, state ->
            popularDishes
            state.copy(popularDishes = dishes)
        }
    }

}

data class MainState(
    val isSearch: Boolean = false,
    val popularDishes: List<Dish> = emptyList()
) : IViewModelState
