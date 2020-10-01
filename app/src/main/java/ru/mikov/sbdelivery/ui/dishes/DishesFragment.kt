package ru.mikov.sbdelivery.ui.dishes

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_dish.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseActivity.MenuItemHolder
import ru.mikov.sbdelivery.ui.base.BaseActivity.ToolbarBuilder
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.dialogs.SortCategoryDialog
import ru.mikov.sbdelivery.ui.dishes.dish.DishesFragmentArgs
import ru.mikov.sbdelivery.ui.dishes.dish.DishesFragmentDirections
import ru.mikov.sbdelivery.viewmodels.SharedViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.base.ViewModelFactory
import ru.mikov.sbdelivery.viewmodels.dishes.DishesState
import ru.mikov.sbdelivery.viewmodels.dishes.DishesViewModel

class DishesFragment : BaseFragment<DishesViewModel>() {
    private val args: DishesFragmentArgs by navArgs()

    override val viewModel: DishesViewModel by viewModels {
        ViewModelFactory(
            owner = this,
            params = arguments?.getString(CATEGORY_ID) ?: args.categoryId
        )
    }
    override val layout: Int = R.layout.fragment_dish
    override val binding: DishesBinding by lazy { DishesBinding() }

    private val sharedModel: SharedViewModel by activityViewModels()
    private val dishesAdapter = DishesAdapter { item, isToggleLike ->
        if (isToggleLike) {
            //todo handleToggleLike
        } else {
            //todo add navigation to dishscreen
        }
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        addMenuItem(
            MenuItemHolder(
                "Filter",
                R.id.action_sort,
                R.drawable.ic_baseline_sort_24,
                null
            ) {
                val action =
                    DishesFragmentDirections.actionPageDishToSortCategoryDialog(
                        binding.selectedSort
                    )
                viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(SortCategoryDialog.CHOOSE_SELECTED_SORT) { _, bundle ->
            @Suppress("UNCHECKED_CAST")
            viewModel.applySort(bundle[SortCategoryDialog.SELECTED_SORT] as String)
            sharedModel.select(bundle[SortCategoryDialog.SELECTED_SORT] as String)
        }

        if (arguments?.getString(CATEGORY_ID).isNullOrEmpty()) setHasOptionsMenu(true)
    }

    override fun setupViews() {
        if (arguments?.getString(CATEGORY_ID).isNullOrEmpty()) toolbar.title = args.name

        with(rv_dishes) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = dishesAdapter
        }

        viewModel.observeList(viewLifecycleOwner, arguments?.getBoolean("isLike") ?: false) {
            dishesAdapter.submitList(it)
        }

        sharedModel.selectedSort.observe(viewLifecycleOwner, Observer {
            viewModel.applySort(it)
        })
    }

    companion object {
        private const val CATEGORY_ID = "CATEGORY_ID"

        @JvmStatic
        fun newInstance(categoryId: String): DishesFragment {
            return DishesFragment().apply {
                arguments = bundleOf(CATEGORY_ID to categoryId)
            }
        }
    }

    inner class DishesBinding : Binding() {
        var selectedSort: String = ""

        override fun bind(data: IViewModelState) {
            data as DishesState
            selectedSort = data.selectedSort
        }
    }
}