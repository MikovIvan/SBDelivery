package ru.mikov.sbdelivery.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dish.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Dish

class DishesAdapter :
    ListAdapter<Dish, DishesAdapter.DishesHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesHolder {
        val containerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dish, parent, false)
        return DishesHolder(containerView)
    }

    override fun onBindViewHolder(holder: DishesHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DishesHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(
            item: Dish
        ) {
            tv_price.text = item.price.toString()
            tv_title.text = item.name

            Glide.with(containerView.context)
                .load(item.image)
                .into(iv_image)
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean =
            oldItem == newItem
    }
}