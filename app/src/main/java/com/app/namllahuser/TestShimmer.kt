package com.app.namllahuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestShimmer : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_test_shimmer, container, false)
        v.findViewById<ShimmerFrameLayout>(R.id.shimmer_text).startShimmer()
        return v;
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestShimmer().apply {
            }
    }
}