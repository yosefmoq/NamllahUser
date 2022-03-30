package com.app.namllahuser.presentation.fragments.createOrder

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.databinding.FragmentSelectLocationBinding
import com.app.namllahuser.presentation.receiver.GpsLocationReceiver
import com.app.namllahuser.presentation.utils.GpsUtils
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import android.graphics.Bitmap
import com.app.namllahuser.R
import androidx.core.content.ContextCompat
import android.graphics.Canvas
import com.app.namllahuser.presentation.utils.CustomMap

@AndroidEntryPoint
class SelectLocationFragment : Fragment(), View.OnClickListener,
    EasyPermissions.PermissionCallbacks, GpsLocationReceiver.OnGpsChanged {
    private val UPDATE_INTERVAL = 180 * 1000 /* 10 secs */.toLong()
    private val FASTEST_INTERVAL: Long = 600000 /* 2 sec */
    private var mLocationRequest: LocationRequest? = null
    private var zoom = 13f;

    lateinit var gpsLocationReceiver: GpsLocationReceiver
    private lateinit var gpsUtils: GpsUtils
    lateinit var fragmentSelectLocationBinding: FragmentSelectLocationBinding
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1234
    lateinit var googleMap: GoogleMap
    var lat: Double = 0.0
    var lng: Double = 0.0
    var selectedLat: Double = 0.0
    var selectedLng: Double = 0.0
    lateinit var serviceDto: ServiceDto

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(23.8859, 45.0792)))
       /* googleMap.setOnCameraMoveListener {
            if (zoom == googleMap.cameraPosition.zoom) {
                googleMap.clear()
            }else{
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(selectedLat,selectedLng)))
            }
            Log.v("tttt","visible the marker view")

            fragmentSelectLocationBinding.ivMarker.visibility =
                View.VISIBLE
        }*/
        googleMap.setOnCameraMoveStartedListener {
            when(it){
                GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
                    if(zoom == googleMap.cameraPosition.zoom){
                        googleMap.clear()
                        fragmentSelectLocationBinding.ivMarker.visibility = View.VISIBLE

                    }

                }

            }

        }
        googleMap.setOnCameraIdleListener {
            Log.v("tttt","Zoom :: $zoom")
            Log.v("tttt","Camera Position Zoom :: ${googleMap.cameraPosition.zoom}")
            if (zoom == googleMap.cameraPosition.zoom) {
                zoom = googleMap.cameraPosition.zoom
                Log.v("tttt","changed Marker")
                googleMap.clear()
                googleMap.addMarker(
                    MarkerOptions().position(googleMap.cameraPosition.target)
                        .title("My Location").icon(
                            bitmapDescriptorFromVector(requireContext(), R.drawable.ic_placeholder)
                        )
                )
                selectedLat = googleMap.cameraPosition.target.latitude
                selectedLng = googleMap.cameraPosition.target.longitude
                fragmentSelectLocationBinding.ivMarker.visibility = View.GONE
            } else {
                Log.v("tttt","Zoom changed")
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(selectedLat,selectedLng)))
                zoom = googleMap.cameraPosition.zoom
                fragmentSelectLocationBinding.ivMarker.visibility = View.GONE


            }

        }

        /*  googleMap.setOnCameraMoveListener {
              val aa = googleMap.cameraPosition.target
              if (selectedLat != 0.0) {
                  if (zoom == googleMap.cameraPosition.zoom) {
                      selectedLat = aa.latitude
                      selectedLng = aa.longitude
                  } else {
                      googleMap.animateCamera(
                          CameraUpdateFactory.newLatLng(
                              LatLng(
                                  selectedLat,
                                  selectedLng
                              )
                          )
                      )
                  }
              }

          }*/
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSelectLocationBinding =
            FragmentSelectLocationBinding.inflate(inflater, container, false)
        fusedLocationProvider =
            LocationServices.getFusedLocationProviderClient(requireActivity())


        return fragmentSelectLocationBinding.root.apply {
            gpsUtils = GpsUtils(requireActivity())
            turnGps()
            fragmentSelectLocationBinding.onClick = this@SelectLocationFragment
/*            fragmentSelectLocationBinding.view4.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        googleMap.clear()
                        fragmentSelectLocationBinding.ivMarker.visibility = View.VISIBLE
                    }
                    MotionEvent.ACTION_UP ->{
                        googleMap.addMarker(MarkerOptions().position(googleMap.cameraPosition.target).title("My Location").icon(
                                bitmapDescriptorFromVector(requireContext(),R.drawable.ic_placeholder)
                                ))
                        selectedLat = googleMap.cameraPosition.target.latitude
                        selectedLng = googleMap.cameraPosition.target.longitude
                        fragmentSelectLocationBinding.ivMarker.visibility = View.GONE

                    }
                }

                    true
            }*/


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as CustomMap?

        mapFragment!!.setNonConsumingTouchListener(object : CustomMap.NonConsumingTouchListener {
            override fun onTouch(motionEvent: MotionEvent?): Boolean {

                if (MotionEvent.ACTION_UP == motionEvent!!.action) {
                    Log.v("ttt", "Action Up")
                    Log.v("ttt", "pointer Count :: ${motionEvent.pointerCount}")


                }

                if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                    Log.v("ttt", "pointer Count :: ${motionEvent.pointerCount}")


                }

                return true
            }

        })
        mapFragment.getMapAsync(callback)
        arguments.let {
            serviceDto = SelectLocationFragmentArgs.fromBundle(it!!).service
        }


        gpsLocationReceiver = GpsLocationReceiver(this)

        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        requireActivity().registerReceiver(gpsLocationReceiver, filter)


    }

    private fun checkLocationPermission() =
        EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)


    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(
                    this,
                    MY_PERMISSIONS_REQUEST_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
                    .setRationale("Location permission")
                    .setPositiveButtonText("Ok")
                    .setNegativeButtonText("Cancel")
                    .build()
            )
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(
                    this,
                    MY_PERMISSIONS_REQUEST_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                    .setRationale("Location permission")
                    .setPositiveButtonText("Ok")
                    .setNegativeButtonText("Cancel")
                    .build()
            )

        }

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()

            return
        }
        startLocationUpdates()
