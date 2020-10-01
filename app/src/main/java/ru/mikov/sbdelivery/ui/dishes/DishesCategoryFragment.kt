package ru.mikov.sbdelivery.ui.dishes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_dishes_category.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.ui.base.BaseActivity
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.ui.dialogs.SortCategoryDialog
import ru.mikov.sbdelivery.viewmodels.SharedViewModel
import ru.mikov.sbdelivery.viewmodels.base.DishCategoryViewModelFactory
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.dishes.DishesCategoryState
import ru.mikov.sbdelivery.viewmodels.dishes.DishesCategoryViewModel


class DishesCategoryFragment : BaseFragment<DishesCategoryViewModel>() {

    override val viewModel: DishesCategoryViewModel by viewModels {
        DishCategoryViewModelFactory(
            owner = this,
            params = args.categoryId
        )
    }
    override val layout: Int = R.layout.fragment_dishes_category
    override val binding: DishesCategoryBinding by lazy { DishesCategoryBinding() }

    private val sharedModel: SharedViewModel by activityViewModels()
    private val args: DishesCategoryFragmentArgs by navArgs()
    private lateinit var dishesCategoryPagerAdapter: DishesCategoryPagerAdapter

    override val prepareToolbar: (BaseActivity.ToolbarBuilder.() -> Unit) = {
        addMenuItem(
            BaseActivity.MenuItemHolder(
                "Filter",
                R.id.action_sort,
                R.drawable.ic_baseline_sort_24,
                null
            ) {
                Log.d("TAG", binding.selectedSort)
                val action = DishesCategoryFragmentDirections.actionPageDishesCategoryToDialogSortCategory(
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

        setHasOptionsMenu(true)
    }

    override fun setupViews() {
        toolbar.title = args.name

        sharedModel.selectedSort.observe(viewLifecycleOwner, Observer {
            viewModel.applySort(it)
        })
    }

    inner class DishesCategoryBinding : Binding() {
        var selectedSort: String = ""
        var dishes: List<Category> by RenderProp(emptyList<Category>()) {
            dishesCategoryPagerAdapter = DishesCategoryPagerAdapter(root, it)
            view_pager.adapter = dishesCategoryPagerAdapter
            TabLayoutMediator(tabs, view_pager) { tab, position ->
                tab.text = dishes[position].name
            }.attach()
            tabs.tabMode = if (it.size >= 3 && (it[0].name.length + it[1].name.length) > 15) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED

        }

        override fun bind(data: IViewModelState) {
            data as DishesCategoryState
            dishes = data.dishes
            selectedSort = data.selectedSort
        }

    }
}