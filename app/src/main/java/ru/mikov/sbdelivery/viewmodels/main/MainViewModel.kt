package ru.mikov.sbdelivery.viewmodels.main

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class MainViewModel(handle: SavedStateHandle) : BaseViewModel<MainState>(handle, MainState()) {
    // TODO: Implement the ViewModel
}

data class MainState(
    val isSearch: Boolean = false
) : IViewModelState
