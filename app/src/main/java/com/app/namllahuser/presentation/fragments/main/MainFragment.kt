package com.app.namllahuser.presentation.fragments.main

import android.os.Bundle
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
    private val mainFragmentVM:MainFragmentVM by viewModels()
    var prevMenuItem: MenuItem? = null
    var fragmentsList = mutableListOf<Fragment>()
    lateinit var bottomNavigationAdapter: BottomNavigationAdapter
    lateinit var fragmentMainBinding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        return fragmentMainBinding.root.apply {

            fragmentsList.add(HomeFragment())
            fragmentsList.add(OrdersFragment())
            fragmentsList.add(NotificationFragment())
            fragmentsList.add(ProfileFragment())
            bottomNavigationAdapter = BottomNavigationAdapter(requireActivity(), fragmentsList)
            fragmentMainBinding.vp2.adapter = bottomNavigationAdapter
            fragmentMainBinding.vp2.offscreenPageLimit = 4
            fragmentMainBinding.vp2.isUserInputEnabled = false

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMainBinding.vp2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    fragmentMainBinding.bottomNavigationView.menu.getItem(0).isChecked = false
                }
                fragmentMainBinding.bottomNavigationView.menu.getItem(position).isChecked = true
                prevMenuItem = fragmentMainBinding.bottomNavigationView.menu.getItem(position)
            }
        })
        //        Session.getInstance(MainActivity.this).getLocalSave().saveUserToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYxMDhkOTIzNmNmNjVlNTQ0NGU4OWJlZjg3NjAyYTU3ZDU0YjQ5MjkwMmEwMmE3Y2M0ZjI2OWI4YWYxMTMwMWNhZGYwYWQ4ZTQ1YjRjYWE5In0.eyJhdWQiOiI1IiwianRpIjoiNjEwOGQ5MjM2Y2Y2NWU1NDQ0ZTg5YmVmODc2MDJhNTdkNTRiNDkyOTAyYTAyYTdjYzRmMjY5YjhhZjExMzAxY2FkZjBhZDhlNDViNGNhYTkiLCJpYXQiOjE1ODIyOTM3OTksIm5iZiI6MTU4MjI5Mzc5OSwiZXhwIjoxNjEzOTE2MTk5LCJzdWIiOiI3MyIsInNjb3BlcyI6W119.C2Pwr38cceLDediHtuI_7pm09zmrZ47yBQrLu1Bbs37Ah3SPxU4HjgMLxlyMWK5mG9Tj7pSCZ4lZsHTN9C0xSGKUMVFnVp6R4vhawdXrxD6FMN8y7uxNIJPDp9wdXlGYbaqI4M7KVJ9cVYjeoPjf5S1FI6qVTbO1eOfsv1EUs_lNK__LAyQ2ZR7OG8jETEKik9YAUihmkNjFfliHnrCxi3XhwEdAVJ6iBaZrgH8AN-jvRw_F_e_tqtKF7EiNx3oG5CaWbcjMHAaGiIWlJYhiFxH-NNfBwwi2tL37WOmklWZu_VaU_z-6ODfSnU2q7pa_QkTZkhXhmSp1GorVRauial_5-B9tUGcMzOWtuGrUicjgFqR7gFnoEDWLnp6nUgDdAKDcorwNY0hki-S3gyTrcRB1oksEVmQQHtpWTBKO8nicvdVPkB2VL7ViyWnNEXnj8tFur8p-YPWxWK8BTSL3hl4LoB2kkue2PlmWkWH42VQRtCuHbC2lZP6PIGzXuVbneJ8jaglwy2MolEKbyoEhbtRW1N4QiJq6K7eaMpS7HLwZ1kWOnOBhEN9CwCTaPgyHNQyy_hobEAEJ8EIuiSLpk5hS_JWhljHIfQhnoWdkDibXit_6FcG0IbQEQXfyG820Iw-608Wq7TC4_aa6zssfbVWCIAh2CLFEG8ksnLJHbRU");

//        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,new MainFragment()).commit();
        fragmentMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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


    class BottomNavigationAdapter(activity: FragmentActivity, private val list: MutableList<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment = list[position]

    }


    /*fun showRateDialog(){
        val viewGroup: ViewGroup =
            requireActivity().window.findViewById(android.R.id.content)
        val dialogReportBinding =
            DialogRateBinding.inflate(LayoutInflater.from(context), viewGroup, false)

        val builder: AlertDialog.Builder =
            AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)




        builder.setView(dialogReportBinding.root)


        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
*/

}

