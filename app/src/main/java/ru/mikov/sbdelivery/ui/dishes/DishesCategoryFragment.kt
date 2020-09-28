package ru.mikov.sbdelivery.ui.dishes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dishes_category.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.viewmodels.base.DishCategoryViewModelFactory
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
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

    private val args: DishesCategoryFragmentArgs by navArgs()
    private lateinit var dishesCategoryPagerAdapter: DishesCategoryPagerAdapter

    override fun setupViews() {
        toolbar.title = args.name
    }

    inner class DishesCategoryBinding : Binding() {
        var dishes: List<Category> by RenderProp(emptyList<Category>()) {
            dishesCategoryPagerAdapter = DishesCategoryPagerAdapter(childFragmentManager, it)
            view_pager.adapter = dishesCategoryPagerAdapter
            with(tabs) {
                tabMode = if (it.size >= 3 && (it[0].name.length + it[1].name.length) > 15) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
                setupWithViewPager(view_pager)
            }
        }

        override fun bind(data: IViewModelState) {
            data as DishesCategoryState
            dishes = data.dishes
        }

    }
}