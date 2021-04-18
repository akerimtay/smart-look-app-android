package com.akerimtay.smartwardrobe.common.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseBottomSheetDialogFragment
import com.akerimtay.smartwardrobe.common.base.adapter.ContentAdapter
import com.akerimtay.smartwardrobe.common.model.ActionMenuType
import com.akerimtay.smartwardrobe.common.utils.args
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.withArgs
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.ActionMenuItem
import com.akerimtay.smartwardrobe.databinding.DialogActionsBinding
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

private const val EXTRA_BUTTONS = "EXTRA_BUTTONS"
private const val DIVIDER_SIZE = 0

class ActionsDialog : BaseBottomSheetDialogFragment(R.layout.dialog_actions) {
    companion object {
        fun show(
            fragmentManager: FragmentManager,
            actionMenuTypes: Set<ActionMenuType>,
        ) {
            ActionsDialog().withArgs(
                EXTRA_BUTTONS to actionMenuTypes
            ).show(fragmentManager, ActionsDialog::class.java.name)
        }
    }

    private val binding: DialogActionsBinding by viewBinding()
    private val actionMenuTypes: Set<ActionMenuType> by args(EXTRA_BUTTONS)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentAdapter = get<ContentAdapter<ItemContentType>> { parametersOf(null) }
        with(binding.recyclerView) {
            adapter = contentAdapter
            setHasFixedSize(true)
            addItemDecoration(DefaultItemDecorator(divider = dip(DIVIDER_SIZE)))
        }
        contentAdapter.collection = actionMenuTypes.map {
            ActionMenuItem(it) {
                (parentFragment as? ActionsDialogCallback)?.onActionMenuClick(it)
                dismiss()
            }
        }
    }

    interface ActionsDialogCallback {
        fun onActionMenuClick(actionMenuType: ActionMenuType)
    }
}