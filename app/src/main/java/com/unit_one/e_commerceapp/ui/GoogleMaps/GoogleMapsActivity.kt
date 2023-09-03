package com.unit_one.e_commerceapp.ui.GoogleMaps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.ActivityGoogleMapsBinding
import com.unit_one.e_commerceapp.ui.base.BaseActivity
import com.unit_one.e_commerceapp.ui.base.BaseViewModel


class GoogleMapsActivity : BaseActivity<ActivityGoogleMapsBinding, BaseViewModel>(
    R.layout.activity_google_maps,
    BaseViewModel::class.java
), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200

    private var locationPermissionGranted = false


    override fun setup() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLocationPermission()
        updateLocationUI()
        val customInfoWindowAdapter = CustomInfoWindow(this)
        mMap.setInfoWindowAdapter(customInfoWindowAdapter)

        mMap.setOnMapClickListener {
            mMap.addMarker(
                MarkerOptions().position(it).title("My New Marker")
                    .snippet(it.toString())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            )
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            log(e.message)
        }
    }

    private fun convertVectorToBitMap(@DrawableRes drawableId: Int): Bitmap {
        val vectorDrawable = ContextCompat.getDrawable(this, drawableId)
        val width = vectorDrawable!!.intrinsicWidth
        val height = vectorDrawable.intrinsicHeight
        vectorDrawable.setBounds(0, 0, width, height)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return bitmap
    }


}