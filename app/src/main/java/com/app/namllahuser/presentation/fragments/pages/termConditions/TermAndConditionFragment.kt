package com.app.namllahuser.presentation.fragments.pages.termConditions

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
import com.app.namllahuser.databinding.TermAndConditionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermAndConditionFragment : Fragment() {

    val termAndConditionViewModel:TermAndConditionViewModel by viewModels()
    lateinit var termAndConditionFragmentBinding: TermAndConditionFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        termAndConditionFragmentBinding =  TermAndConditionFragmentBinding.inflate(inflater, container, false)

        return termAndConditionFragmentBinding.apply {
            termAndConditionFragmentBinding.include7.tvToolbarText.text = getString(R.string.term)
            termAndConditionFragmentBinding.include7.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val metadataData = termAndConditionViewModel.getMetadata()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                termAndConditionFragmentBinding.tvTermText.setText(Html.fromHtml(metadataData.term_and_condition_en, Html.FROM_HTML_MODE_COMPACT));
            } else {
                termAndConditionFragmentBinding.tvTermText.setText(Html.fromHtml(metadataData.term_and_condition_en));
            }

        }
            .root
    }


}