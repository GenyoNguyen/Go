package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BackArrowWithText
import com.example.projectse104.Component.BigButton
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.R
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserLocationWithLocation
import com.example.projectse104.ui.screens.profile.Component.SavedLocation
import com.example.projectse104.ui.screens.profile.Component.ShimmerSavedLocationScreen

@Composable
fun SavedLocationScreen(
    navController: NavController,
    userId: String,
    viewModel: SavedLocationViewModel = hiltViewModel()
) {

    val iconMap = mapOf(
        "Home" to R.drawable.saved_location_home,
        "Work" to R.drawable.saved_location_work,
        "Other" to R.drawable.saved_location_other
    ).withDefault { R.drawable.saved_location_other }

    val savedLocationState by viewModel.savedLocationListState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var savedLocations: List<UserLocationWithLocation> = emptyList()

    when (val state = savedLocationState) {
        is Response.Success<List<UserLocationWithLocation>> -> {
            savedLocations = state.data.orEmpty()
            viewModel.setLoading(false)
        }

        is Response.Failure -> {
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }

        else -> {}
    }

    if (isLoading) {
        ShimmerSavedLocationScreen(navController)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            BackArrowWithText(navController, "Saved location")

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                for (savedLocation in savedLocations) {
                    SavedLocation(
                        iconMap[savedLocation.type.nameType]!!,
                        savedLocation.type.nameType,
                        savedLocation.location.name
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                BigButton(
                    navController = navController,
                    text = "ADD NEW ADDRESS",
                    onClick = {navController.navigate("add_new_address/{userId}")}
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Ensuring the content is aligned above the navbar
            BottomNavigationBar(navController, userId, 4)
        }
    }
}