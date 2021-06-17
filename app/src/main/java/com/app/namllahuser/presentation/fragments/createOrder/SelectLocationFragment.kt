package com.app.namllahuser.presentation.fragments.createOrder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.databinding.FragmentSelectLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectLocationFragment : Fragment(),View.OnClickListener {

    lateinit var fragmentSelectLocationBinding: FragmentSelectLocationBinding
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    val MY_PERMISSIONS_REQUEST_LOCATION = 1234
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
        googleMap.setOnMapClickListener {
            googleMap.clear()
            lat =it.latitude
            lng =it.longitude
            val sydney = LatLng(lat, lng)
            googleMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))

        }

        this.googleMap = googleMap
        googleMap.getUiSettings().isZoomControlsEnabled=true

        getLocation()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSelectLocationBinding =
            FragmentSelectLocationBinding.inflate(inflater, container, false)

        return fragmentSelectLocationBinding.root.apply {
            fusedLocationProvider =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            checkLocationPermission()
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
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                requestLocationPermission()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
            return false
        } else {
            return true
        }
    }

    fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }

    }


    fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider!!.lastLocation.addOnCompleteListener {
                lat = it.result.latitude
                lng = it.result.longitude
                val sydney = LatLng(lat, lng)
                googleMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
            }

        }
    }

    override fun onClick(v: View?) {
        when(v){
            fragmentSelectLocationBinding.btnConfirmLocation->confirmLocation()
        }
    }

    private fun confirmLocation() {
        if(lat!=0.0 && lng !=0.0){
            findNavController().navigate(SelectLocationFragmentDirections.actionSelectLocationFragmentToServiceProviderFragment(service = serviceDto,lat =lat.toString() /*lat.toString()*/,lng = lng.toString()))
        }
    }
}