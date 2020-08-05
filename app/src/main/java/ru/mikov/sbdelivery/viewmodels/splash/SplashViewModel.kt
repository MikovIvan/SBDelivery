package ru.mikov.sbdelivery.viewmodels.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mikov.sbdelivery.App
import ru.mikov.sbdelivery.data.repositories.LoadRepository
import ru.mikov.sbdelivery.extensions.isNetworkAvailable
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class SplashViewModel(handle: SavedStateHandle) :
    BaseViewModel<SplashState>(handle, SplashState()) {

    private val repository = LoadRepository

    fun syncDataIfNeed(): LiveData<LoadResult<Boolean>> {
        val result: MutableLiveData<LoadResult<Boolean>> =
            MutableLiveData(LoadResult.Loading(false))

        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isNeedUpdate()) {
                if (!App.applicationContext().isNetworkAvailable) {
                    result.postValue(
                        LoadResult.Error(
                            "Интернет не доступен! Приложение может работать не корректно!",
                            true
                        )
                    )
                    return@launch
                }
                repository.sync()
                result.postValue(LoadResult.Success(true))
            } else {
                delay(3000)
                result.postValue(LoadResult.Success(true))
            }
        }
        return result
    }


}

sealed class LoadResult<T>(
    val data: T?,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : LoadResult<T>(data)
    class Loading<T>(data: T? = null) : LoadResult<T>(data)
    class Error<T>(message: String, data: T? = null) : LoadResult<T>(data, message)

}

data class SplashState(val isSearch: Boolean = false) : IViewModelState
