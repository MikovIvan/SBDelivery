package ru.mikov.sbdelivery.viewmodels.auth.recovery

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class RecoveryPasswordViewModel(handle: SavedStateHandle) : BaseViewModel<RecoveryPasswordState>(handle, RecoveryPasswordState()) {
    private val repository = RootRepository

    fun handleEmail(email: String) {
        updateState { it.copy(email = email) }
    }

    fun handleBtn() {
        updateState { it.copy(isSendBtnEnable = currentState.email.isNotBlank()) }
    }

    fun sendEmail(email: String) {
        launchSafety {
            showLoadingBlock()
            repository.sendRecoveryEmail(email)
        }
    }
}

data class RecoveryPasswordState(
    val email: String = "",
    val isSendBtnEnable: Boolean = false
) : IViewModelState