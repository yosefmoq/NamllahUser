package com.app.namllahuser.presentation.fragments.pages.privacyPolicy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.namllahuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : Fragment() {

    companion object {
        fun newInstance() =
            PrivacyPolicyFragment()
    }

    private lateinit var viewModel: PrivacyPolicyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.privacy_policy_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrivacyPolicyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}