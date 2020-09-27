package ru.mikov.sbdelivery.viewmodels.base

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.mikov.sbdelivery.viewmodels.dishes.DishesCategoryViewModel
import ru.mikov.sbdelivery.viewmodels.dishes.dish.DishViewModel


class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle = bundleOf(),
    private val params: Any
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            return DishViewModel(
                handle,
                params as String
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DishCategoryViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle = bundleOf(),
    private val params: Any
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DishesCategoryViewModel::class.java)) {
            return DishesCategoryViewModel(
                handle,
                params as String
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}