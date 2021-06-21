package com.app.namllahuser.presentation.fragments.main.home.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.namllahuser.R
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.OrdersFragmentBinding
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.fragments.main.adapter.OrdersAdapter
import com.app.namllahuser.presentation.listeners.OnOrderClickListener
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(), OnOrderClickListener {

    private lateinit var orderFragmentBinding: OrdersFragmentBinding
    private val ordersViewModel: OrdersViewModel by viewModels()
    private lateinit var dialogUtils: DialogUtils
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersData: MutableList<OrderDto> = mutableListOf();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderFragmentBinding = OrdersFragmentBinding.inflate(inflater, container, false)

        return orderFragmentBinding.root.apply {
            dialogUtils = DialogUtils(requireActivity())
            ordersAdapter = OrdersAdapter(requireContext(), ordersData, this@OrdersFragment)
            orderFragmentBinding.include10.tvToolbarText.text = getString(R.string.orders)
            orderFragmentBinding.include10.ivToolbarBack.visibility = View.GONE
            orderFragmentBinding.rvOrders.adapter = ordersAdapter
            orderFragmentBinding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
            ordersViewModel.getOrders()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData();
    }

    fun observeData() {
        ordersViewModel.orderLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status!!) {
                ordersAdapter.update(it.data)
            } else {
                ordersViewModel.changeErrorMessage(it.msg!!)
            }
        })

        ordersViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        ordersViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
    }

    override fun onOrderClick(orderDto: OrderDto) {
        Toast.makeText(requireContext(), orderDto.cancel_reason_id.toString(), Toast.LENGTH_SHORT)
            .show()
        when (orderDto.status.id) {
            1,2,3,4,5,6,7 -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToOrderStatusFragment(orderDto.status.id.toInt(),orderDto))
            }
            8->{

            }
        }
    }

}