/*
        fusedLocationProvider!!.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result != null) {
                    Toast.makeText(requireContext(), "It not Null", Toast.LENGTH_SHORT).show()

                    Log.v(
                        "ttt",
                        "result !=null So the lat = ${it.result.latitude}  and  lng = ${it.result.longitude}"
                    )
                } else {
                    val mLocationManager =
                        requireActivity().getSystemService(LOCATION_SERVICE) as (LocationManager)
                    val location =
                        mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        lat = location.latitude
                        lng = location.longitude
                    }
                    val sydney = LatLng(lat, lng)

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

                }
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()

            }
*/

    }

    override fun onClick(v: View?) {
        when (v) {
            fragmentSelectLocationBinding.btnConfirmLocation -> confirmLocation()
            fragmentSelectLocationBinding.ivShotting -> returnToMyLocation()
        }
    }

    private fun returnToMyLocation() {
        googleMap.clear()
        val sydney = LatLng(lat, lng)

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom))
    }

    private fun confirmLocation() {
        if (selectedLat != 0.0 && selectedLng != 0.0) {
            findNavController().navigate(
                SelectLocationFragmentDirections.actionSelectLocationFragmentToServiceProviderFragment(
                    service = serviceDto,
                    lat = selectedLat.toString() /*lat.toString()*/,
                    lng = selectedLng.toString()
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getLocation()
    }


    fun turnGps() {
        if (isLocationEnabled(requireContext())) {
            getLocation()
            Log.v("ttt", "location enable")
        } else {

            Log.v("ttt", "loaction not enable")

            gpsUtils.turnGPSOn {

            }

        }

    }

    private fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            locationMode = try {
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            !TextUtils.isEmpty(locationProviders)
        }
    }

    override fun gpsChanged() {
        turnGps()
        Toast.makeText(requireContext(), "changed", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("MissingPermission")
    protected fun startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.interval = UPDATE_INTERVAL
        mLocationRequest!!.fastestInterval = FASTEST_INTERVAL


        // Create LocationSettingsRequest object using location request
        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest: LocationSettingsRequest = builder.build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        settingsClient.checkLocationSettings(locationSettingsRequest)

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
            return
        }
        fusedLocationProvider!!.requestLocationUpdates(
            mLocationRequest!!, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // do work here
                    lat = locationResult.lastLocation.latitude
                    lng = locationResult.lastLocation.longitude
//                    googleMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
                    val sydney = LatLng(lat, lng)

                    googleMap.addMarker(
                        MarkerOptions().position(sydney).title("My Location").icon(
                            bitmapDescriptorFromVector(requireContext(), R.drawable.ic_placeholder)
                        )
                    )
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom))


                }
            },
            Looper.myLooper()!!
        )
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}