package com.app.namllahuser.presentation.fragments.pages.contactus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.namllahuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactUsFragment()
    }

    private lateinit var viewModel: ContactUsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_us_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactUsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}