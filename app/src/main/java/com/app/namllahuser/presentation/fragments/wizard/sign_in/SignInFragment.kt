package com.app.namllahuser.presentation.fragments.wizard.sign_in

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
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
import com.app.namllahuser.R
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.databinding.FragmentSignInBinding
import com.app.namllahuser.domain.Constants.RESEND_TYPE_VARIFY
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.service.MyFirebaseInstanceIDService
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment(), View.OnClickListener {
    lateinit var dialogUtils:DialogUtils
    private val signInViewModel: SignInViewModel by viewModels()
    private var fragmentSignInBinding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return fragmentSignInBinding?.apply {
            actionOnClick = this@SignInFragment
            dialogUtils =DialogUtils(requireActivity())
            MyFirebaseInstanceIDService.getObservable().map {
                Log.v("ttt",it)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

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
                fragmentSignInBinding?.tilPhoneNumber?.error = null
                fragmentSignInBinding?.tilPassword?.error = null
            }
        }
        fragmentSignInBinding?.etPhoneNumber?.addTextChangedListener(onTextChanged)
        fragmentSignInBinding?.etPassword?.addTextChangedListener(onTextChanged)
    }

    private fun initToolbar() {
        val toolbar = fragmentSignInBinding?.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar?.root)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolBarTitleView = toolbar?.toolbarTitle
        toolBarTitleView?.text = getString(R.string.login_to_your_account)

        toolbar?.root?.navigationIcon = null
    }

    private fun observeLiveData() {

        signInViewModel.loadingLiveData.observe(requireActivity(),Observer{
            dialogUtils.loading(it)
        })

        signInViewModel.errorLiveData.observe(viewLifecycleOwner , Observer{
                dialogUtils.showFailAlert(it)

        })

        signInViewModel.signInLiveData.observe(viewLifecycleOwner, Observer{
            it?.let {
                handleSignInResponse(it)
                //To Stop Livedata
//                signInViewModel.signInLiveData.postValue(null)
            }
        })
    }

    private fun handleSignInResponse(signInResponse: SignInResponse) {
        if(signInResponse.status!!){
            if (signInResponse.userDto != null) {
                signInViewModel.saveUserDataLocal(signInResponse.userDto!!)
                signInViewModel.saveToken(signInResponse.userDto!!.token)
                signInViewModel.changeLoginStatus(true)
                startActivity(HomeActivity.getIntent(requireActivity(),1,null))
                requireActivity().finishAffinity()
            } else {
                val errorMessage = signInResponse.error ?: signInResponse.msg
                ?: "Something error, Please try again later"
                Log.v("ttt",errorMessage)
                signInViewModel.changeErrorMessage(errorMessage)
            }
        }else{

            if(signInResponse.error == "auth.not_verified"){
                Log.d(TAG, "handleSignInResponse: auth ${signInResponse.error}")
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToVerificationCodeFragment(phoneNumber = fragmentSignInBinding!!.etPhoneNumber.text.toString(),type = RESEND_TYPE_VARIFY  ))
                fragmentSignInBinding!!.etPhoneNumber.setText("")
                fragmentSignInBinding!!.etPassword.setText("")

            }else{
                dialogUtils.showFailAlert(signInResponse.error!!)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentSignInBinding?.tvForgetPassword -> onClickForgetPassword()
            fragmentSignInBinding?.btnSignIn -> onClickSignIn()
            fragmentSignInBinding?.ibSignWithGoogle -> onClickSignWithGoogle()
            fragmentSignInBinding?.tvSignUp -> onClickSignUp()
        }
    }

    private fun onClickForgetPassword() {
        findNavController().navigate(R.id.action_signInFragment_to_forgetPasswordFragment)
    }

    private fun onClickSignIn() {
        val errorMessage: String
        val phoneNumber = fragmentSignInBinding?.etPhoneNumber?.text?.toString() ?: ""
        val password = fragmentSignInBinding?.etPassword?.text?.toString() ?: ""

        if (phoneNumber.isEmpty() || phoneNumber.isBlank() || phoneNumber.length < 6) {
            errorMessage = "Invalid Phone number"
            fragmentSignInBinding?.tilPhoneNumber?.error = errorMessage
            return
        }

        if (password.isEmpty() || password.isBlank() || password.length < 6) {
            errorMessage = "Please fill Password"
            fragmentSignInBinding?.tilPassword?.error = errorMessage
            return
        }
        //Show Loading Dialog
        Log.v("ttt","signin")
        signInViewModel.signInRequest(phoneNumber, password)
    }

    private fun onClickSignWithGoogle() {

    }

    private fun onClickSignUp() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    companion object {
        private const val TAG = "SignInFragment"
    }

}