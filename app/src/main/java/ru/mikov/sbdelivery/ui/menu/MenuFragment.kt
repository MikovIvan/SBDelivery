package ru.mikov.sbdelivery.ui.menu

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.menu.MenuState
import ru.mikov.sbdelivery.viewmodels.menu.MenuViewModel

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val viewModel: MenuViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_menu
    override val binding: CategoriesBinding by lazy { CategoriesBinding() }

    private val categoriesAdapter = CategoriesAdapter {
        Toast.makeText(context, "Go to ${it.name}", Toast.LENGTH_SHORT).show()
    }

    override fun setupViews() {

        with(rv_categories) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoriesAdapter
        }
    }

    inner class CategoriesBinding : Binding() {
        private var categories: List<Category> by RenderProp(emptyList<Category>()) {
            categoriesAdapter.submitList(it)
        }

        override fun bind(data: IViewModelState) {
            data as MenuState
            categories = data.categories
        }

    }

}