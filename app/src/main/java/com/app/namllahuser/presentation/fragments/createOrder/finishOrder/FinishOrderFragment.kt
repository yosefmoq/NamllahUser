package com.app.namllahuser.presentation.fragments.createOrder.finishOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.namllahuser.R

class FinishOrderFragment : Fragment() {

    companion object {
        fun newInstance() = FinishOrderFragment()
    }

    private lateinit var viewModel: FinishOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finish_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinishOrderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}