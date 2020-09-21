package ru.mikov.sbdelivery.viewmodels.auth.recovery

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class EnterCodeViewModel(handle: SavedStateHandle) : BaseViewModel<EnterCodeState>(handle, EnterCodeState()) {
    private val repository = RootRepository

    fun sendCode(email: String, code: String) {
        launchSafety {
            showLoadingBlock()
            repository.sendRecoveryCode(email, code)
//            val isValid:Boolean = repository.sendRecoveryCode(email, code)
//            updateState { it.copy(isCodeValid = isValid) }
        }
    }

    fun handleFirst(first: String) {
        updateState { it.copy(first = first) }
    }

    fun handleSecond(second: String) {
        updateState { it.copy(second = second) }
    }

    fun handleThird(third: String) {
        updateState { it.copy(third = third) }
    }

    fun handleFourth(fourth: String) {
        updateState { it.copy(fourth = fourth) }
    }

    fun handleCode() {
        updateState { it.copy(code = currentState.first + currentState.second + currentState.third + currentState.fourth) }
    }
}

data class EnterCodeState(
    val first: String = "",
    val second: String = "",
    val third: String = "",
    val fourth: String = "",
    val code: String = "",
    val isCodeValid: Boolean = false
) : IViewModelState