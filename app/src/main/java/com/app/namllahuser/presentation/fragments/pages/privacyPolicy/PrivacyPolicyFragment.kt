package com.app.namllahuser.presentation.fragments.pages.privacyPolicy

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.databinding.PrivacyPolicyFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : Fragment() {

    val privacyPolicyViewModel:PrivacyPolicyViewModel by viewModels()
    lateinit var fragmentPrivacyPolicyFragmentBinding: PrivacyPolicyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPrivacyPolicyFragmentBinding = PrivacyPolicyFragmentBinding.inflate(inflater,container,false)

        return fragmentPrivacyPolicyFragmentBinding.apply {
            val metadataData = privacyPolicyViewModel.getMetaData()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentPrivacyPolicyFragmentBinding.tvTermText.setText(Html.fromHtml(metadataData.privacy_en, Html.FROM_HTML_MODE_COMPACT));
            } else {
                fragmentPrivacyPolicyFragmentBinding.tvTermText.setText(Html.fromHtml(metadataData.privacy_en));
            }
            fragmentPrivacyPolicyFragmentBinding.include8.tvToolbarText.text = getString(R.string.privacy)
            fragmentPrivacyPolicyFragmentBinding.include8.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }.root
    }


}