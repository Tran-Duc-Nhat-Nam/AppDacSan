package com.example.appcsn.core.ui.viewmodel

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrangGoogleMapViewModel @Inject constructor(
    val placesClient: PlacesClient
) : BaseViewModel() {
    val singapore = LatLng(1.35, 103.87)

    fun open(context: Context) {
        Places.initializeWithNewPlacesApiEnabled(context, "MAPS_API_KEY")
    }
}