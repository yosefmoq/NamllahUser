package com.app.namllahuser.presentation.fragments.wizard.sign_up

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.data.auth.sign_up.SignUpResponse
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentSignUpBinding
import com.app.namllahuser.presentation.utils.DialogUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private var fragmentSignUpBinding: FragmentSignUpBinding? = null
    private var phoneNumber = ""
    lateinit var dialogUtils: DialogUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return fragmentSignUpBinding?.apply {
            actionOnClick = this@SignUpFragment
            dialogUtils =DialogUtils(requireActivity())

        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initToolbar()
        observeLiveData()
    }

    private fun initViews() {
        val onTextChanged = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                fragmentSignUpBinding?.tilUsername?.error = null
                fragmentSignUpBinding?.tilPhoneNumber?.error = null
                fragmentSignUpBinding?.tilPassword?.error = null
                fragmentSignUpBinding?.tilConfirmPassword?.error = null
            }
        }
        fragmentSignUpBinding?.etUsername?.addTextChangedListener(onTextChanged)
        fragmentSignUpBinding?.etPhoneNumber?.addTextChangedListener(onTextChanged)
        fragmentSignUpBinding?.etPassword?.addTextChangedListener(onTextChanged)
        fragmentSignUpBinding?.etConfirmPassword?.addTextChangedListener(onTextChanged)
    }

    private fun initToolbar() {
        val toolbar = fragmentSignUpBinding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar?.root)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolBarTitleView = toolbar?.toolbarTitle
        toolBarTitleView?.text = getString(R.string.create_new_account)

        toolbar?.root?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeLiveData() {
        signUpViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer{
            dialogUtils.loading(it)
        })

        signUpViewModel.errorLiveData.observe(viewLifecycleOwner, Observer{
            Timber.tag(TAG).e("observeLiveData : Error Message ${Gson().toJson(it)}")
            dialogUtils.showFailAlert(it)
        })

        signUpViewModel.signUpLiveData.observe(viewLifecycleOwner, Observer{
            it?.let {
                handleSignUpResponse(it)
                //To Stop Livedata
//                signUpViewModel.signUpLiveData.postValue(null)
            }
        })
    }

    private fun handleSignUpResponse(signUpResponse: SignUpResponse) {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToVerificationCodeFragment(
                phoneNumber = phoneNumber
            )
        )

        if (signUpResponse.status!!) {
            //Show Message to User With Activation Code
            //Navigate to Verification Code UI
        } else {
            //Show Message to User With Error Message
            val errorMessage = signUpResponse.msg ?: "Something error, Please try again later"
            signUpViewModel.changeErrorMessage(errorMessage)
        }
    }

    companion object {
        private const val TAG = "SignUpFragment"
    }

    override fun onClick(view: View?) {
        when (view ?: return) {
            fragmentSignUpBinding?.btnSignUp -> onClickSignUp()
            fragmentSignUpBinding?.ibSignWithGoogle -> onClickSignWithGoogle()
            fragmentSignUpBinding?.tvSignIn -> onClickSignIn()
        }
    }

    private fun onClickSignUp() {
        val errorMessage: String
        val userName = fragmentSignUpBinding?.etUsername?.text?.toString() ?: ""
        val phoneNumber = fragmentSignUpBinding?.etPhoneNumber?.text?.toString() ?: ""
        val password = fragmentSignUpBinding?.etPassword?.text?.toString() ?: ""
        val confirmedPassword = fragmentSignUpBinding?.etConfirmPassword?.text?.toString() ?: ""
        val language = "ar"

        val acceptTermStatus = fragmentSignUpBinding?.cbAcceptTerms?.isChecked ?: false

        if (userName.isEmpty() || userName.isBlank() || userName.length < 4) {
            errorMessage = "Invalid Username"
            fragmentSignUpBinding?.tilUsername?.error = errorMessage
            return
        }

        if (phoneNumber.isEmpty() || phoneNumber.isBlank() || phoneNumber.length < 6) {
            errorMessage = "Invalid Phone number"
            fragmentSignUpBinding?.tilPhoneNumber?.error = errorMessage
            return
        }

        if (password != confirmedPassword) {
            errorMessage = "Password doesn't match"
            fragmentSignUpBinding?.tilPassword?.error = errorMessage
            fragmentSignUpBinding?.tilConfirmPassword?.error = errorMessage
            return
        } else if (password.isEmpty() || password.isBlank() || password.length < 6) {
            errorMessage = "Please fill Password"
            fragmentSignUpBinding?.tilPassword?.error = errorMessage
            return
        }

        if (!acceptTermStatus) {
            //Show Dialog to make user Accept the terms
            return
        }

        this.phoneNumber = phoneNumber
        //Show Loading Dialog
        signUpViewModel.signUpRequest(userName, phoneNumber, password, language)
    }


    private fun onClickSignWithGoogle() {

    }

    private fun onClickSignIn() {
        findNavController().popBackStack()
    }
}