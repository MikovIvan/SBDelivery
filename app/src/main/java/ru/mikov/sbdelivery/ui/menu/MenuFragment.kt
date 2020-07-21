package ru.mikov.sbdelivery.ui.menu

import androidx.fragment.app.activityViewModels
import ru.mikov.sbdelivery.R
import ru.mikov.sbdelivery.ui.base.BaseFragment
import ru.mikov.sbdelivery.viewmodels.menu.MenuViewModel

class MenuFragment : BaseFragment<MenuViewModel>() {

    override val viewModel: MenuViewModel by activityViewModels()
    override val layout: Int = R.layout.fragment_menu

    override fun setupViews() {

    }

}