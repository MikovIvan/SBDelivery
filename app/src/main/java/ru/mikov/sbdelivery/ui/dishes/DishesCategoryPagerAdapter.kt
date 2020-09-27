package ru.mikov.sbdelivery.ui.dishes

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.ui.dishes.dish.DishFragment

class DishesCategoryPagerAdapter(
    fragmentManager: FragmentManager,
    private val dishes: List<Category>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = DishFragment.newInstance(dishes[position].categoryId)
    override fun getPageTitle(position: Int) = dishes[position].name
    override fun getCount() = dishes.size

}