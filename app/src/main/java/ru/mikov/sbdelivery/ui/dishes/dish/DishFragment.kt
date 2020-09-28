package ru.mikov.sbdelivery.ui.dishes.dish

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_dish.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.ui.base.BaseActivity.MenuItemHolder
import ru.mikov.sbdelivery.ui.base.BaseActivity.ToolbarBuilder
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.ui.main.DishesAdapter
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.ViewModelFactory
import ru.mikov.sbdelivery.viewmodels.dishes.dish.DishState
import ru.mikov.sbdelivery.viewmodels.dishes.dish.DishViewModel

class DishFragment : BaseFragment<DishViewModel>() {
    private val args: DishFragmentArgs by navArgs()

    override val viewModel: DishViewModel by viewModels {
        ViewModelFactory(
            owner = this,
            params = arguments?.getString(CATEGORY_ID) ?: args.categoryId
        )
    }
    override val layout: Int = R.layout.fragment_dish
    override val binding: DishesBinding by lazy { DishesBinding() }

    private val dishesAdapter = DishesAdapter()

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        addMenuItem(
            MenuItemHolder(
                "Filter",
                R.id.action_sort,
                R.drawable.ic_baseline_sort_24,
                null
            ) {
                //TODO nav to dialog
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setupViews() {
        if (arguments?.getString(CATEGORY_ID).isNullOrEmpty()) toolbar.title = args.name

        with(rv_dishes) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = dishesAdapter
        }
    }

    companion object {
        private const val CATEGORY_ID = "CATEGORY_ID"

        @JvmStatic
        fun newInstance(categoryId: String): DishFragment {
            return DishFragment().apply {
                arguments = bundleOf(CATEGORY_ID to categoryId)
            }
        }
    }

    inner class DishesBinding : Binding() {
        private var dishes: List<Dish> by RenderProp(emptyList<Dish>()) {
            dishesAdapter.submitList(it)
        }

        override fun bind(data: IViewModelState) {
            data as DishState
            dishes = data.dishes
        }
    }
}