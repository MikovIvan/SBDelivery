package ru.mikov.sbdelivery.ui.auth.recovery

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.extensions.disable
import ru.mikov.sbdelivery.extensions.enable
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.ui.base.Binding
import ru.mikov.sbdelivery.viewmodels.auth.recovery.EnterCodeState
import ru.mikov.sbdelivery.viewmodels.auth.recovery.EnterCodeViewModel
import ru.mikov.sbdelivery.viewmodels.base.IViewModelState
import ru.mikov.sbdelivery.viewmodels.base.NavigationCommand

class EnterCodeFragment : BaseFragment<EnterCodeViewModel>() {
    private val args: EnterCodeFragmentArgs by navArgs()
    override val viewModel: EnterCodeViewModel by viewModels()
    override val layout: Int = R.layout.fragment_enter_code
    override val binding: EnterCodeBinding by lazy { EnterCodeBinding() }

    override fun setupViews() {
        et_first_number.addTextChangedListener(GenericTextWatcher(et_first_number, et_second_number))
        et_second_number.addTextChangedListener(GenericTextWatcher(et_second_number, et_third_number))
        et_third_number.addTextChangedListener(GenericTextWatcher(et_third_number, et_fourth_number))
        et_fourth_number.addTextChangedListener(GenericTextWatcher(et_fourth_number, null))

        et_first_number.setOnKeyListener(GenericKeyEvent(et_first_number, null))
        et_second_number.setOnKeyListener(GenericKeyEvent(et_second_number, et_first_number))
        et_third_number.setOnKeyListener(GenericKeyEvent(et_third_number, et_second_number))
        et_fourth_number.setOnKeyListener(GenericKeyEvent(et_fourth_number, et_third_number))
    }

    inner class EnterCodeBinding : Binding() {
        var first: String = ""
        var second: String = ""
        var third: String = ""
        var fourth: String = ""
        var code: String = ""
        var isCodeValid: Boolean = false

        override fun bind(data: IViewModelState) {
            data as EnterCodeState
            first = data.first
            second = data.second
            third = data.third
            fourth = data.fourth
            code = data.code
            isCodeValid = data.isCodeValid
        }

    }

    inner class GenericTextWatcher internal constructor(private val currentView: EditText, private val nextView: EditText?) : TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.et_first_number -> if (text.length == 1) {
                    nextView!!.enable()
                    viewModel.handleFirst(text)
                }
                R.id.et_second_number -> if (text.length == 1) {
                    nextView!!.enable()
                    viewModel.handleSecond(text)
                }
                R.id.et_third_number -> if (text.length == 1) {
                    nextView!!.enable()
                    viewModel.handleThird(text)
                }
                R.id.et_fourth_number -> if (text.length == 1) {
                    viewModel.handleFourth(text)
                    viewModel.handleCode()
                    viewModel.sendCode(args.email, binding.code)
                    if (binding.isCodeValid) {
                        viewModel.navigate(NavigationCommand.To(R.id.page_enter_new_password))
                    }

                }
            }
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

    }

}

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et_first_number && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            currentView.disable()
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}

