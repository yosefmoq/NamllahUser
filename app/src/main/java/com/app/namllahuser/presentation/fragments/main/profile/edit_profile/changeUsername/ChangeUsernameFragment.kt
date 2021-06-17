package com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changeUsername

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentChangeUsernameBinding
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.EditProfileFragment
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.EditProfileVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeUsernameFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var fragmentChangeUsernameBinding: FragmentChangeUsernameBinding? = null

    val editProfileVM:EditProfileVM by viewModels()
    lateinit var onUserNameClick: OnUserNameClick
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentChangeUsernameBinding =
            FragmentChangeUsernameBinding.inflate(inflater, container, false)
        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheet.parent.parent.requestLayout()
            }
        }

        return fragmentChangeUsernameBinding?.apply {
            actionOnClick = this@ChangeUsernameFragment
            arguments.let {
                name = ChangeUsernameFragmentArgs.fromBundle(it!!).username.name
                onUserNameClick = ChangeUsernameFragmentArgs.fromBundle(it).username.onUserNameClick
            }
        }?.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentChangeUsernameBinding?.ibClose -> onClickClose()
            fragmentChangeUsernameBinding?.btnSave -> onClickSave()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun onClickClose() {
        dismiss()
    }

    private fun onClickSave() {
        if(fragmentChangeUsernameBinding!!.etCurrentPassword.text.toString().length<8){
            fragmentChangeUsernameBinding!!.etCurrentPassword.error = "The length must be more than 8"
        }else{
            dismiss()
            onUserNameClick.onClick(fragmentChangeUsernameBinding!!.etCurrentPassword.text.toString())

        }
    }
    interface OnUserNameClick{
        fun onClick(name:String)
    }
}