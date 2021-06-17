package com.app.namllahuser.presentation.fragments.main.profile.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.model.order.User
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.databinding.FragmentProfileBinding
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.presentation.MainActivity
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.utils.DialogUtils
import com.google.android.gms.common.util.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {
    lateinit var dialogUtils: DialogUtils
    lateinit var fragmentProfileBinding: FragmentProfileBinding
    lateinit var userDto: UserDto
    val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

        return fragmentProfileBinding.root.also {
            dialogUtils = DialogUtils(requireActivity())

            fragmentProfileBinding.onClick = this@ProfileFragment
            fragmentProfileBinding.include3.ivToolbarBack.visibility = View.GONE
            fragmentProfileBinding.include3.tvToolbarText.text = getString(R.string.profile)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        profileViewModel.logoutLiveData.observe(viewLifecycleOwner, Observer {
//            if(it.status!!){
            logout()
            //            }
        })
        profileViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        profileViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            logout()
//            dialogUtils.showFailAlert(it)
        })
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentProfileBinding.tvProAboutUs -> aboutUsHandle()
            fragmentProfileBinding.tvProContactUs -> contactUsHandle()
            fragmentProfileBinding.tvProEdit -> editProfileHandle()
            fragmentProfileBinding.tvProPrivacy -> privacyPolicyHandle()
            fragmentProfileBinding.tvProTerm -> termConditionHandle()
            fragmentProfileBinding.tvProSignout -> signoutHandle()

        }
    }

    private fun signoutHandle() {
        profileViewModel.logout()

    }

    private fun termConditionHandle() {

    }

    private fun privacyPolicyHandle() {

    }

    private fun editProfileHandle() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToEditProfileFragment())

    }

    private fun contactUsHandle() {

    }

    private fun aboutUsHandle() {

    }

    override fun onResume() {
        super.onResume()
        userDto =
            SharedVariables(requireContext()).getObjectFromSharedVariable<UserDto>(SharedValueFlags.USER)!!

        fragmentProfileBinding.name = userDto.name
        fragmentProfileBinding.location = "Palestine, Gaza"
        fragmentProfileBinding.imageUrl = userDto.images.med
        fragmentProfileBinding.executePendingBindings()
    }

    fun logout() {
        SharedVariables(requireContext()).clearAllData()
        requireActivity().finish()
        requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))

    }
}