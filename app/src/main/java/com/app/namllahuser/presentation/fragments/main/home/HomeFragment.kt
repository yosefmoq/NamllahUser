package com.app.namllahuser.presentation.fragments.main.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.namllahuser.R
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.data.model.UserFirebaseJaveModel
import com.app.namllahuser.databinding.FragmentHomeBinding
import com.app.namllahuser.presentation.MyForgroundService
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.fragments.main.adapter.MainCategoryAdapter
import com.app.namllahuser.presentation.fragments.main.adapter.SliderAdapterExample
import com.app.namllahuser.presentation.listeners.OnCategoryClickListeners
import com.app.namllahuser.presentation.utils.DialogUtils
import com.app.namllahuser.presentation.utils.getDifferance
import com.app.namllahuser.presentation.utils.getUserDocument
import com.app.namllahuser.presentation.utils.toCountUp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class HomeFragment : Fragment(), OnCategoryClickListeners {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    private val homeFragmentMV: HomeFragmentMV by viewModels()
    private val servicesList = mutableListOf<ServiceDto>()
    private lateinit var myDocumentReference: DocumentReference
    private lateinit var mainCategoryAdapter: MainCategoryAdapter
    private var isWorking = false
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var counter:Disposable

    //    var stopWatch: IStopWatch = StopWatch.create()

    lateinit var dialogUtils: DialogUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return fragmentHomeBinding.root.apply {
            /***********/
            homeFragmentMV.getServices()
            homeFragmentMV.getSlider()
            /* class ru.ifsoft.network.$$Lambda$ChatFragment$fhqZ2YcM2NkvXqRT_c1lBCMbhVg */ // timerx.TimeTickListener


            /**********/
            dialogUtils = DialogUtils(requireActivity())
            fragmentHomeBinding.include.name =
                getString(R.string.hello) + homeFragmentMV.getName().split(" ")[0]
            fragmentHomeBinding.include.ivHomeSetting.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
            }
            mainCategoryAdapter = MainCategoryAdapter(servicesList, this@HomeFragment)
            fragmentHomeBinding.rvMainCategory.layoutManager =
                GridLayoutManager(requireContext(), 3)
            fragmentHomeBinding.rvMainCategory.adapter = mainCategoryAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDocumentReference = this@HomeFragment.getUserDocument(homeFragmentMV.getId())
        fragmentHomeBinding.tvHomTimer.keepScreenOn = true
        observeData()
    }

    private fun observeData() {
        homeFragmentMV.serviceLiveData.observe(viewLifecycleOwner, {
            handleServices(it)
        })
        homeFragmentMV.loadingLiveData.observe(viewLifecycleOwner, {
            dialogUtils.loading(it)
        })
        homeFragmentMV.errorLiveData.observe(viewLifecycleOwner, {
            dialogUtils.showFailAlert(it)
        })
        homeFragmentMV.slidersLiveData.observe(viewLifecycleOwner, {
            handleSlider(it)
            homeFragmentMV.getMetaData()
        })
        FirebaseMessaging.getInstance().subscribeToTopic("NamllahUser")
            .addOnCompleteListener { it ->
                if (it.isComplete) {
                    FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                        if (it.isComplete) {
                            homeFragmentMV.setTokenFirebase(it.result.token)
                            homeFragmentMV.saveTokenToServer(it.result.token)
                            Log.v("ttt", "${it.result.token}  token")
                        }
                    }
                } else {
                    Log.v("ttt", it.exception!!.localizedMessage)
                }
            }
    }

    private fun handleChangeDataInMyDocument(value: DocumentSnapshot) {
        val userFirebaseJavaModel = value.toObject(UserFirebaseJaveModel::class.java)
        if (userFirebaseJavaModel != null) {
            isWorking = userFirebaseJavaModel.isIs_working
            if (isWorking) {
                fragmentHomeBinding.tvHomTimer.visibility = View.VISIBLE
//                fragmentHomeBinding.constraintLayout.visibility = View.GONE

             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(
                        Intent(
                            requireContext(),
                            MyForgroundService::class.java
                        )
                    )
                } else {
                    requireActivity().startService(
                        Intent(
                            requireContext(),
                            MyForgroundService::class.java
                        )
                    )
                }*/
                countUp(userFirebaseJavaModel)
            } else {

                if (userFirebaseJavaModel.status_id == 6) {
                    if (this::countDownTimer.isInitialized)
                        countDownTimer.cancel()
                    fragmentHomeBinding.tvHomTimer.visibility = View.VISIBLE


                    fragmentHomeBinding.tvHomTimer.text =
                        (userFirebaseJavaModel.duration).toCountUp()
                } else {
                    fragmentHomeBinding.tvHomTimer.visibility = View.GONE
                }
//                fragmentHomeBinding.constraintLayout.visibility = View.VISIBLE
            }
        }

    }


    private fun handleSlider(it: SliderResponse?) {
        val sliderAdapterExample = SliderAdapterExample(requireContext())
        fragmentHomeBinding.imageSlider.setSliderAdapter(sliderAdapterExample)
        fragmentHomeBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        fragmentHomeBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        fragmentHomeBinding.imageSlider.autoCycleDirection = AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        fragmentHomeBinding.imageSlider.indicatorSelectedColor = Color.WHITE
        fragmentHomeBinding.imageSlider.indicatorUnselectedColor = Color.GRAY
        fragmentHomeBinding.imageSlider.scrollTimeInSec = 4
        fragmentHomeBinding.imageSlider.startAutoCycle()

        sliderAdapterExample.renewItems(it!!.data)

    }

    private fun handleServices(serviceResponse: ServiceResponse) {
        mainCategoryAdapter.update(serviceResponse.data)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    fun countUp(userFirebaseJaveModel: UserFirebaseJaveModel) {

        counter = io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                val differance = userFirebaseJaveModel.complete_at.getDifferance()
                val timeAsLong = userFirebaseJaveModel.duration + (differance * 1000)

                requireActivity().runOnUiThread {
                    fragmentHomeBinding.tvHomTimer.text = timeAsLong.toCountUp()

                }
            }
/*
        job = Job()
        launch {
*/
            /*while (isWorking){
               *//* io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        val differance = userFirebaseJaveModel.complete_at.getDifferance()
                        val timeAsLong = userFirebaseJaveModel.duration + (differance * 1000)

                        fragmentHomeBinding.tvHomTimer.text = timeAsLong.toCountUp()
                    })*//*
            }*/

//        }.start()
    }

    override fun onCategoryClick(serviceDto: ServiceDto) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSelectLocationFragment(
                serviceDto
            )
        )
    }

    override fun onResume() {
        super.onResume()
        myDocumentReference.addSnapshotListener(MetadataChanges.EXCLUDE) { value, _ ->
            Log.d(TAG, "observeData: value $value")
            if (value != null) {
                if(this::counter.isInitialized)
                    counter.dispose()
                isWorking = false
//                job.cancel()
//                if (this::countDownTimer.isInitialized)
                handleChangeDataInMyDocument(value)

            }

        }
    }


}