package ru.mikov.sbdelivery.viewmodels.auth.recovery

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class EnterNewPasswordViewModel(handle: SavedStateHandle) : BaseViewModel<EnterNewPasswordState>(handle, EnterNewPasswordState()) {
}

data class EnterNewPasswordState(
    val password: String = "",
    val passwordRepeat: String = ""
) : IViewModelState