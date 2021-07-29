package com.app.namllahuser.presentation.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentMainBinding
import com.app.namllahuser.presentation.fragments.main.home.HomeFragment
import com.app.namllahuser.presentation.fragments.main.home.orders.OrdersFragment
import com.app.namllahuser.presentation.fragments.main.notifiactions.NotificationFragment
import com.app.namllahuser.presentation.fragments.main.profile.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    val mainFragmentVM:MainFragmentVM by viewModels()
    var prevMenuItem: MenuItem? = null
    var fragmentsList = mutableListOf<Fragment>()
    lateinit var bottomNavigationAdapter: BottomNavigationAdapter
    lateinit var fragmentMainBinding: FragmentMainBinding

    /*
        lateinit var mainCategoryAdapter: MainCategoryAdapter
        val servicesDto:MutableList<ServiceDto> = mutableListOf()
        lateinit var hashMap:HashMap<String,Int>
        lateinit var rvCategories:RecyclerView
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
/*
        hashMap = HashMap();
        val v = inflater.inflate(R.layout.fragment_main, container, false)
*/

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
        return fragmentMainBinding.root.apply {
        };
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMainBinding.vp2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    fragmentMainBinding.bottomNavigationView.getMenu().getItem(0).setChecked(false)
                }
                Log.d("page", "onPageSelected: $position")
                fragmentMainBinding.bottomNavigationView.getMenu().getItem(position)
                    .setChecked(true)
                prevMenuItem = fragmentMainBinding.bottomNavigationView.getMenu().getItem(position)
            }
        })
        //        Session.getInstance(MainActivity.this).getLocalSave().saveUserToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYxMDhkOTIzNmNmNjVlNTQ0NGU4OWJlZjg3NjAyYTU3ZDU0YjQ5MjkwMmEwMmE3Y2M0ZjI2OWI4YWYxMTMwMWNhZGYwYWQ4ZTQ1YjRjYWE5In0.eyJhdWQiOiI1IiwianRpIjoiNjEwOGQ5MjM2Y2Y2NWU1NDQ0ZTg5YmVmODc2MDJhNTdkNTRiNDkyOTAyYTAyYTdjYzRmMjY5YjhhZjExMzAxY2FkZjBhZDhlNDViNGNhYTkiLCJpYXQiOjE1ODIyOTM3OTksIm5iZiI6MTU4MjI5Mzc5OSwiZXhwIjoxNjEzOTE2MTk5LCJzdWIiOiI3MyIsInNjb3BlcyI6W119.C2Pwr38cceLDediHtuI_7pm09zmrZ47yBQrLu1Bbs37Ah3SPxU4HjgMLxlyMWK5mG9Tj7pSCZ4lZsHTN9C0xSGKUMVFnVp6R4vhawdXrxD6FMN8y7uxNIJPDp9wdXlGYbaqI4M7KVJ9cVYjeoPjf5S1FI6qVTbO1eOfsv1EUs_lNK__LAyQ2ZR7OG8jETEKik9YAUihmkNjFfliHnrCxi3XhwEdAVJ6iBaZrgH8AN-jvRw_F_e_tqtKF7EiNx3oG5CaWbcjMHAaGiIWlJYhiFxH-NNfBwwi2tL37WOmklWZu_VaU_z-6ODfSnU2q7pa_QkTZkhXhmSp1GorVRauial_5-B9tUGcMzOWtuGrUicjgFqR7gFnoEDWLnp6nUgDdAKDcorwNY0hki-S3gyTrcRB1oksEVmQQHtpWTBKO8nicvdVPkB2VL7ViyWnNEXnj8tFur8p-YPWxWK8BTSL3hl4LoB2kkue2PlmWkWH42VQRtCuHbC2lZP6PIGzXuVbneJ8jaglwy2MolEKbyoEhbtRW1N4QiJq6K7eaMpS7HLwZ1kWOnOBhEN9CwCTaPgyHNQyy_hobEAEJ8EIuiSLpk5hS_JWhljHIfQhnoWdkDibXit_6FcG0IbQEQXfyG820Iw-608Wq7TC4_aa6zssfbVWCIAh2CLFEG8ksnLJHbRU");

//        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,new MainFragment()).commit();
        fragmentMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.getItemId()) {
                R.id.navHome -> fragmentMainBinding.vp2.currentItem = 0
                R.id.navOrders -> fragmentMainBinding.vp2.currentItem = 1
                R.id.navNotification -> {
                    fragmentMainBinding.vp2.currentItem = 2
                    mainFragmentVM.markAllAsRead()
                }
                R.id.navProfile -> fragmentMainBinding.vp2.currentItem = 3
            }
            true
        }
    }


    class BottomNavigationAdapter(activity: FragmentActivity, val list: MutableList<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment = list.get(position)

    }

    override fun onResume() {
        super.onResume()
        fragmentsList.add(HomeFragment())
        fragmentsList.add(OrdersFragment())
        fragmentsList.add(NotificationFragment())
        fragmentsList.add(ProfileFragment())
        bottomNavigationAdapter = BottomNavigationAdapter(requireActivity(), fragmentsList)
        fragmentMainBinding.vp2.adapter = bottomNavigationAdapter
        fragmentMainBinding.vp2.offscreenPageLimit = 4
        fragmentMainBinding.vp2.isUserInputEnabled = false
        bottomNavigationAdapter.notifyDataSetChanged()
    }


}

