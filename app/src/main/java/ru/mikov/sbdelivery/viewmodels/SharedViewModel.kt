package ru.mikov.sbdelivery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selectedSort = MutableLiveData<String>()

    fun select(s: String) {
        selectedSort.value = s
    }
}