package com.app.namllahuser.presentation.fragments.wizard.reset_password

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentResetPasswordBinding
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.fragments.wizard.forget_password.ForgetPasswordVM
import com.app.namllahuser.presentation.service.MyFirebaseInstanceIDService
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment(), View.OnClickListener {
    private lateinit var dialogUtils: DialogUtils
    private val forgetPasswordVM:ResetPasswordVM by viewModels()
    private var fragmentResetPasswordBinding: FragmentResetPasswordBinding? = null
    private var phoneNumber:String? = null
    private var code:Int = 0
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
            code = ResetPasswordFragmentArgs.fromBundle(it).code
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
        forgetPasswordVM.resetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            if(it.status!!){
                dialogUtils.showSuccessAlert(it.msg!!)
                forgetPasswordVM.saveUserDataLocal(it.userDto!!)
                forgetPasswordVM.changeLoginStatus(true)
                forgetPasswordVM.saveToken(it.userDto!!.token)
                object : CountDownTimer(2000, 1000) {
                    override fun onFinish() {
                        startActivity(HomeActivity.getIntent(requireActivity(), 1,null))
                        requireActivity().finishAffinity()
                    }

                    override fun onTick(millisUntilFinished: Long) {

                    }

                }.start()

            }else{
                dialogUtils.showFailAlert(it.msg!!)
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
        if(fragmentResetPasswordBinding!!.etPassword.text.toString().length<6){
            fragmentResetPasswordBinding!!.etPassword.error = "The length must be more than 5 char"
        }else if(fragmentResetPasswordBinding!!.etConfirmPassword.text.toString().isEmpty()||fragmentResetPasswordBinding!!.etConfirmPassword.text.toString() != fragmentResetPasswordBinding!!.etPassword.text.toString()){
            fragmentResetPasswordBinding!!.etConfirmPassword.error = "The confirm password doesn't match the password"
        }else{
            forgetPasswordVM.resetPassword(phoneNumber!!,fragmentResetPasswordBinding!!.etPassword.text.toString(),code)
        }
    }

    private fun onClickBack() {
        //Show Alert Dialog
    }

    companion object {
        private const val TAG = "ResetPasswordFragment"
    }
}