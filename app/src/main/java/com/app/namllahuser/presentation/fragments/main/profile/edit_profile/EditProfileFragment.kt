package com.app.namllahuser.presentation.fragments.main.profile.edit_profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.databinding.FragmentEditProfileBinding
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePassword.ChangePasswordFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() , View.OnClickListener {
    lateinit var fragmentEditProfileBinding: FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return fragmentEditProfileBinding.apply {
            actionOnClick = this@EditProfileFragment
        }.root
    }

    companion object {
        private const val TAG = "EditProfileFragment"
    }

    override fun onClick(v: View?) {
        when(v ?: return){
            fragmentEditProfileBinding.etUserName->{
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangeUsernameFragment()
                )
            }
            fragmentEditProfileBinding.etPassword->{
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangePasswordFragment()
                )
            }
            fragmentEditProfileBinding.etPhoneNumber->{
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangePhonenumberFragment()
                )

            }
            fragmentEditProfileBinding.circleImageView->{
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangePasswordFragment()
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Log.v("ttt","editProfileResume")
    }
}




