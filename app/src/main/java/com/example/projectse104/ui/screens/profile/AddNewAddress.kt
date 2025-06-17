// app/src/main/java/com/example/projectse104/ui/screens/profile/AddNewAddress.kt
package com.example.projectse104.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.BigButton
import com.example.projectse104.R
import com.example.projectse104.ui.screens.profile.Component.IconDropdownSelectorWithLabel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.UserLocation
import com.example.projectse104.ui.screens.profile.Component.SimpleDropdownSelectorWithLabel

@Composable
fun AddNewAddressScreen(
    navController: NavController,
    userId: String,
    viewModel: AddNewAddressViewModel = hiltViewModel()
) {
    val iconList = listOf(
        R.drawable.saved_location_home to "HOME",
        R.drawable.saved_location_work to "WORK",
        R.drawable.saved_location_other to "OTHER"
    )
    var showError by remember { mutableStateOf(false) }
    var selectedTypeIcon by remember { mutableStateOf<Int?>(null) }
    var selectedLocationIndex by remember { mutableStateOf<Int?>(null) }

    val locationListState by viewModel.locationListState.collectAsStateWithLifecycle()
    val addUserLocationState by viewModel.addUserLocationState.collectAsStateWithLifecycle()

    // Extract location names for dropdown
    val locationList: List<Location> = when (locationListState) {
        is Response.Success -> (locationListState as Response.Success<List<Location>>).data ?: emptyList()
        else -> emptyList()
    }
    val locationNames = locationList.map { it.name }

    LaunchedEffect(Unit) {
        viewModel.fetchLocationList()
    }

    // Observe addUserLocationState and navigate back on success
    LaunchedEffect(addUserLocationState) {
        if (addUserLocationState is Response.Success) {
            // Set a result flag for the previous screen to refresh its data
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("address_added", true)
            navController.popBackStack()
        }
    }

    // Show toast if fetching locations failed
    if (locationListState is Response.Failure) {
        ToastMessage(
            message = "Không thể tải dữ liệu. Vui lòng thử lại!",
            show = true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackArrowWithText(navController, "Add New Address")

        Spacer(modifier = Modifier.height(20.dp))

        IconDropdownSelectorWithLabel(
            label = "TYPE",
            selectedIconId = selectedTypeIcon,
            iconList = iconList,
            onIconSelected = { selectedTypeIcon = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SimpleDropdownSelectorWithLabel(
            label = "LOCATION",
            selectedIndex = selectedLocationIndex,
            options = locationNames,
            onOptionSelected = { selectedLocationIndex = it }
        )
        Spacer(modifier = Modifier.height(32.dp))

        BigButton(
            navController = navController,
            text = "SAVE ADDRESS",
            onClick = {
                if (selectedTypeIcon != null && selectedLocationIndex != null) {
                    val selectedLocation = locationList.getOrNull(selectedLocationIndex!!)
                    val locationId = selectedLocation?.id ?: ""
                    val userLocation = UserLocation(
                        userId = userId,
                        locationId = locationId,
                        type = iconList.find { it.first == selectedTypeIcon }?.second ?: "OTHER",
                    )
                    Log.d("AddNewAddressScreen", "Adding user location: $userLocation")
                    viewModel.addUserLocation(userLocation)
                    // navController.popBackStack() // Remove this line, navigation is handled in LaunchedEffect
                } else {
                    showError = true
                }
            }
        )
        if (showError) {
            Text(
                text = "Please fill in all the input fields before continuing.",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}