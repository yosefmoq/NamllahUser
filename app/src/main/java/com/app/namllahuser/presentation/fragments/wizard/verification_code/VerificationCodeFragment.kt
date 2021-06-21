package com.app.namllahuser.presentation.fragments.wizard.verification_code

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
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentVerificationCodeBinding
import com.app.namllahuser.domain.Constants
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.base.DialogData
import com.app.namllahuser.presentation.utils.toTimer
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VerificationCodeFragment : Fragment(), View.OnClickListener {
    lateinit var dialogUtils: DialogUtils
    private val verificationCodeViewModel: VerificationCodeViewModel by viewModels()

    private var fragmentVerificationCodeBinding: FragmentVerificationCodeBinding? = null
    private var phoneNumber = ""
    private var type = 1
    private var timer: Long = 180000;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentVerificationCodeBinding =
            FragmentVerificationCodeBinding.inflate(inflater, container, false)
        return fragmentVerificationCodeBinding?.apply {
            actionOnClick = this@VerificationCodeFragment
            dialogUtils = DialogUtils(requireActivity())
        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        arguments.let {
            phoneNumber = VerificationCodeFragmentArgs.fromBundle(it!!).phoneNumber
            type = VerificationCodeFragmentArgs.fromBundle(it).type

            fragmentVerificationCodeBinding?.tvPhoneNumber?.text = phoneNumber
        }
        if (type == Constants.RESEND_TYPE_NORMAL || type == Constants.RESEND_TYPE_FORGET_PASS)
            countDown()

        if (type == Constants.RESEND_TYPE_VARIFY)
            verificationCodeViewModel.resendCode(phoneNumber)

        observeLiveData()
    }

    private fun observeLiveData() {
        verificationCodeViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            Timber.tag(TAG).d("observeLiveData : Loading Status $it")
            dialogUtils.loading(it)
        })

        verificationCodeViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            Timber.tag(TAG).e("observeLiveData : Error Message ${it}")
            dialogUtils.showFailAlert(it)
        })

        verificationCodeViewModel.dialogLiveData.observe(viewLifecycleOwner, Observer {
            Timber.tag(TAG).e("observeLiveData : Error Message $it")

        })

        verificationCodeViewModel.resendCodeLiveData.observe(viewLifecycleOwner, Observer {

            if (it!!.status) {
                countDown()
                dialogUtils.showSuccessAlert("تم إرسال الكود بنجاح")
            } else {
                Toast.makeText(
                    requireContext(),
                    "الرجاء الإنتظار قليلا قبل الإرسال مرة أخرى",
                    Toast.LENGTH_SHORT
                ).show()
                dialogUtils.showFailAlert(it.msg)
            }
        })
        verificationCodeViewModel.verificationCodeLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {

                handleVerifyCodeResponse(it)
                //To Stop Livedata
                verificationCodeViewModel.verificationCodeLiveData.postValue(null)
            }
        })
        verificationCodeViewModel.checkPassword.observe(viewLifecycleOwner, Observer {
            it?.let {

                if (it.status!!) {
                    findNavController().navigate(
                        VerificationCodeFragmentDirections.actionVerificationCodeFragmentToResetPasswordFragment(
                            phoneNumber,
                            fragmentVerificationCodeBinding!!.pvVerifyOTP.text.toString().toInt()
                        )
                    )
                } else {
                    dialogUtils.showFailAlert(it.msg!!)
                }
                //To Stop Livedata
                verificationCodeViewModel.verificationCodeLiveData.postValue(null)
            }
        })
    }

    private fun handleVerifyCodeResponse(verificationCodeResponse: VerificationCodeResponse) {
        if (verificationCodeResponse.status!!) {
            if (verificationCodeResponse.userDto != null) {
                //Success Login
                //Save User data in SP
                verificationCodeViewModel.saveUserDataLocal(verificationCodeResponse.userDto!!)
                verificationCodeViewModel.changeLoginStatus(true)
                verificationCodeViewModel.saveToken(verificationCodeResponse.userDto!!.token)
                dialogUtils.showSuccessAlert(msg = "Success validation")
                object : CountDownTimer(2000, 1000) {
                    override fun onFinish() {
                        startActivity(HomeActivity.getIntent(requireActivity(), 1))
                        requireActivity().finishAffinity()
                    }

                    override fun onTick(millisUntilFinished: Long) {

                    }

                }.start()
            } else {
                if (verificationCodeResponse.status) {
                    //Account already active go to Login Page
                    verificationCodeViewModel.changeDialogLiveData(
                        DialogData(
                            title = "",
                            message = ""
                        )
                    )
                } else {
                    //OTP Code is error
                    verificationCodeViewModel.changeDialogLiveData(
                        DialogData(
                            title = "",
                            message = ""
                        )
                    )
                }
            }
        } else {
            dialogUtils.showFailAlert(verificationCodeResponse.msg!!)
        }

    }

    private fun initToolbar() {
        val toolbar = fragmentVerificationCodeBinding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar?.root)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
//        toolbar?.root?.title = ""

        val toolBarTitleView = toolbar?.toolbarTitle
        toolBarTitleView?.text = getString(R.string.verification_code)

        toolbar?.root?.setNavigationOnClickListener {
            onClickBack()
        }
    }

    companion object {
        private const val TAG = "VerificationCodeFragmen"
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentVerificationCodeBinding?.btnVerifyOTP -> onClickVerifyOTPCode()
            fragmentVerificationCodeBinding?.tvPhoneNumber -> onClickPhoneNumber()
            fragmentVerificationCodeBinding?.tvResendOTPCode -> onClickResendOTPCode()
        }
    }

    private fun onClickBack() {
        //Show Alert dialog
    }

    private fun onClickVerifyOTPCode() {
        val otpCode = fragmentVerificationCodeBinding?.pvVerifyOTP?.text.toString()
        val code = otpCode.toIntOrNull() ?: -1
        if (code != -1)
            if (type == Constants.RESEND_TYPE_FORGET_PASS) {
                verificationCodeViewModel.checkPassword(phoneNumber, code)

            } else {
                verificationCodeViewModel.verifyOTPCode(phoneNumber = phoneNumber, code = code)

            }
        else
            verificationCodeViewModel.changeErrorMessage("OTP Code Error")
    }

    private fun onClickPhoneNumber() {
        findNavController().popBackStack()
    }

    private fun onClickResendOTPCode() {
        if (timer == 0.toLong()) {
            verificationCodeViewModel.resendCode(phoneNumber)
        } else {
            Toast.makeText(
                requireContext(),
                "الرجاء الإنتظار قليلا قبل الإرسال مرة أخرى",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun countDown() {
        Log.v("ttt", "countDown")
        timer = 180000
        object : CountDownTimer(180000, 1000) {
            override fun onFinish() {
                timer = 0
                fragmentVerificationCodeBinding!!.tvTimer.setText("00:00")
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.v("ttt", "text")
                timer -= 1000
                fragmentVerificationCodeBinding!!.tvTimer.setText(timer.toTimer())
            }
        }.start()
    }

}