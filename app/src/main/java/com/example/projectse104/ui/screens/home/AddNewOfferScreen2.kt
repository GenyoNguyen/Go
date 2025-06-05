package com.example.projectse104.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.ui.navigation.Screen
import com.example.projectse104.Component.*
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.ui.screens.home.Component.*

@Composable
fun AddNewOfferScreen2(
    navController: NavController,
    userId: String,
    time: String,
    viewModel: AddNewOfferViewModel = hiltViewModel()
) {
    var departureLocation by remember { mutableStateOf("") }
    var departureLocationId by remember { mutableStateOf("") }

    val locationListState by viewModel.locationListState.collectAsStateWithLifecycle()
    val locationOptions: List<String> = when (locationListState) {
        is Response.Success -> (locationListState as Response.Success<List<Location>>).data?.map { it.name } ?: emptyList()
        else -> emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackArrowWithText(navController, "Add new offer")

        Spacer(modifier = Modifier.height(100.dp))

        AddNewOfferContent(R.drawable.flag_icon, "Where is the departure location?")

        Spacer(modifier = Modifier.height(16.dp))

        InputBar(
            value = departureLocation,
            options = locationOptions,
            onValueChange = { selectedLocation ->
                departureLocation = selectedLocation
                departureLocationId = when (val state = locationListState) {
                    is Response.Success -> {
                        val matchedLocation = state.data?.find { it.name.equals(selectedLocation, ignoreCase = true) }
                        matchedLocation?.id ?: ""
                    }
                    else -> ""
                }
                Log.d("AddNewOfferScreen2", "Selected departureLocationId: $departureLocationId")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(Modifier.fillMaxWidth(0.8f)) {
            var showError by remember { mutableStateOf(false) }
            BigButton(navController, "NEXT") {
                if (departureLocation.isNotBlank() && departureLocationId.isNotBlank()) {
                    navController.navigate("add_new_offer3/$userId/$time/$departureLocationId")
                } else {
                    showError = true
                }
            }
            if (showError) {
                Text(
                    text = "Please select a location.",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}