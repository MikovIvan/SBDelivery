package ru.mikov.sbdelivery.ui.dishes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.mikov.sbdelivery.data.local.entities.Category

class DishesCategoryPagerAdapter(
    fa: FragmentActivity,
    private val dishes: List<Category>
) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment = DishesFragment.newInstance(dishes[position].categoryId)

    override fun getItemCount(): Int = dishes.size
}