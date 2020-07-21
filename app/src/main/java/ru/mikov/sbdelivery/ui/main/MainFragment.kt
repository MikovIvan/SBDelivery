package ru.mikov.sbdelivery.ui.main

import android.view.View
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_root.*
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.viewmodels.main.MainViewModel

class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_main

    override fun setupViews() {
        requireActivity().appbar.visibility = View.VISIBLE
    }

}