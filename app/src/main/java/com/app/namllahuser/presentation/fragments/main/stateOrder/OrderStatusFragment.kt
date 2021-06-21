package com.app.namllahuser.presentation.fragments.main.stateOrder

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.FragmentOrderStatusBinding
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderStatusFragment : Fragment() {
    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding
    lateinit var orderDto:OrderDto
    lateinit var dialogUtils:DialogUtils
     var status:Int = 0
    companion object {
        fun newInstance() = OrderStatusFragment()
    }


    private  val  viewModel: OrderStatusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            findNavController().popBackStack()
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {

        })
    }

    fun step(step: Int) {
        if(status == 1){
            fragmentOrderStatusBinding.btnContactWithProvider.visibility = View.GONE
            fragmentOrderStatusBinding.include.ivToolbarMenu.visibility = View.VISIBLE
        }else{
            fragmentOrderStatusBinding.btnContactWithProvider.visibility = View.VISIBLE
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

}