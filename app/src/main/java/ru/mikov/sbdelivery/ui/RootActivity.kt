package ru.mikov.sbdelivery.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_root.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseActivity
import ru.mikov.sbdelivery.viewmodels.RootState
import ru.mikov.sbdelivery.viewmodels.RootViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.base.Notify

class RootActivity : BaseActivity<RootViewModel>() {

    override val layout: Int = R.layout.activity_root
    public override val viewModel: RootViewModel by viewModels()
    private var isAuth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appbarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main,
                R.id.nav_menu,
                R.id.nav_favorites,
                R.id.nav_cart,
                R.id.nav_profile,
                R.id.nav_orders,
                R.id.nav_notifications,
                R.id.nav_about
            )
        )

        setupActionBarWithNavController(navController, appbarConfiguration)

        nav_view.setNavigationItemSelectedListener {
            //if click on bottom navigation item -> navigate to destination by item id
            viewModel.navigate(NavigationCommand.To(it.itemId))
            true
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            if(destination.id == R.id.nav_auth) nav_view.selectItem(arguments?.get("private_destination")as Int?)
//
//            if (destination.id == R.id.nav_auth && isAuth) {
//                controller.popBackStack()
//                val private = arguments?.get("private_destination") as Int?
//                if (private != null) controller.navigate(private)
//            }

//            if (destination.id == R.id.nav_auth && isAuth) {
//                controller.popBackStack()
//                if (arguments != null && arguments["private_destination"] != null)
//                    viewModel.navigate(NavigationCommand.To(arguments["private_destination"] as Int))
//            }
        }
    }

    override fun renderNotification(notify: Notify) {
        val snackbar = Snackbar.make(drawer_layout, notify.message, Snackbar.LENGTH_LONG)
//        val snackbar = Snackbar.make(container, notify.message, Snackbar.LENGTH_LONG)
//        snackbar.anchorView = findViewById<Bottombar>(R.id.bottombar) ?: nav_view
//
//        if (bottombar != null) snackbar.anchorView = bottombar
//        else snackbar.anchorView = nav_view

        when (notify) {
            is Notify.TextMessage -> {
            }

            is Notify.ActionMessage -> {
                snackbar.setActionTextColor(getColor(R.color.color_accent_dark))
                snackbar.setAction(notify.actionLabel) {
                    notify.actionHandler?.invoke()
                }
            }

            is Notify.ErrorMessage -> {
                with(snackbar) {
                    setBackgroundTint(getColor(R.color.design_default_color_error))
                    setTextColor(getColor(android.R.color.white))
                    setActionTextColor(getColor(android.R.color.white))
                    setAction(notify.errLabel) {
                        notify.errHandler?.invoke()
                    }

                }
            }
        }

        snackbar.show()
    }

    override fun subscribeOnState(state: IViewModelState) {
        //do smth with state
        isAuth = (state as RootState).isAuth
    }
}