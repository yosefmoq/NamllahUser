package com.app.namllahuser.presentation.fragments.main.profile.profile

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.namllahuser.databinding.FragmentLanguageListBottomSheetBinding
import com.app.namllahuser.presentation.MainActivity
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.utils.DialogUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class LanguageListBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    lateinit var dialogUtils: DialogUtils
    private var fragmentUserEditBottomSheetBinding: FragmentLanguageListBottomSheetBinding? = null

    private var currentLanguage: Int = -1
    var newLanguage = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogUtils = DialogUtils(requireActivity())
            currentLanguage = LanguageListBottomSheetFragmentArgs.fromBundle(it).currentLanguage
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentUserEditBottomSheetBinding =
            FragmentLanguageListBottomSheetBinding.inflate(inflater, container, false)
        return fragmentUserEditBottomSheetBinding?.apply {
            actionOnClick = this@LanguageListBottomSheetFragment
            Timber.tag(TAG).d("onCreateView : currentLanguage ${this@LanguageListBottomSheetFragment.currentLanguage}")
            currentLanguage = this@LanguageListBottomSheetFragment.currentLanguage

        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }


    private fun observeLiveData() {
        profileViewModel.loadingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            it?.let {
                Timber.tag(TAG).d("observeLiveData : Loading Status $it")
                dialogUtils.loading(it)
                profileViewModel.loadingLiveData.postValue(null)
            }
        })

        profileViewModel.errorLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            dialogUtils.showFailAlert(it)
        })

        profileViewModel.updateProfileLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            it?.let { updateUserProfileResponse ->
                Timber.tag(TAG)
                    .d("observeLiveData : updateUserProfileResponse $updateUserProfileResponse")
                if (updateUserProfileResponse.status!!) {
                    startActivity(Intent(requireContext(),MainActivity::class.java))
                    requireActivity().finish()
/*
                    com.app.namllahuser.presentation.base.ContextUtils.updateLocale(requireContext(), Locale(newLanguage))

                    startActivity(HomeActivity.getIntent(requireActivity(),0,null))
                    requireActivity().finish()
*/
                    dismiss()
                } else {
                    val errorMessage = updateUserProfileResponse.msg
                        ?: updateUserProfileResponse.error
                        ?: "Something error, Please try again later"
                    profileViewModel.changeErrorMessage(Throwable(errorMessage))
                }
                profileViewModel.updateProfileLiveData.postValue(null)
            }
        })
    }


    override fun onClick(v: View?) {
        when (v ?: return) {
            fragmentUserEditBottomSheetBinding?.ibClose -> onClickClose()
            fragmentUserEditBottomSheetBinding?.btnSave -> onClickSave()
        }
    }

    private fun onClickClose() {
        dismiss()
    }

    private fun onClickSave() {
        newLanguage = when (fragmentUserEditBottomSheetBinding?.rgLanguages?.checkedRadioButtonId) {
            fragmentUserEditBottomSheetBinding?.rbArabicLanguage?.id -> {
                "ar"
            }
            fragmentUserEditBottomSheetBinding?.rbEnglishLanguage?.id -> {
                "en"
            }
            else -> {
                "en"
            }
        }
        Timber.tag(TAG).d("onClickSave : newLanguage $newLanguage")
        profileViewModel.changeLanguage(newLanguage)
    }

    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    companion object {
        private const val TAG = "LanguageListBottomSheet"
    }
}