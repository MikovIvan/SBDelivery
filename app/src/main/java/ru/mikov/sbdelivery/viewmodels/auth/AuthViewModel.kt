package ru.mikov.sbdelivery.viewmodels.auth

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class AuthViewModel(handle: SavedStateHandle) : BaseViewModel<AuthState>(handle, AuthState()) {
    private val repository = RootRepository

    init {
        subscribeOnDataSource(repository.isAuth()) { isAuth, state ->
            state.copy(isAuth = isAuth)
        }
    }

//    fun handleLogin(login: String, pass: String, dest: Int?) {
//        launchSafety {
//            repository.login(login, pass)
//            navigate(NavigationCommand.FinishLogin(dest))
//        }
//    }
}

data class AuthState(val isAuth: Boolean = false) : IViewModelState