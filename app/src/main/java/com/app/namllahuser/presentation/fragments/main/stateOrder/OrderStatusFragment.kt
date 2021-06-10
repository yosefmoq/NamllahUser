package com.app.namllahuser.presentation.fragments.main.stateOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.namllahuser.R
import com.shuhart.stepview.StepView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatusFragment : Fragment() {
    companion object {
        fun newInstance() = OrderStatusFragment()
    }

    private lateinit var viewModel: OrderStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_order_status, container, false)
        return v;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderStatusViewModel::class.java)
        // TODO: Use the ViewModel
    }

}