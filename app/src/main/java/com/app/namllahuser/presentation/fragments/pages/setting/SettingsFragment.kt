package com.app.namllahuser.presentation.fragments.pages.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.databinding.FragmentSettingBinding
import com.app.namllahuser.domain.SharedValueFlags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    lateinit var fragmentSettingBinding: FragmentSettingBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater,container,false)


        return fragmentSettingBinding.root.apply {
            fragmentSettingBinding.onClick = this@SettingsFragment
            fragmentSettingBinding.include5.tvToolbarText.text = getString(R.string.settings)
            fragmentSettingBinding.include5.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
//        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onClick(v: View?) {
        findNavController(requireParentFragment()).navigate(SettingsFragmentDirections.actionSettingFragmentToLanguageListBottomSheetFragment(1))
    }


}