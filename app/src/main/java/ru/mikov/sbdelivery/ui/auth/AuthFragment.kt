package ru.mikov.sbdelivery.ui.auth

import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_auth.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.viewmodels.auth.AuthViewModel
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand

class AuthFragment : BaseFragment<AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override val layout: Int = R.layout.fragment_auth

    override fun setupViews() {
        btn_registration.setOnClickListener {
            viewModel.navigate(NavigationCommand.To(R.id.page_registration))
        }
    }


}