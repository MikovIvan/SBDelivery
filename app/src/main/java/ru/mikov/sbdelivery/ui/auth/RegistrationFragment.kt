package ru.mikov.sbdelivery.ui.auth

import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.extensions.*
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.viewmodels.auth.RegistrationViewModel

class RegistrationFragment : BaseFragment<RegistrationViewModel>() {
    override val viewModel: RegistrationViewModel by viewModels()
    override val layout: Int = R.layout.fragment_registration

    override fun setupViews() {
        et_name.afterTextChanged {
            if (it.hasDigits() && it.isNotBlank()) tv_error_name.visible() else tv_error_name.invisible()
        }
        et_surname.afterTextChanged { if (it.hasDigits() && it.isNotBlank()) tv_error_surname.visible() else tv_error_surname.invisible() }
        et_login.afterTextChanged { if (!it.isEmailValid()) tv_error_login.visible() else tv_error_login.invisible() }
        et_password.afterTextChanged { if (it.isBlank()) tv_error_password.visible() else tv_error_password.invisible() }
    }

}