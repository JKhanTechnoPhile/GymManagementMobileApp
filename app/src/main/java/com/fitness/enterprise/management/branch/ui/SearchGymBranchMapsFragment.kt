package com.fitness.enterprise.management.branch.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.branch.model.LocationModel
import com.fitness.enterprise.management.branch.utils.ResponseStatus
import com.fitness.enterprise.management.branch.viewmodel.SearchGymBranchMapsViewModel
import com.fitness.enterprise.management.databinding.FragmentSearchGymBranchMapsBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


//https://betterprogramming.pub/reverse-geocoding-with-google-maps-in-android-313bed159817
//https://github.com/Mustufa786/GeoCoderExample/tree/master/app/src/main/java/com/mustufamedium/geocoderexample
//https://github.com/Mustufa786
//https://www.javatpoint.com/kotlin-android-google-map-search-location
//https://www.geeksforgeeks.org/how-to-implement-current-location-button-feature-in-google-maps-in-android/
//https://developers.google.com/maps/documentation/android-sdk/views
//https://developer.android.com/training/location/permissions
//https://developer.android.com/training/location/retrieve-current

@AndroidEntryPoint
class SearchGymBranchMapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private var _binding: FragmentSearchGymBranchMapsBinding? = null
    private val binding get() = _binding!!
    var mGoogleMap: GoogleMap? = null
    private val viewModel by viewModels<SearchGymBranchMapsViewModel>()
    var latLng: LatLng = LatLng(12.9716, 77.5946)
    private var lastSelectedLocation: LocationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchGymBranchMapsBinding.inflate(inflater, container, false)

        /**
         * initializing MapView
         */
        binding.mapView.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireContext())
        binding.mapView.getMapAsync(this)

        binding.fetchedLocationAddressTextField.setEndIconOnClickListener {
            if (lastSelectedLocation == null) {
                AlertDialog.showAlert(
                    requireContext(),
                    "User Location",
                    "Please select location",
                    positiveButtonText = "OK"
                )
            } else {
                Log.d("Fetch Location", "Address: ${lastSelectedLocation?.locationAddress} Lat: ${lastSelectedLocation?.locationLatitude} Long: ${lastSelectedLocation?.locationLongitude}")
                val bundle = Bundle()
                val jsonData = Gson().toJson(lastSelectedLocation)
                bundle.putString("SELECTED_LOCATION_MODEL", jsonData)
                setFragmentResult(
                    "ADD_LOCATION",
                    bundle
                )
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLocationInformation.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    ResponseStatus.ERROR -> {
                        binding.fetchedLocationAddressTextField.hint = "Location not found!"
                    }
                    ResponseStatus.LOADING -> {
                        binding.fetchedLocationAddressTextField.hint = "Searching..."
                    }
                    ResponseStatus.SUCCESS -> {
                        it.data?.let { model ->
                            lastSelectedLocation = LocationModel().apply {
                                locationAddress = model.locationAddress
                                locationLatitude = latLng.latitude.toString()
                                locationLongitude = latLng.longitude.toString()
                            }
                            binding.fetchedLocationAddressTextField.hint = ""
                            binding.fetchedLocationAddressTextField.editText?.setText(model.locationAddress)

                        }
                    }
                }
            }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        mGoogleMap?.clear()
        mGoogleMap?.setOnCameraIdleListener(this)
        animateCamera()

        if (mGoogleMap != null) {
            mGoogleMap?.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
            if (ActivityCompat.checkSelfPermission(
                    activity!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity!!.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            enableMyLocation()
        }
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    enableMyLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    enableMyLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun enableMyLocation() {
        mGoogleMap?.setMyLocationEnabled(true)
        mGoogleMap?.getUiSettings()?.setMyLocationButtonEnabled(true)
    }

    private fun animateCamera() {
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mGoogleMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    override fun onResume() {
        if (binding != null) {
            binding.mapView.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        if (binding != null) {
            binding.mapView.onPause()
        }
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLowMemory() {
        if (binding != null) {
            binding.mapView.onLowMemory()
        }
        super.onLowMemory()
    }

    override fun onCameraIdle() {
        mGoogleMap?.let {
            it.cameraPosition.let { position ->
                latLng = mGoogleMap?.cameraPosition!!.target
                viewModel.getLocationInfo(
                    requireContext(),
                    latLng.latitude.toString(),
                    latLng.longitude.toString()
                )
            }
        }
    }
}