package ru.mikov.sbdelivery.viewmodels.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.repositories.LoadRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class MenuViewModel(handle: SavedStateHandle) : BaseViewModel<MenuState>(handle, MenuState()) {
    private val rep = LoadRepository

    val categories: LiveData<List<Category>> = rep.getCategories()

    init {
        subscribeOnDataSource(categories) { categories1, state ->
            categories
            state.copy(categories = categories1)
        }
    }
}

data class MenuState(
    val isSearch: Boolean = false,
    val categories: List<Category> = emptyList()
) : IViewModelState