package com.unit_one.e_commerceapp.ui.GoogleMaps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.unit_one.e_commerceapp.databinding.CustomInfoWindowBinding

class CustomInfoWindow(val context:Context): InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {

        val binding = CustomInfoWindowBinding.inflate(LayoutInflater.from(context))
        binding.tvTitle.text = marker.title
        binding.tvDescription.text = marker.snippet

        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}