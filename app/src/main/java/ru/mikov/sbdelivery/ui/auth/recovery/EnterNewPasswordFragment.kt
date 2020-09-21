package ru.mikov.sbdelivery.ui.auth.recovery

import androidx.fragment.app.viewModels
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.viewmodels.auth.recovery.EnterNewPasswordViewModel

class EnterNewPasswordFragment : BaseFragment<EnterNewPasswordViewModel>() {
    override val viewModel: EnterNewPasswordViewModel by viewModels()
    override val layout: Int = R.layout.fragment_enter_new_password


    override fun setupViews() {

    }

}