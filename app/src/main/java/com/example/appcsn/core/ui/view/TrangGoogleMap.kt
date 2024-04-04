package com.example.appcsn.core.ui.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.core.ui.viewmodel.TrangGoogleMapViewModel
import com.example.appcsn.features.noiban.ui.nav.PlaceGraph
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.model.Place.Field
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<PlaceGraph>()
@Composable
fun TrangGoogleMap(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<TrangGoogleMapViewModel>()
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.singapore, 10F)
    }

    BackHandler {
        BaseViewModel.dsNavItem[1].backStack.removeLast()
        navigator.navigate(BaseViewModel.dsNavItem[1].backStack.last())
    }
    val text = remember {
        mutableStateOf("")
    }
    val fields = remember {
        mutableStateListOf<Field>()
    }

    LaunchedEffect(Unit) {
        viewModel.open(context)
    }

    Column {
        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.placesClient.searchByText(
                            SearchByTextRequest.newInstance(
                                text.value,
                                fields
                            )
                        )
                            .addOnSuccessListener { place ->
                                text.value =
                                    place.places[0].address ?: place.places[0].toString()
                            }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = true,
                mapToolbarEnabled = true
            )
        ) {
            Marker(
                state = MarkerState(position = viewModel.singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}