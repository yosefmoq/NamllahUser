package com.app.namllahuser.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.app.namllahuser.R
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.data.model.ServiceImage
import com.app.namllahuser.presentation.fragments.main.adapter.MainCategoryAdapter
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var mainCategoryAdapter: MainCategoryAdapter
    val servicesDto:MutableList<ServiceDto> = mutableListOf()
    lateinit var hashMap:HashMap<String,Int>
    lateinit var rvCategories:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        hashMap = HashMap();
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        /*hashMap.put("Great Indian Deal",R.drawable.resturant)
        hashMap.put("New Deal Every Hour",R.drawable.resturant)
        hashMap.put("Appliances Sale",R.drawable.resturant)
        hashMap.put("UnBox snapdeal",R.drawable.resturant)


        servicesDto.add(ServiceDto(1, ServiceImage("1234"),"test","test test"))
        servicesDto.add(ServiceDto(2, ServiceImage("1234"),"test","test test"))
        servicesDto.add(ServiceDto(3, ServiceImage("1234"),"test","test test"))
        servicesDto.add(ServiceDto(4, ServiceImage("1234"),"test","test test"))
        rvCategories = v.findViewById(R.id.rvMainCategory)
        mainCategoryAdapter = MainCategoryAdapter(requireContext(),servicesDto)
        rvCategories.adapter = mainCategoryAdapter
        rvCategories.layoutManager = GridLayoutManager(requireContext(),3)



        for (name in hashMap.keys) {
            val textSliderView = TextSliderView(requireContext())
            textSliderView
                .description(name)
                .image(hashMap.get(name)!!)
                .setScaleType(BaseSliderView.ScaleType.Fit)
            textSliderView.bundle(Bundle())
            textSliderView.bundle
                .putString("extra", name)
            v.findViewById<SliderLayout>(R.id.sliderLayout).addSlider(textSliderView)
        }*/
        return v;
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}