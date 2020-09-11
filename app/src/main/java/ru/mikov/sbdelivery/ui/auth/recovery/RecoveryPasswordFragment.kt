package ru.mikov.sbdelivery.ui.auth.recovery

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_recovery_password.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.viewmodels.auth.recovery.RecoveryPasswordState
import ru.mikov.sbdelivery.viewmodels.auth.recovery.RecoveryPasswordViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand

class RecoveryPasswordFragment : BaseFragment<RecoveryPasswordViewModel>() {
    override val viewModel: RecoveryPasswordViewModel by viewModels()
    override val layout: Int = R.layout.fragment_recovery_password
    override val binding: RecoveryPasswordBinding by lazy { RecoveryPasswordBinding() }

    override fun setupViews() {
        btn_send_email.setOnClickListener {
            if (binding.isSendBtnEnable) {
                viewModel.sendEmail(binding.email)
                val action = RecoveryPasswordFragmentDirections.actionNavRecoveryPasswordToPageEnterCode(binding.email)
                viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
            }
        }

        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = viewModel.handleBtn()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.handleEmail(et_email.text.toString())
            }

        })
    }

    inner class RecoveryPasswordBinding : Binding() {
        var email: String = ""

        var isSendBtnEnable: Boolean by RenderProp(false) {
            btn_send_email.isActivated = it
        }

        override fun bind(data: IViewModelState) {
            data as RecoveryPasswordState
            email = data.email
            isSendBtnEnable = data.isSendBtnEnable
        }

    }

}