package com.app.namllahuser.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R

class TestFragment : Fragment() {

    private var button :Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        button = root.findViewById(R.id.btn_testNav)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button!!.setOnClickListener {
//            findNavController().navigate(R.id.action_testFragment_to_test2Fragment)
        }
    }

    companion object {

    }
}