package com.app.namllahuser.presentation.fragments.createOrder

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
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
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.databinding.FragmentSelectLocationBinding
import com.app.namllahuser.presentation.receiver.GpsLocationReceiver
import com.app.namllahuser.presentation.utils.GpsUtils
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

@AndroidEntryPoint
class SelectLocationFragment : Fragment(), View.OnClickListener,
    EasyPermissions.PermissionCallbacks, GpsLocationReceiver.OnGpsChanged {
    private val UPDATE_INTERVAL = 120 * 1000 /* 10 secs */.toLong()
    private val FASTEST_INTERVAL: Long = 60000 /* 2 sec */
    private var mLocationRequest: LocationRequest? = null


    lateinit var gpsLocationReceiver: GpsLocationReceiver
    lateinit var gpsUtils: GpsUtils
    lateinit var fragmentSelectLocationBinding: FragmentSelectLocationBinding
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1234
    lateinit var googleMap: GoogleMap
    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var serviceDto: ServiceDto

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
/*
*/
/*
        googleMap.setOnMapClickListener {
            googleMap.clear()
            lat = it.latitude
            lng = it.longitude
            val sydney = LatLng(lat, lng)
            googleMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

        }
*/

        this.googleMap = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true


    }

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
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
    }

    private fun confirmLocation() {
        if (lat != 0.0 && lng != 0.0) {
            findNavController().navigate(
                SelectLocationFragmentDirections.actionSelectLocationFragmentToServiceProviderFragment(
                    service = serviceDto,
                    lat = lat.toString() /*lat.toString()*/,
                    lng = lng.toString()
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
                    context.getContentResolver(),
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


    override fun onStop() {
        super.onStop()

    }

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

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))

                }
            },
            Looper.myLooper()
        )
    }

}