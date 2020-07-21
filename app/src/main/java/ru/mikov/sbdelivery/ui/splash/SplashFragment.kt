package ru.mikov.sbdelivery.ui.splash

import android.view.View
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_splash.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.splash.SplashViewModel


class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_splash
    override val binding: SplashBinding by lazy { SplashBinding() }

    override fun setupViews() {
        requireActivity()!!.appbar.visibility = View.GONE

        iv_logo.setOnClickListener { viewModel.navigate(NavigationCommand.To(R.id.nav_main)) }

    }

    inner class SplashBinding : Binding() {
        override fun bind(data: IViewModelState) {

        }

    }
}