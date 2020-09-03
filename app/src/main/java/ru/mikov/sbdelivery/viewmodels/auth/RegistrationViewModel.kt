package ru.mikov.sbdelivery.viewmodels.auth

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class RegistrationViewModel(handle: SavedStateHandle) :
    BaseViewModel<RegistrationState>(handle, RegistrationState()) {
    private val repository = RootRepository


}

data class RegistrationState(
    val name: String = "",
    val surname: String = "",
    val login: String = "",
    val password: String = ""
) : IViewModelState