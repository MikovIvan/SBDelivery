package ru.mikov.sbdelivery.viewmodels.splash

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class SplashViewModel(handle: SavedStateHandle) :
    BaseViewModel<SplashState>(handle, SplashState()) {
    // TODO: Implement the ViewModel
}

data class SplashState(val isSearch: Boolean = false) : IViewModelState
