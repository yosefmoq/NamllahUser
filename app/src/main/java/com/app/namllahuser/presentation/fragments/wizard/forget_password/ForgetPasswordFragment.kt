package com.app.namllahuser.presentation.fragments.wizard.forget_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentForgetPasswordBinding
import com.app.namllahuser.domain.Constants.RESEND_TYPE_FORGET_PASS
import com.app.namllahuser.presentation.fragments.wizard.reset_password.ResetPasswordVM
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(), View.OnClickListener {

    private var fragmentForgetPasswordBinding: FragmentForgetPasswordBinding? = null
    private val forgetPasswordVM:ForgetPasswordVM by viewModels()
    lateinit var dialogUtils:DialogUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentForgetPasswordBinding =
            FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return fragmentForgetPasswordBinding?.apply {
            actionOnClick = this@ForgetPasswordFragment
            dialogUtils = DialogUtils(requireActivity())
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        observeLiveData()
    }


    private fun initToolbar() {
        val toolbar = fragmentForgetPasswordBinding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar?.root)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolBarTitleView = toolbar?.toolbarTitle
        toolBarTitleView?.text = getString(R.string.forget_password)

        toolbar?.root?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    companion object {
        private const val TAG = "ForgetPasswordFragment"
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentForgetPasswordBinding?.btnSendOTPCode -> onClickSendOTPCode()
        }
    }

    private fun onClickSendOTPCode() {
        forgetPasswordVM.forgetPassword(fragmentForgetPasswordBinding!!.etPhoneNumber.text.toString())
    }
    fun observeLiveData(){
        forgetPasswordVM.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        forgetPasswordVM.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        forgetPasswordVM.forgetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            if(it.status){
                findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToVerificationCodeFragment(phoneNumber = fragmentForgetPasswordBinding!!.etPhoneNumber.text.toString(),type = RESEND_TYPE_FORGET_PASS))
            }else{
                dialogUtils.showFailAlert(it.msg)
            }
        })

    }
}