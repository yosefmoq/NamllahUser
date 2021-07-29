package com.app.namllahuser.presentation.fragments.pages.aboutus

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
import com.app.namllahuser.databinding.AboutUsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : Fragment() {

    val aboutUsViewModel:AboutUsViewModel by viewModels()
    lateinit var aboutUsFragmentBinding: AboutUsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutUsFragmentBinding = AboutUsFragmentBinding.inflate(inflater,container,false)

        return aboutUsFragmentBinding.apply {
            aboutUsFragmentBinding.include5.tvToolbarText.text = getString(R.string.aboutUs)
            aboutUsFragmentBinding.include5.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val metadataData = aboutUsViewModel.getMetaData()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                aboutUsFragmentBinding.tvAboutText.setText(Html.fromHtml(metadataData.about_us_en, Html.FROM_HTML_MODE_COMPACT));
            } else {
                aboutUsFragmentBinding.tvAboutText.setText(Html.fromHtml(metadataData.about_us_en));
            }

        }.root
    }

}