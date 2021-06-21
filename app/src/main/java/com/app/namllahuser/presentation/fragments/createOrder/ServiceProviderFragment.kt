package com.app.namllahuser.presentation.fragments.createOrder

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
import com.app.namllahuser.data.model.CreateOrderRequest
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.data.model.ServiceProviderDto
import com.app.namllahuser.databinding.FragmentServiceProviderBinding
import com.app.namllahuser.presentation.fragments.main.adapter.ServiceProvidersAdapter
import com.app.namllahuser.presentation.listeners.OnServiceProvidersClickListeners
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceProviderFragment : Fragment(), OnServiceProvidersClickListeners {

    companion object {
        fun newInstance() = ServiceProviderFragment()
    }

    private lateinit var serviceDto: ServiceDto
    private var lat: Double? = 0.0
    private var lng: Double? = 0.0
    private lateinit var fragmentServiceProviderBinding: FragmentServiceProviderBinding
    private val serviceViewModel: ServiceViewModel by viewModels()
    private lateinit var dialogUtils: DialogUtils
    private val serviceProviderData: MutableList<ServiceProviderDto> = mutableListOf()
    lateinit var serviceProviderAdapter: ServiceProvidersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentServiceProviderBinding =
            FragmentServiceProviderBinding.inflate(layoutInflater, container, false)

        return fragmentServiceProviderBinding.root.also {
            dialogUtils = DialogUtils(requireActivity())
            serviceProviderAdapter =
                ServiceProvidersAdapter(serviceProviderData, this@ServiceProviderFragment)
            fragmentServiceProviderBinding.rvServiceProviders.layoutManager =
                LinearLayoutManager(requireContext())
            fragmentServiceProviderBinding.rvServiceProviders.adapter = serviceProviderAdapter
            fragmentServiceProviderBinding.include11.tvToolbarText.text =
                getString(R.string.serviceProviders)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData();
        arguments.also {
            lng = ServiceProviderFragmentArgs.fromBundle(it!!).lng.toDouble()
            lat = ServiceProviderFragmentArgs.fromBundle(it).lat.toDouble()
            serviceDto = ServiceProviderFragmentArgs.fromBundle(it).service
        }
        serviceViewModel.getNearServiceProviders(
            lat = lat!!,
            lng = lng!!,
            serviceId = serviceDto.id.toInt()
        )
    }

    fun observeData() {
        serviceViewModel.serviceProviders.observe(viewLifecycleOwner, Observer {
            if (it.status!!) {
                it.data.forEach {
                    it.category = serviceDto.title
                }
                serviceProviderAdapter.update(it.data)

            } else {
                dialogUtils.showFailAlert(it.msg!!)
            }
        })
        serviceViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        serviceViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
    }


    override fun onServiceProvidersClick(serviceProviderDto: ServiceProviderDto) {
        findNavController().navigate(
            ServiceProviderFragmentDirections.actionServiceProviderFragmentToFinishOrderFragment(
                CreateOrderRequest(
                    serviceDto.id,
                    serviceProviderDto.id!!,
                    serviceDto.title,
                    serviceProviderDto.name!!,
                    lat!!,
                    lng!!
                )
            )
        )
    }
}