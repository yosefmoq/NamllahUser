package com.app.namllahuser.presentation.fragments.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.namllahuser.R
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.databinding.FragmentHomeBinding
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.fragments.main.adapter.MainCategoryAdapter
import com.app.namllahuser.presentation.listeners.OnCategoryClickListeners
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),OnCategoryClickListeners {

    lateinit var fragmentHomeBinding:FragmentHomeBinding
    private val homeFragmentMV:HomeFragmentMV by viewModels()
    private val servicesList = mutableListOf<ServiceDto>()
    private lateinit var mainCategoryAdapter:MainCategoryAdapter
    lateinit var dialogUtils: DialogUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return fragmentHomeBinding.root.apply {
            homeFragmentMV.getServices()
            dialogUtils = DialogUtils(requireActivity())
            mainCategoryAdapter = MainCategoryAdapter(servicesList,this@HomeFragment)
            fragmentHomeBinding.rvMainCategory.layoutManager = GridLayoutManager(requireContext(),3)
            fragmentHomeBinding.rvMainCategory.adapter = mainCategoryAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentMV.serviceLiveData.observe(viewLifecycleOwner, Observer {
            handleServices(it)
        })
        homeFragmentMV.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        homeFragmentMV.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
    }

    private fun handleServices(serviceResponse:ServiceResponse) {
        mainCategoryAdapter.update(serviceResponse.data.data)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCategoryClick(serviceDto: ServiceDto) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToSelectLocationFragment(serviceDto))

    }
}