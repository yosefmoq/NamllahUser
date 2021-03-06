package com.app.namllahuser.presentation.fragments.wizard.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.databinding.FragmentSplashBinding
import com.app.namllahuser.presentation.MainActivity
import com.app.namllahuser.presentation.activities.HomeActivity
import com.app.namllahuser.presentation.service.MyFirebaseInstanceIDService
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var fragmentSplashBinding: FragmentSplashBinding? = null

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return fragmentSplashBinding?.apply {

        }?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated : printInLaunch")
        GlobalScope.launch(context = Dispatchers.Main) {
/*
            val tokenSubscriber = object: io.reactivex.Observer<String> {
                override fun onNext(token: String) {

                    splashViewModel.saveToken(token)
                    Log.v("ttt","token saved")

                }

                override fun onComplete() {
                    println("onComplete")

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onSubscribe(d: Disposable) {

                }

            }

            MyFirebaseInstanceIDService.getObservable().subscribe(tokenSubscriber)
*/

            delay(3000)
            moveToNextUI()
        }

        GlobalScope.launch(context = Dispatchers.Main) {
            //Do Other Action like check network and initial requests from server


        }


    }

    private fun moveToNextUI() {
        Timber.tag(TAG).d("moveToNextUI : ")
        val isLogin = splashViewModel.isLogin()
        val isSeenOnBoarding = splashViewModel.isSeenOnBoarding()
        if(isLogin){
            startActivity(HomeActivity.getIntent(requireActivity(),1,null))
            requireActivity().finishAffinity();
        }else{
            var  destination = 0;
            if (!isSeenOnBoarding) {
                //Go to Boarding Screen
                destination = R.id.action_splashFragment_to_onBoardingFragment
            } else {
                //Go to Login Screen
                destination = R.id.action_splashFragment_to_signInFragment
            }
            findNavController().navigate(destination)
        }

    }



    companion object {
        private const val TAG = "SplashFragment"

    }
}