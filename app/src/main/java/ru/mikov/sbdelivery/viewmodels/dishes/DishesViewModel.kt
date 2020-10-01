package ru.mikov.sbdelivery.viewmodels.dishes

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.data.repositories.DishRepository
import ru.mikov.sbdelivery.data.repositories.DishesSort
import ru.mikov.sbdelivery.viewmodels.base.BaseViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import java.util.concurrent.Executors


class DishesViewModel(
    handle: SavedStateHandle,
    private val categoryId: String
) : BaseViewModel<DishesState>(
    handle,
    DishesState()
) {
    private val rep = DishRepository
    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(30)
            .build()
    }

    private val listData = Transformations.switchMap(state) {
        val filter = it.toDishSort()
        return@switchMap buildPagedList(rep.rawQueryDishes(categoryId, filter))
    }

    fun observeList(
        owner: LifecycleOwner,
        isLike: Boolean = false,
        onChange: (list: PagedList<Dish>) -> Unit
    ) {
        updateState { it.copy(isLike = isLike) }
        listData.observe(owner, Observer { onChange(it) })
    }

    private fun buildPagedList(
        dataFactory: DataSource.Factory<Int, Dish>
    ): LiveData<PagedList<Dish>> {
        val builder = LivePagedListBuilder<Int, Dish>(
            dataFactory,
            listConfig
        )
        return builder
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    fun applySort(sort: String) {
        updateState { it.copy(selectedSort = sort) }
    }

}

private fun DishesState.toDishSort(): DishesSort =
    DishesSort(
        sort = selectedSort
    )

data class DishesState(
    val isLike: Boolean = false,
    val selectedSort: String = "",
    val dishes: List<Category> = emptyList()
) : IViewModelState

