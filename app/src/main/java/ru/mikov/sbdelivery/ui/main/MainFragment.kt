package ru.mikov.sbdelivery.ui.main

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Dish
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.main.MainState
import ru.mikov.sbdelivery.viewmodels.main.MainViewModel

class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_main
    override val binding: ArticlesBinding by lazy { ArticlesBinding() }


    private val popularDishesAdapter = DishesAdapter()
    override fun setupViews() {
        requireActivity().appbar.visibility = View.VISIBLE

        with(rv_popular) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularDishesAdapter
        }

        tv_popular_all.setOnClickListener {
            viewModel.navigate(NavigationCommand.To(R.id.nav_menu))
        }

    }

    inner class ArticlesBinding : Binding() {
        private var popularDishes: List<Dish> by RenderProp(emptyList<Dish>()) {
            popularDishesAdapter.submitList(it.shuffled())
        }

        override fun bind(data: IViewModelState) {
            data as MainState
            popularDishes = data.popularDishes
        }

    }

}