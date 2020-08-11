package ru.mikov.sbdelivery.ui.menu

import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.data.local.entities.Category
import ru.mikov.sbdelivery.utils.svg.SvgSoftwareLayerSetter


class CategoriesAdapter(
    private val listener: (Category) -> Unit
) : ListAdapter<Category, CategoriesAdapter.CategoriesHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val containerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoriesHolder(containerView)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class CategoriesHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(
            item: Category,
            listener: (Category) -> Unit
        ) {
            tv_category_title.text = item.name

            if (item.categoryId == "1") {
                Glide.with(containerView.context).load(R.drawable.ic_action_dishes)
                    .into(iv_category_image)
            } else {
                val requestBuilder: RequestBuilder<PictureDrawable> =
                    Glide.with(containerView.context)
                        .`as`(PictureDrawable::class.java)
                        .listener(SvgSoftwareLayerSetter())

                requestBuilder.load(item.icon).into(iv_category_image)
            }

            itemView.setOnClickListener { listener(item) }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.categoryId == newItem.categoryId

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }
}