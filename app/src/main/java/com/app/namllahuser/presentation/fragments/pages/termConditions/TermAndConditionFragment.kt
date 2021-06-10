package com.app.namllahuser.presentation.fragments.pages.termConditions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.namllahuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermAndConditionFragment : Fragment() {

    companion object {
        fun newInstance() = TermAndConditionFragment()
    }

    private lateinit var viewModel: TermAndConditionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.term_and_condition_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TermAndConditionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}