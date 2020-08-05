package ru.mikov.sbdelivery.ui.splash

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_root.*
import kotlinx.android.synthetic.main.fragment_splash.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand
import ru.mikov.sbdelivery.viewmodels.splash.LoadResult
import ru.mikov.sbdelivery.viewmodels.splash.SplashViewModel


class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_splash
    override val binding: SplashBinding by lazy { SplashBinding() }

    override fun setupViews() {
        requireActivity()!!.appbar.visibility = View.GONE

        iv_logo.setOnClickListener { viewModel.navigate(NavigationCommand.To(R.id.nav_main)) }

        viewModel.syncDataIfNeed().observe(this, Observer {
            when (it) {
                is LoadResult.Loading -> {

                }
                is LoadResult.Success -> {
                    viewModel.navigate(NavigationCommand.To(R.id.nav_main))
                }
                is LoadResult.Error -> {
                    Snackbar.make(
                        root_container,
                        it.errorMessage.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        })

    }

    inner class SplashBinding : Binding() {
        override fun bind(data: IViewModelState) {

        }

    }
}