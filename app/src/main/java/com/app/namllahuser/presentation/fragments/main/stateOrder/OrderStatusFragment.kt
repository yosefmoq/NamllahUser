package com.app.namllahuser.presentation.fragments.main.stateOrder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.FragmentOrderStatusBinding
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


@AndroidEntryPoint
class OrderStatusFragment : Fragment() ,EasyPermissions.PermissionCallbacks{
    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding
    lateinit var orderDto:OrderDto
    val MY_PERMISSIONS_REQUEST_LOCATION = 13

    lateinit var dialogUtils:DialogUtils
     var status:Int = 0
    companion object {
        fun newInstance() = OrderStatusFragment()
    }


    private  val  viewModel: OrderStatusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentOrderStatusBinding = FragmentOrderStatusBinding.inflate(inflater, container, false);
        return fragmentOrderStatusBinding.root.apply {
            fragmentOrderStatusBinding.include.tvToolbarText.text = getString(R.string.trackOrder)
            fragmentOrderStatusBinding.include.ivToolbarMenu.visibility = View.VISIBLE
            dialogUtils = DialogUtils(requireActivity())
            fragmentOrderStatusBinding.include.ivToolbarMenu.setOnClickListener {
                val popup = PopupMenu(requireContext(), fragmentOrderStatusBinding.include.ivToolbarMenu)
                popup.menuInflater.inflate(R.menu.cancel_menu,popup.menu)
                popup.setOnMenuItemClickListener {
                    viewModel.cancelOrder(orderDto.id!!.toInt(),1,"too late")

                    return@setOnMenuItemClickListener  true
                }
                popup.show()

            }
            fragmentOrderStatusBinding.include
            fragmentOrderStatusBinding.include.ivToolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            arguments.apply {
                status = OrderStatusFragmentArgs.fromBundle(this!!).status
                orderDto = OrderStatusFragmentArgs.fromBundle(this).orderDto
                step(status)

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

    }

    private fun observeData() {
        viewModel.cancelRequestMutableData.observe(viewLifecycleOwner, Observer {
            if(it.status!!){
                dialogUtils.showSuccessAlert(it.msg!!)
            }else{
                dialogUtils.showFailAlert(it.msg!!)

            }
            requireActivity().finish()
            requireActivity().startActivity(HomeActivity.getIntent(requireActivity(),1,null))
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)

        })
        fragmentOrderStatusBinding.fabSpeed.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                when(menuItem!!.itemId){
                    R.id.itemCall ->handleCall()
                    R.id.itemMessage ->handleMessage()
                    R.id.itemWhatsapp ->handleWhatsapp()
                }
                return true
            }

        })
    }

    private fun handleMessage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:${orderDto.provider.mobile}"))
//        intent.putExtra("sms_body", id.message)
        startActivity(intent)

    }

    private fun handleWhatsapp() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://wa.me/966"+orderDto.provider.mobile)
        startActivity(i)

    }

    private fun handleCall() {
        if(checkLocationPermission()){
            val uri = "tel:" + orderDto.provider.mobile
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }else{
            requestLocationPermission()
        }


    }

    fun step(step: Int) {
        if(status == 1){
            fragmentOrderStatusBinding.fabSpeed.visibility = View.GONE
            fragmentOrderStatusBinding.include.ivToolbarMenu.visibility = View.VISIBLE
        }else{
            fragmentOrderStatusBinding.fabSpeed.visibility = View.VISIBLE
            fragmentOrderStatusBinding.include.ivToolbarMenu.visibility = View.GONE
        }
        when (step) {
            1 -> makeOneActive()
            2 -> makeTwoActive()
            3,4 -> makeThreeActive()
            5,6 -> makeFourActive()
            7 -> makeFifeActive()
        }
    }

    /// bg_rb_custom for selected rb
    ///bg_rb_custom_gray for deactive
    private fun makeOneActive() {

    }

    private fun makeTwoActive() {
        fragmentOrderStatusBinding.rb2.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb2.isChecked = true
        fragmentOrderStatusBinding.vFs.setBackgroundColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.tvTwo.setTextColor(Color.WHITE)
        makeThreeSelected()
    }

    private fun makeThreeSelected() {
        fragmentOrderStatusBinding.tvThree.setTextColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.rb3.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb3.isChecked = false

    }

    private fun makeThreeActive() {
        makeTwoActive()
        fragmentOrderStatusBinding.rb3.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb3.isChecked = true
        fragmentOrderStatusBinding.vFt.setBackgroundColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.tvThree.setTextColor(Color.WHITE)
        makeFourSelected()
    }

    private fun makeFourSelected(){
        fragmentOrderStatusBinding.tvFour.setTextColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.rb4.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb4.isChecked = false

    }
    private fun makeFourActive() {
        makeThreeActive()
        fragmentOrderStatusBinding.rb4.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb4.isChecked = true
        fragmentOrderStatusBinding.vFf.setBackgroundColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.tvFour.setTextColor(Color.WHITE)
        makeFiveSelected()
    }

    private fun makeFiveSelected(){
        fragmentOrderStatusBinding.tvFive.setTextColor(Color.parseColor("#0080C6"))
        fragmentOrderStatusBinding.rb5.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb5.isChecked = false
    }
    private fun makeFifeActive() {
        makeFourActive()
        fragmentOrderStatusBinding.rb5.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_rb_custom)
        fragmentOrderStatusBinding.rb5.isChecked = true
        fragmentOrderStatusBinding.tvFive.setTextColor(Color.WHITE)

    }
    fun checkLocationPermission(): Boolean =
        EasyPermissions.hasPermissions(requireContext(),Manifest.permission.CALL_PHONE)


    fun requestLocationPermission() {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(requireActivity(), MY_PERMISSIONS_REQUEST_LOCATION, Manifest.permission.CALL_PHONE)
                .setRationale("Location permission")
                .setPositiveButtonText("Ok")
                .setNegativeButtonText("Cancel")
                .build())
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        handleCall()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,requireActivity())
    }
}