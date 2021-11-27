package com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.fragment.app.DialogFragment
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentChangePasswordBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePasswordFragment() : BottomSheetDialogFragment(), View.OnClickListener {

    private var fragmentUserEditPasswordBinding: FragmentChangePasswordBinding? = null
    lateinit var changePasswordClick: ChangePasswordClick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentUserEditPasswordBinding =
            FragmentChangePasswordBinding.inflate(inflater, container, false)
        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheet.parent.parent.requestLayout()
            }
        }

        return fragmentUserEditPasswordBinding?.apply {
            actionOnClick = this@ChangePasswordFragment
            arguments.let {
                changePasswordClick =ChangePasswordFragmentArgs.fromBundle(it!!).passwordObject.onChangePasswordClick
            }
        }?.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentUserEditPasswordBinding?.ibClose -> onClickClose()
            fragmentUserEditPasswordBinding?.btnSave -> onClickSave()
        }
    }

    private fun onClickClose() {
        dismiss()
    }

    private fun onClickSave() {
        if(fragmentUserEditPasswordBinding!!.etCurrentPassword.text.toString().length < 6){
            fragmentUserEditPasswordBinding!!.etCurrentPassword.error = "Please Enter a valid password"

        }else if(fragmentUserEditPasswordBinding!!.etNewPassword.text.toString().length<6){
            fragmentUserEditPasswordBinding!!.etNewPassword.error = "Please Enter a valid new password"

        }else if(fragmentUserEditPasswordBinding!!.etNewPassword.text.toString() != fragmentUserEditPasswordBinding!!.etConfirmNewPassword.text.toString()){
            fragmentUserEditPasswordBinding!!.etConfirmNewPassword.error = "The confirm password doesn't match the new password"

        }else{
            dismiss()
            changePasswordClick.onChangePasswordClick(fragmentUserEditPasswordBinding!!.etCurrentPassword.text.toString(),fragmentUserEditPasswordBinding!!.etNewPassword.text.toString())
        }
    }

    interface ChangePasswordClick {
        fun onChangePasswordClick(oldPass: String, pass: String)
    }
}