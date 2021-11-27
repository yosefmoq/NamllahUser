package com.app.namllahuser.presentation.fragments.pages.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    lateinit var fragmentSettingBinding: FragmentSettingBinding
     val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val lang = if(viewModel.getLang() == "en"){
            1
        }else{
            2
        }
        findNavController(requireParentFragment()).navigate(SettingsFragmentDirections.actionSettingFragmentToLanguageListBottomSheetFragment(lang))
    }


}