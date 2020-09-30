package ru.mikov.sbdelivery.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import ru.mikov.sbdelivery.R

class SortCategoryDialog : DialogFragment() {
    companion object {
        const val CHOOSE_SELECTED_SORT = "CHOOSE_SELECTED_SORT"
        const val SELECTED_SORT = "SELECTED_SORT"
    }

    private val args: SortCategoryDialogArgs by navArgs()
    private var selectedSort: String = ""
    private val sortItems = arrayOf("A to Z", "Z to A", "More popular", "Less popular", "Higher rating", "Lower rating")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        selectedSort = ""
        selectedSort = savedInstanceState?.getString("selectedSort") ?: args.selectedSort
        val tv = TextView(context)
        with(tv) {
            text = context.getString(R.string.sort)
            textSize = 20F
            setTextColor(resources.getColor(android.R.color.black))
            setPadding(20, 30, 20, 30)
        }
        val adb = AlertDialog.Builder(requireContext())
            .setCustomTitle(tv)
            .setSingleChoiceItems(sortItems, sortItems.indexOf(selectedSort)) { dialog, item ->
                selectedSort = sortItems[item]
            }
            .setPositiveButton("Apply") { _, _ ->
                setFragmentResult(
                    CHOOSE_SELECTED_SORT,
                    bundleOf(SELECTED_SORT to selectedSort)
                )
            }
            .setNegativeButton("Reset") { _, _ ->
                setFragmentResult(
                    CHOOSE_SELECTED_SORT,
                    bundleOf(SELECTED_SORT to "")
                )
            }
        return adb.create()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("selectedSort", selectedSort)
        super.onSaveInstanceState(outState)
    }
}