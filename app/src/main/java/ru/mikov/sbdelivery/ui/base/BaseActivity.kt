package ru.mikov.sbdelivery.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import kotlinx.android.synthetic.main.activity_root.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.extensions.blockInput
import ru.mikov.sbdelivery.extensions.dpToIntPx
import ru.mikov.sbdelivery.extensions.unblockInput
import ru.mikov.sbdelivery.viewmodels.base.*
import ru.mikov.sbdelivery.viewmodels.base.Loading.*

abstract class BaseActivity<T : BaseViewModel<out IViewModelState>> : AppCompatActivity() {
    protected abstract val viewModel: T
    protected abstract val layout: Int
    lateinit var navController: NavController
    lateinit var appbarConfiguration: AppBarConfiguration

    val toolbarBuilder = ToolbarBuilder()

    //    set listeners, tuning views
    abstract fun subscribeOnState(state: IViewModelState)

    abstract fun renderNotification(notify: Notify)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)
        viewModel.observeState(this) { subscribeOnState(it) }
        viewModel.observeNotifications(this) { renderNotification(it) }
        viewModel.observeNavigation(this) { subscribeOnNavigation(it) }
        viewModel.observeLoading(this) { renderLoading(it) }

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.restoreState()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appbarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (navController.currentDestination != null && navController.currentDestination!!.id == R.id.nav_main) {
            finish()
            return
        }
        super.onBackPressed()
    }

    private fun subscribeOnNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> {
                navController.navigate(
                    command.destination,
                    command.args,
                    command.options,
                    command.extras
                )
            }

            is NavigationCommand.FinishLogin -> {
                navController.navigate(R.id.finish_login)
                if (command.privateDestination != null)
                    navController.navigate(command.privateDestination)
            }

            is NavigationCommand.StartLogin -> {
                navController.navigate(
                    R.id.start_login,
                    bundleOf("private_destination" to (command.privateDestination ?: -1))
                )
            }
        }
    }

    open fun renderLoading(loadingState: Loading) {
        when (loadingState) {
            SHOW_LOADING -> progress.isVisible = true
            SHOW_BLOCKING_LOADING -> {
                progress.isVisible = true
                blockInput()
            }
            HIDE_LOADING -> {
                progress.isVisible = false
                unblockInput()
            }
        }
    }

    class ToolbarBuilder() {
        var title: String? = null
        var subtitle: String? = null
        var logo: String? = null
        var visibility: Boolean = true
        var items: MutableList<MenuItemHolder> = mutableListOf()

        fun setTitle(title: String): ToolbarBuilder {
            this.title = title
            return this
        }

        fun setSubTitle(subtitle: String): ToolbarBuilder {
            this.subtitle = subtitle
            return this
        }

        fun setLogo(logo: String): ToolbarBuilder {
            this.logo = logo
            return this
        }

        fun setVisibility(isVisible: Boolean): ToolbarBuilder {
            this.visibility = isVisible
            return this
        }

        fun addMenuItem(item: MenuItemHolder): ToolbarBuilder {
            this.items.add(item)
            return this
        }

        fun invalidate(): ToolbarBuilder {
            this.title = null
            this.subtitle = null
            this.logo = null
            this.visibility = true
            this.items.clear()
            return this
        }

        fun prepare(prepareFn: (ToolbarBuilder.() -> Unit)?): ToolbarBuilder {
            prepareFn?.invoke(this)
            return this
        }

        fun build(context: FragmentActivity) {

            //show appbar if hidden due to scroll behavior
            context.appbar.setExpanded(true, true)

            with(context.toolbar) {
                if (this@ToolbarBuilder.title != null) title = this@ToolbarBuilder.title
                subtitle = this@ToolbarBuilder.subtitle
                if (this@ToolbarBuilder.logo != null) {
                    val logoSize = context.dpToIntPx(40)
                    val logoMargin = context.dpToIntPx(16)
                    val logoPlaceholder = getDrawable(context, R.drawable.logo_placeholder)

                    logo = logoPlaceholder

                    val logo = children.last() as? ImageView
                    if (logo != null) {
                        logo.scaleType = ImageView.ScaleType.CENTER_CROP
                        (logo.layoutParams as? Toolbar.LayoutParams)?.let {
                            it.width = logoSize
                            it.height = logoSize
                            it.marginEnd = logoMargin
                            logo.layoutParams = it
                        }

                        Glide.with(context)
                            .load(this@ToolbarBuilder.logo)
                            .apply(circleCropTransform())
                            .override(logoSize)
                            .into(logo)
                    }
                } else {
                    logo = null
                }
            }
        }
    }

    data class MenuItemHolder(
        val title: String,
        val menuId: Int,
        val icon: Int,
        val actionViewLayout: Int? = null,
        val clickListener: ((MenuItem) -> Unit)? = null
    )
}
