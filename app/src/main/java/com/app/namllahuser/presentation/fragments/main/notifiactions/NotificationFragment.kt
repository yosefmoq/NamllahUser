package com.app.namllahuser.presentation.fragments.main.notifiactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.namllahuser.R
import com.app.namllahuser.data.model.NotificationDto
import com.app.namllahuser.databinding.FragmentNotificationBinding
import com.app.namllahuser.presentation.fragments.main.adapter.NotificationAdapter
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private val notificationViewModel: NotificationViewModel by viewModels()
    lateinit var dialogUtils: DialogUtils
    lateinit var fragmentNotificationBinding: FragmentNotificationBinding
    lateinit var notificationAdapter: NotificationAdapter
    val notificationData = mutableListOf<NotificationDto>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater,container,false)
        return fragmentNotificationBinding.root.also {
            fragmentNotificationBinding.include2.tvToolbarText.text = getString(R.string.notifications)
            fragmentNotificationBinding.include2.ivToolbarBack.visibility = View.GONE
            notificationAdapter = NotificationAdapter(requireContext(),notificationData)
            fragmentNotificationBinding.rvNotification.layoutManager = LinearLayoutManager(context)
            fragmentNotificationBinding.rvNotification.adapter = notificationAdapter
            dialogUtils = DialogUtils(requireActivity())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationViewModel.getNotification()
        observeData()
    }

    fun observeData(){
        notificationViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        notificationViewModel.notificationLiveData.observe(viewLifecycleOwner, Observer {
            notificationAdapter.update(it.data)
        })
        notificationViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
    }
    companion object {
        private const val TAG = "NotificationFragment"
    }
}