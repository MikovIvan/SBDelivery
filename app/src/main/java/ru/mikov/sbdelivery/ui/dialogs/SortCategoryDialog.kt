package ru.mikov.sbdelivery.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

class SortCategoryDialog : DialogFragment() {
    companion object {
        const val SELECTED_SORT = "SELECTED_SORT"
    }

    private val args: SortCategoryDialogArgs by navArgs()
    private val selectedSort: Int = -1
    private val sortItems = arrayOf("A to Z", "Z to A", "More popular", "Less popular", "Higher rating", "Lower rating")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        selectedSort == -1
        selectedSort == savedInstanceState?.getInt("selectedSort") ?: args.selectedSort

        val tv = TextView(context)
        with(tv) {
            text = "Sort"
            textSize = 20F
            setTextColor(resources.getColor(android.R.color.black))
            setPadding(20, 30, 20, 30)
        }
        val adb = AlertDialog.Builder(requireContext())
            .setCustomTitle(tv)
            .setSingleChoiceItems(sortItems, -1) { dialog, item ->
                selectedSort == item
            }
            .setPositiveButton("Apply") { _, _ ->
                setFragmentResult(
                    SELECTED_SORT,
                    bundleOf(SELECTED_SORT to selectedSort)
                )
            }
            .setNegativeButton("Reset") { _, _ ->
                setFragmentResult(
                    SELECTED_SORT,
                    bundleOf(SELECTED_SORT to -1)
                )
            }
        return adb.create()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("selectedSort", selectedSort)
        super.onSaveInstanceState(outState)
    }
}