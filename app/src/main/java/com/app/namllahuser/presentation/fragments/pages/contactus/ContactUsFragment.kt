package com.app.namllahuser.presentation.fragments.pages.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentContactUsBinding
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : Fragment(),View.OnClickListener {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val contactUsViewModel:ContactUsViewModel by viewModels()
    lateinit var dialogUtils:DialogUtils
    lateinit var fragmentContactUsBinding: FragmentContactUsBinding
    companion object {
        fun newInstance() = ContactUsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentContactUsBinding = FragmentContactUsBinding.inflate(inflater,container,false)

        return fragmentContactUsBinding.apply {
            include6.tvToolbarText.text = getString(R.string.contactUs)
            include6.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            onClick = this@ContactUsFragment
            dialogUtils = DialogUtils(requireActivity())
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

    }

    private fun observeData() {
        contactUsViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        contactUsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        contactUsViewModel.contactUsMutableData.observe(viewLifecycleOwner, Observer {
            if(it.status!!){
                dialogUtils.showSuccessAlert(it.msg!!)
                findNavController().popBackStack()
            }else{
                dialogUtils.showFailAlert(it.msg!!)
                findNavController().popBackStack()
            }
        })


    }

    override fun onClick(v: View?) {
        when(v){
            fragmentContactUsBinding.btnContactSend-> handleSendContact()
        }
    }

    private fun handleSendContact() {
        val email:String = fragmentContactUsBinding.etContactEmail.text.toString()
        val message:String? = fragmentContactUsBinding.etContactMessage.text.toString()
        if(!email.matches(Regex(emailPattern))){
            fragmentContactUsBinding.etContactEmail.error = "Enter valid email"
        }else if(message.isNullOrEmpty()){
            fragmentContactUsBinding.etContactMessage.error = "The message must not be empty"
        }else{
            contactUsViewModel.contactUs(email,message)
        }
    }

}