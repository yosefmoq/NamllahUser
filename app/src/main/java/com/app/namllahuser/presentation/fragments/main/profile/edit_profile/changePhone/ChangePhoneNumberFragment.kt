package com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePhone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentChangePhoneNumberBinding
import com.app.namllahuser.databinding.FragmentChangeUsernameBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePhoneNumberFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var fragmentChangePhoneNumberBinding: FragmentChangePhoneNumberBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentChangePhoneNumberBinding =
            FragmentChangePhoneNumberBinding.inflate(inflater, container, false)
        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheet.parent.parent.requestLayout()
            }
        }

        return fragmentChangePhoneNumberBinding?.apply {
            actionOnClick = this@ChangePhoneNumberFragment
        }?.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }
    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentChangePhoneNumberBinding?.ibClose -> onClickClose()
            fragmentChangePhoneNumberBinding?.btnSave -> onClickSave()
        }
    }

    private fun onClickClose() {
        dismiss()
    }

    private fun onClickSave() {
    }

}