package ru.mikov.sbdelivery.viewmodels.menu

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class MenuViewModel(handle: SavedStateHandle) : BaseViewModel<MenuState>(handle, MenuState()) {
    // TODO: Implement the ViewModel
}

data class MenuState(
    val isSearch: Boolean = false
) : IViewModelState