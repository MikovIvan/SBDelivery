package ru.mikov.sbdelivery.viewmodels.auth

import androidx.lifecycle.SavedStateHandle
import ru.mikov.sbdelivery.data.repositories.RootRepository
import ru.mikov.sbdelivery.extensions.hasDigits
import ru.mikov.sbdelivery.extensions.isEmailValid
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand

class RegistrationViewModel(handle: SavedStateHandle) :
    BaseViewModel<RegistrationState>(handle, RegistrationState()) {
    private val repository = RootRepository

    init {
        subscribeOnDataSource(repository.isAuth()) { isAuth, state ->
            state.copy(isAuth = isAuth)
        }
    }

    fun handleRegistration(
        name: String,
        surname: String,
        login: String,
        password: String,
        dest: Int?
    ) {
        launchSafety {
            repository.register(name, surname, login, password)
            navigate(NavigationCommand.FinishLogin(dest))
        }
    }

    fun handleName(name: String) {
        updateState { it.copy(name = name, nameError = false) }
    }

    fun handleSurname(surname: String) {
        updateState { it.copy(surname = surname, surnameError = false) }
    }

    fun handleLogin(login: String) {
        updateState { it.copy(login = login, loginError = false) }
    }

    fun handlePassword(password: String) {
        updateState { it.copy(password = password, passwordError = false) }
    }

    fun handleBtn() {
        updateState {
            it.copy(
                isRegBtnEnable = currentState.name.isNotBlank() && currentState.surname.isNotBlank()
                        && currentState.login.isNotBlank() && currentState.password.isNotBlank()
            )
        }
    }

    fun checkValidation(name: String, surname: String, login: String, password: String): Boolean {
        updateState {
            it.copy(
                isNameValid = !name.hasDigits(),
                isSurnameValid = !surname.hasDigits(),
                isLoginValid = login.isEmailValid(),
                isPasswordValid = !password.isEmpty()
            )
        }
        return (currentState.isNameValid && currentState.isSurnameValid && currentState.isLoginValid && currentState.isPasswordValid)
    }

    fun checkFields() {
        updateState {
            it.copy(
                nameError = currentState.name.isEmpty(),
                surnameError = currentState.surname.isEmpty(),
                loginError = currentState.login.isEmpty(),
                passwordError = currentState.password.isEmpty()
            )
        }
    }


}

data class RegistrationState(
    val name: String = "",
    val surname: String = "",
    val login: String = "",
    val password: String = "",
    val nameError: Boolean = false,
    val surnameError: Boolean = false,
    val loginError: Boolean = false,
    val passwordError: Boolean = false,
    val isNameValid: Boolean = true,
    val isSurnameValid: Boolean = true,
    val isLoginValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isRegBtnEnable: Boolean = false,
    val isAuth: Boolean = false

) : IViewModelState {
    override fun save(outState: SavedStateHandle) {
        outState.set("name", name)
        outState.set("surname", surname)
        outState.set("login", login)
        outState.set("password", password)
    }

    override fun restore(savedState: SavedStateHandle): IViewModelState {
        return copy(
            name = savedState["name"] ?: "",
            surname = savedState["surname"] ?: "",
            login = savedState["login"] ?: "",
            password = savedState["password"] ?: ""
        )
    }
}