package com.app.namllahuser.presentation.fragments.selectPayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.R
import com.app.namllahuser.data.model.AOrderModel
import com.app.namllahuser.data.model.BillsData
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.FragmentSelectPaymentMethodBinding
import com.app.namllahuser.presentation.fragments.main.adapter.BillsAdapter
import com.app.namllahuser.presentation.utils.DialogUtils
import com.app.namllahuser.presentation.utils.toHours
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPaymentMethodFragment : Fragment(), View.OnClickListener {

    lateinit var fragmentSelectPaymentMethodBinding: FragmentSelectPaymentMethodBinding
    val selectPaymentMethodViewModel: SelectPaymentMethodViewModel by viewModels()
    lateinit var order: OrderDto
    var bills: MutableList<BillsData> = mutableListOf()
    lateinit var adapter: BillsAdapter
    lateinit var dialogUtils: DialogUtils
    var orderId: Long = 0
    var amount:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSelectPaymentMethodBinding =
            FragmentSelectPaymentMethodBinding.inflate(inflater, container, false)
        return fragmentSelectPaymentMethodBinding.apply {
            arguments.let {
                order = SelectPaymentMethodFragmentArgs.fromBundle(it!!).order
            }
            this.include12.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            this.include12.tvToolbarText.text = getString(R.string.bill)
            adapter = BillsAdapter(requireContext(), bills)
            rvBills.adapter = adapter
            rvBills.layoutManager =
                object : LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                        lp!!.width = width / 3
                        return true

                    }
                }
            fragmentSelectPaymentMethodBinding.onClick = this@SelectPaymentMethodFragment
            dialogUtils = DialogUtils(requireActivity())
        }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectPaymentMethodViewModel.getOrder(order.id!!.toInt())
        observeData()
    }

    private fun observeData() {
        selectPaymentMethodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })

        selectPaymentMethodViewModel.orderMutableData.observe(viewLifecycleOwner, Observer {
            handleOrder(it)
        })
        selectPaymentMethodViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        selectPaymentMethodViewModel.payOrderMutableData.observe(viewLifecycleOwner, Observer {
            if(it.status!!){
                dialogUtils.showSuccessAlert(it.msg!!)
                findNavController().popBackStack()

            }else{
                dialogUtils.showFailAlert(it.msg!!)
            }
        })
    }

    private fun handleOrder(aOrderModel: AOrderModel) {
        if (aOrderModel.status!!) {
            fragmentSelectPaymentMethodBinding.orderDto = aOrderModel.data
            fragmentSelectPaymentMethodBinding.boughtPrice =
                "${aOrderModel.data.bring_times * 9} SR"
            fragmentSelectPaymentMethodBinding.hours = aOrderModel.data.duration.toLong().toHours()
            fragmentSelectPaymentMethodBinding.executePendingBindings()
            adapter.update(aOrderModel.data.bills!!)
            orderId = aOrderModel.data.id!!
            amount  = aOrderModel.data.total_price_floor

            if(aOrderModel.data.payment.id==0){
                fragmentSelectPaymentMethodBinding.cvPaymentMethod.visibility = View.VISIBLE
                fragmentSelectPaymentMethodBinding.button.visibility = View.VISIBLE
            }else{
                fragmentSelectPaymentMethodBinding.cvPaymentMethod.visibility = View.GONE
                fragmentSelectPaymentMethodBinding.button.visibility = View.GONE

            }

        } else {
            dialogUtils.showFailAlert(aOrderModel.msg!!)
        }

    }

    override fun onClick(v: View?) {
        if (v == fragmentSelectPaymentMethodBinding.button) {
            selectPaymentMethodViewModel.payOrder(orderId,1)
        }
    }


}