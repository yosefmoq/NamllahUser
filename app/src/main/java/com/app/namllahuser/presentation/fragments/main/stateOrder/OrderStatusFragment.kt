package com.app.namllahuser.presentation.fragments.main.stateOrder

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentOrderStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderStatusFragment : Fragment() {
    lateinit var fragmentOrderStatusBinding: FragmentOrderStatusBinding

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
            step(2)
        }
    }


    fun step(step: Int) {
        when (step) {
            1 -> makeOneActive()
            2 -> makeTwoActive()
            3 -> makeThreeActive()
            4 -> makeFourActive()
            5 -> makeFifeActive()
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