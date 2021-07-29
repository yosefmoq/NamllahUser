package com.app.namllahuser.presentation.fragments.main.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.namllahuser.R
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.data.model.UserFirebaseJaveModel
import com.app.namllahuser.databinding.FragmentHomeBinding
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.fragments.main.adapter.MainCategoryAdapter
import com.app.namllahuser.presentation.fragments.main.adapter.SliderAdapterExample
import com.app.namllahuser.presentation.listeners.OnCategoryClickListeners
import com.app.namllahuser.presentation.utils.DialogUtils
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
/*
import com.stopwatch.IStopWatch
import com.stopwatch.StopWatch
*/
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timerx.StopwatchBuilder
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class HomeFragment : Fragment(), OnCategoryClickListeners, CoroutineScope {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    private val homeFragmentMV: HomeFragmentMV by viewModels()
    private val servicesList = mutableListOf<ServiceDto>()
    private lateinit var myDocumentReference: DocumentReference
    private lateinit var mainCategoryAdapter: MainCategoryAdapter
    private var isWorking = false
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var stopwatch: StopwatchBuilder? = null
//    var stopWatch: IStopWatch = StopWatch.create()

    lateinit var dialogUtils: DialogUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return fragmentHomeBinding.root.apply {
            /***********/
            homeFragmentMV.getServices()
            homeFragmentMV.getSlider()
            /* class ru.ifsoft.network.$$Lambda$ChatFragment$fhqZ2YcM2NkvXqRT_c1lBCMbhVg */ // timerx.TimeTickListener

            /**********/
            dialogUtils = DialogUtils(requireActivity())
            fragmentHomeBinding.include.name = getString(R.string.hello)+homeFragmentMV.getName().split(" ").get(0)
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
        job = Job()
        observeData()

    }

    private fun observeData() {
        homeFragmentMV.serviceLiveData.observe(viewLifecycleOwner, Observer {
            handleServices(it)
        })
        homeFragmentMV.loadingLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.loading(it)
        })
        homeFragmentMV.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogUtils.showFailAlert(it)
        })
        homeFragmentMV.slidersLiveData.observe(viewLifecycleOwner, Observer {
            handleSlider(it)
            homeFragmentMV.getMetaData()
        })
        myDocumentReference.addSnapshotListener(MetadataChanges.EXCLUDE) { value, error ->
            Log.d(TAG, "observeData: value $value")
            handleChangeDataInMyDocument(value!!)

        }
        FirebaseMessaging.getInstance().subscribeToTopic("NamllahUser").addOnCompleteListener {

            if (it.isComplete) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener {
                    if (it.isComplete) {
                        homeFragmentMV.setTokenFirebase(it.result.token)
                        homeFragmentMV.saveTokenToServer()
                        Log.v("ttt", "${it.result.token}  token")
                    }
                }

            } else {
                Log.v("ttt", it.exception!!.localizedMessage)
            }
        }
    }

    private fun handleChangeDataInMyDocument(value: DocumentSnapshot) {
        val userFirebaseJaveModel = value.toObject(UserFirebaseJaveModel::class.java)
        if (userFirebaseJaveModel != null) {

            job.cancel()
//        Thread.sleep(1000)
            isWorking = userFirebaseJaveModel.isIs_working
            if (userFirebaseJaveModel.isIs_working) {

                fragmentHomeBinding.tvHomTimer.visibility = View.VISIBLE
//                fragmentHomeBinding.constraintLayout.visibility = View.GONE
                countUp(userFirebaseJaveModel.duration)
/*
            stopwatch = TimerBuilder().startTime(userFirebaseJaveModel.duration, TimeUnit.MILLISECONDS)
                    .startFormat("MM:SS")
                    .onTick { charSequence: CharSequence? ->
                        fragmentHomeBinding.tvHomTimer.text = charSequence
                    }.changeFormatWhen(1, TimeUnit.HOURS, "HH:MM:SS").build()
            stopwatch!!.start()
*/

            } else {
/*
            if (stopwatch != null) {
                stopwatch!!.release()
                stopwatch!!.stop()
            }
*/
                fragmentHomeBinding.tvHomTimer.visibility = View.GONE
//                fragmentHomeBinding.constraintLayout.visibility = View.VISIBLE
            }
        }

    }

    private fun handleSlider(it: SliderResponse?) {
        val sliderAdapterExample = SliderAdapterExample(requireContext())
        fragmentHomeBinding.imageSlider.setSliderAdapter(sliderAdapterExample)
        fragmentHomeBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        fragmentHomeBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        fragmentHomeBinding.imageSlider.setAutoCycleDirection(AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        fragmentHomeBinding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        fragmentHomeBinding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        fragmentHomeBinding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        fragmentHomeBinding.imageSlider.startAutoCycle();

        sliderAdapterExample.renewItems(it!!.data)

    }

    private fun handleServices(serviceResponse: ServiceResponse) {
        mainCategoryAdapter.update(serviceResponse.data)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    fun countUp(time: Long) {
        job = Job()
        launch {
            var timeAsLong = time
            while (isWorking) {
                if (!isWorking)
                    break
                delay(1000)
                timeAsLong += 1000.toLong()
                fragmentHomeBinding.tvHomTimer.text = timeAsLong.toCountUp()
                Log.v("ttt", timeAsLong.toCountUp())
            }
        }.start()

    }

    override fun onCategoryClick(serviceDto: ServiceDto) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToSelectLocationFragment(
                serviceDto
            )
        )
    }

}