package ru.mikov.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand

class RootViewModel(handle: SavedStateHandle) : BaseViewModel<RootState>(handle, RootState()) {
    private val repository: RootRepository = RootRepository
    private val privateRoutes = listOf(R.id.nav_profile)

    init {
        subscribeOnDataSource(repository.isAuth()) { isAuth, state ->
            state.copy(isAuth = isAuth)
        }

        subscribeOnDataSource(repository.getProfile()) { profile, state ->
            profile ?: return@subscribeOnDataSource null
            state.copy(
                name = profile.firstName,
                email = profile.email
            )

        }
    }

    override fun navigate(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> {
                if (privateRoutes.contains(command.destination) && !currentState.isAuth) {
                    //set requested destination as arg
                    super.navigate(NavigationCommand.StartLogin(command.destination))
                } else {
                    super.navigate(command)
                }
            }
            else -> super.navigate(command)
        }
    }

    fun logout() {
        repository.logout()
    }
}

data class RootState(
    val isAuth: Boolean = false,
    val name: String = "",
    val email: String = ""
) : IViewModelState