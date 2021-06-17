package com.app.namllahuser.presentation.fragments.wizard.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentResetPasswordBinding
import com.app.namllahuser.presentation.fragments.wizard.forget_password.ForgetPasswordVM
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment(), View.OnClickListener {
    private lateinit var dialogUtils: DialogUtils
    private val forgetPasswordVM:ForgetPasswordVM by viewModels()
    private var fragmentResetPasswordBinding: FragmentResetPasswordBinding? = null
    private var phoneNumber:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentResetPasswordBinding =
            FragmentResetPasswordBinding.inflate(inflater, container, false)
        return fragmentResetPasswordBinding?.apply {
            actionOnClick = this@ResetPasswordFragment
            dialogUtils = DialogUtils(requireActivity())
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            phoneNumber = ResetPasswordFragmentArgs.fromBundle(it!!).phoneNumber
        }
        initToolbar()
        observeLiveData()
    }

    private fun initToolbar() {
        val toolbar = fragmentResetPasswordBinding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar?.root)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolBarTitleView = toolbar?.toolbarTitle
        toolBarTitleView?.text = getString(R.string.reset_password)

        toolbar?.root?.setNavigationOnClickListener {
            onClickBack()
        }
    }
    fun observeLiveData(){
        forgetPasswordVM.forgetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            if(it.status){
                dialogUtils.showSuccessAlert(it.msg)

            }else{
                dialogUtils.showFailAlert(it.msg)
            }
        })
        forgetPasswordVM.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        forgetPasswordVM.loadingLiveData.observe(viewLifecycleOwner, Observer {
             dialogUtils.loading(it)
        })

    }

    override fun onClick(v: View?) {
        when(v?:return)
        {
               fragmentResetPasswordBinding!!.btnSendOTPCode2->sendOtpCode()

        }
    }

    private fun sendOtpCode() {
        forgetPasswordVM.forgetPassword(phoneNumber!!)
    }

    private fun onClickBack() {
        //Show Alert Dialog
    }

    companion object {
        private const val TAG = "ResetPasswordFragment"
    }
}