package ru.mikov.sbdelivery.ui.auth

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.extensions.invisible
import ru.mikov.sbdelivery.extensions.visible
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.ui.delegates.RenderProp
import ru.mikov.sbdelivery.viewmodels.auth.RegistrationState
import ru.mikov.sbdelivery.viewmodels.auth.RegistrationViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState

class RegistrationFragment : BaseFragment<RegistrationViewModel>() {
    override val viewModel: RegistrationViewModel by viewModels()
    override val layout: Int = R.layout.fragment_registration
    override val binding: RegistrationBinding by lazy { RegistrationBinding() }

    override fun setupViews() {
        et_name.addTextChangedListener(textWatcher)
        et_surname.addTextChangedListener(textWatcher)
        et_login.addTextChangedListener(textWatcher)
        et_password.addTextChangedListener(textWatcher)

        btn_registration.setOnClickListener {
            if (!binding.isRegBtnEnable) {
                viewModel.checkFields()
            } else if (viewModel.checkValidation(
                    binding.name,
                    binding.surname,
                    binding.login,
                    binding.password
                )
            ) {
                //            viewModel.handleRegistration(
//                et_name.text.toString(),
//                et_surname.text.toString(),
//                et_login.text.toString(),
//                et_password.text.toString()
//            )
            }



            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()

        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = viewModel.handleBtn()

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.handleName(et_name.text.toString())
            viewModel.handleSurname(et_surname.text.toString())
            viewModel.handleLogin(et_login.text.toString())
            viewModel.handlePassword(et_password.text.toString())
        }
    }

    inner class RegistrationBinding : Binding() {
        var name: String = ""
        var surname: String = ""
        var login: String = ""
        var password: String = ""

        var isRegBtnEnable: Boolean by RenderProp(false) {
            btn_registration.isActivated = it
        }

        var nameError: Boolean by RenderProp(false) {
            with(tv_error_name) {
                text = "Empty field"
                if (it) visible() else invisible()
            }
        }

        var surnameError: Boolean by RenderProp(false) {
            with(tv_error_surname) {
                text = "Empty field"
                if (it) visible() else invisible()
            }
        }

        var loginError: Boolean by RenderProp(false) {
            with(tv_error_login) {
                text = "Empty field"
                if (it) visible() else invisible()
            }
        }

        var passwordError: Boolean by RenderProp(false) {
            with(tv_error_password) {
                text = "Empty field"
                if (it) visible() else invisible()
            }
        }

        var isNameValid: Boolean by RenderProp(true) {
            with(tv_error_name) {
                text = "Only letters"
                if (!it) visible() else invisible()
            }
        }

        var isSurnameValid: Boolean by RenderProp(true) {
            with(tv_error_surname) {
                text = "Only letters"
                if (!it) visible() else invisible()
            }
        }

        var isLoginValid: Boolean by RenderProp(true) {
            with(tv_error_login) {
                text = "Invalid e-mail"
                if (!it) visible() else invisible()
            }
        }

        var isPasswordValid: Boolean by RenderProp(true) {
            with(tv_error_password) {
                text = "Empty field"
                if (!it) visible() else invisible()
            }
        }

        override fun bind(data: IViewModelState) {
            data as RegistrationState
            name = data.name
            surname = data.surname
            login = data.login
            password = data.password
            nameError = data.nameError
            surnameError = data.surnameError
            loginError = data.loginError
            passwordError = data.passwordError
            isNameValid = data.isNameValid
            isSurnameValid = data.isSurnameValid
            isLoginValid = data.isLoginValid
            isPasswordValid = data.isPasswordValid
            isRegBtnEnable = data.isRegBtnEnable
        }

    }
}