package com.app.namllahuser.presentation.fragments.createOrder.finishOrder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.CreateOrderRequest
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.databinding.FragmentFinishOrderBinding
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.utils.DialogUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishOrderFragment : Fragment(),View.OnClickListener {
    lateinit var createOrderRequest: CreateOrderRequest
    private val viewModel: FinishOrderViewModel by viewModels()
    private lateinit var fragmentFinishOrderBinding:FragmentFinishOrderBinding
    private lateinit var dialogUtils: DialogUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFinishOrderBinding = FragmentFinishOrderBinding.inflate(inflater,container,false)

        return fragmentFinishOrderBinding.root.apply {
            dialogUtils = DialogUtils(requireActivity());
            fragmentFinishOrderBinding.myNumber = "${SharedVariables(requireContext()).getObjectFromSharedVariable<UserDto>(SharedValueFlags.USER)!!.mobile}"
            arguments.let {
                createOrderRequest = FinishOrderFragmentArgs.fromBundle(it!!).finishOrderRequest
                fragmentFinishOrderBinding.finishOrderRequest = createOrderRequest
                fragmentFinishOrderBinding.include9.ivToolbarBack.setOnClickListener {
                    findNavController().popBackStack()
                }
                fragmentFinishOrderBinding.include9.tvToolbarText.text = getString(R.string.finishOrder)
            }
            fragmentFinishOrderBinding.onClick = this@FinishOrderFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(OnMapReadyCallback {
            val sydney = LatLng(createOrderRequest.lat,createOrderRequest.lng)
            it.addMarker(MarkerOptions().position(sydney).title("My Location"))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,10f))
        })

    }
    fun observeData(){
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        viewModel.postOrder.observe(viewLifecycleOwner, Observer {
            if(it.status!!){
                requireActivity().finish()
                requireActivity().startActivity(HomeActivity.getIntent(requireActivity(),2))
            }else{
                dialogUtils.showFailAlert(it.msg!!)
            }
        })

    }

    override fun onClick(v: View?) {
        viewModel.postOrder(createOrderRequest)
    }


}