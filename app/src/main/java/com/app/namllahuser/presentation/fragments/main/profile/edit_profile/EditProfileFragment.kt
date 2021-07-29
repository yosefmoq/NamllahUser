package com.app.namllahuser.presentation.fragments.main.profile.edit_profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.PasswordBottomSheetData
import com.app.namllahuser.data.model.PhoneBottomSheetData
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.model.UsernameBottomSheetData
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.databinding.FragmentEditProfileBinding
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePassword.ChangePasswordFragment
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePhone.ChangePhoneNumberFragment
import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changeUsername.ChangeUsernameFragment
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class EditProfileFragment : Fragment(), ChangeUsernameFragment.OnUserNameClick,
    ChangePasswordFragment.ChangePasswordClick, ChangePhoneNumberFragment.OnPhoneNumberClick,
    View.OnClickListener/*, EasyPermissions.PermissionCallbacks*/ {
    private val SELECT_PHOTO: Int = 143

    lateinit var fragmentEditProfileBinding: FragmentEditProfileBinding
    lateinit var userDtoA: UserDto
    lateinit var dialogUtils: DialogUtils
    val editProfileVM: EditProfileVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return fragmentEditProfileBinding.apply {
            actionOnClick = this@EditProfileFragment
            notifyChanges()

            fragmentEditProfileBinding.include4.tvToolbarText.text = getString(R.string.editProfile)
            dialogUtils = DialogUtils(requireActivity())
            fragmentEditProfileBinding.include4.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }.root
    }

    companion object {
        private const val TAG = "EditProfileFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        editProfileVM.updateProfileLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status!!) {
                SharedVariables(requireContext()).setObjectInSharedVariable(SharedValueFlags.USER,it.userDto!!)
                notifyChanges()
                dialogUtils.showSuccessAlert(getString(R.string.update_profile))
            } else
                dialogUtils.showFailAlert(it.msg!!)
        })
        editProfileVM.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        editProfileVM.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
    }

    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentEditProfileBinding.etUserName -> {
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangeUsernameFragment(
                        username = UsernameBottomSheetData(userDtoA.name, this@EditProfileFragment)
                    )
                )
            }
            fragmentEditProfileBinding.etPassword -> {
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangePasswordFragment(
                        PasswordBottomSheetData(this@EditProfileFragment)
                    )
                )
            }
            fragmentEditProfileBinding.etPhoneNumber -> {
                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToChangePhonenumberFragment(
                        phoneNumber = PhoneBottomSheetData(
                            userDtoA.mobile,
                            this@EditProfileFragment
                        )
                    )
                )

            }
            fragmentEditProfileBinding.circleImageView -> {

                // Ask for one permission
                Log.v("ttt", "done")
                selectImage()

            }
        }
    }


/*
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        selectImage()
    }
*/


    fun selectImage() {
        if (hasStoragePermission()) {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            someActivityResultLauncher!!.launch(intent)
        } else {
            Log.v("ttt", "done2")

            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.take_image),
                SELECT_PHOTO,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        /* Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/ *");
            startActivityForResult(intent, RESULT_LOAD_IMAGE);*/

    }

    private val someActivityResultLauncher: ActivityResultLauncher<Intent?>? =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            androidx.activity.result.ActivityResultCallback<androidx.activity.result.ActivityResult> { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent = result.getData()!!
                    fragmentEditProfileBinding.circleImageView.setImageURI(data.data)
                    val realPath: String = com.app.namllahuser.presentation.utils.FileUtils()
                        .getPath(requireContext(), data.data!!)!!
                    val requestBody: RequestBody = RequestBody.create(
                        "image/*".toMediaTypeOrNull(),
                        java.io.File(realPath)
                    )

                    editProfileVM.changeImage(requestBody)
                    Log.v("ttt", realPath)
                }
            })

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onResume() {
        super.onResume()
        notifyChanges()
    }

    fun notifyChanges() {
        userDtoA =
            SharedVariables(requireContext()).getObjectFromSharedVariable<UserDto>(
                SharedValueFlags.USER
            )!!;
        fragmentEditProfileBinding.userDto = userDtoA
        fragmentEditProfileBinding.executePendingBindings()

    }

    override fun onClick(name: String) {
        editProfileVM.changeUserName(name)
    }

    override fun onChangePasswordClick(oldPass: String, pass: String) {
        editProfileVM.changePassword(oldPass, pass)
    }

    override fun onPhoneNumberSave(phone: String) {
        editProfileVM.changeMobile(phone)
    }
}





