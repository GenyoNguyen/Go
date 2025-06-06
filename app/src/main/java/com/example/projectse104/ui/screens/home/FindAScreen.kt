package com.example.projectse104.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.HomeHeader
import com.example.projectse104.ui.screens.home.Component.SearchBar
import com.example.projectse104.ui.screens.home.Component.TopNavBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideOfferWithLocation
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow

@Composable
fun FindARideScreen(
    navController: NavController,
    userId: String,
    userName: String = "",
    viewModel: FindARideVIewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val rideOfferListState by viewModel.rideOfferListState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    var rides = emptyList<RideOfferWithLocation>()
    var finalUserName = userName.split(" ").last()
    var showErrorToast = false
    var errorMessage = ""

    // Handle userState
    if (finalUserName.isEmpty()) {
        when (val state = userState) {
            is Response.Success<User> -> {
                finalUserName = state.data?.fullName.toString().split(" ").last()
            }
            is Response.Failure -> {
                errorMessage = "Không thể tải thông tin người dùng. Vui lòng thử lại!"
                showErrorToast = true
            }
            else -> {}
        }
    }

    // Handle rideListState
    when (val state = rideOfferListState) {
        is Response.Success<List<RideOfferWithLocation>> -> {
            rides = state.data.orEmpty()
        }
        is Response.Failure -> {
            errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
            showErrorToast = true
        }
        is Response.Loading, is Response.Idle -> {
            // Không làm gì, dựa vào isLoadingMore
        }
    }

    // Show toast for errors
    if (showErrorToast) {
        ToastMessage(message = errorMessage, show = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            alpha = 0.2f
        )
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 1)
            },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HomeHeader(finalUserName,"Home",R.drawable.header_home_real)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopNavBar(navController, userId, 2, finalUserName)
                    Spacer(modifier = Modifier.height(16.dp))
                    SearchBar(
                        searchText = searchText,
                        onSearchTextChange = { searchText = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                val listState = rememberLazyListState()
                val filteredRides by remember(rides, searchText) {
                    mutableStateOf(rides.filter { ride ->
                        val searchQuery = searchText.text.trim().lowercase()
                        ride.startLocation.toString().lowercase().contains(searchQuery) ||
                                ride.endLocation.toString().lowercase().contains(searchQuery)
                    })
                }
                println("Rendering LazyColumn with ${filteredRides.size} items")
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredRides, key = { it.rideOffer.id }) { ride ->
                        val rideNo = ride.rideOffer.rideCode
                        val estimatedDeparture = ride.rideOffer.estimatedDepartTime.toCustomString()
                        val fromLocation = ride.startLocation
                        val toLocation = ride.endLocation
                        val avatarResId: Int = R.drawable.avatar_1
                        RideItem(
                            navController = navController,
                            rideNo = rideNo,
                            rideId = ride.rideOffer.id,
                            estimatedDeparture = estimatedDeparture,
                            fromLocation = fromLocation,
                            toLocation = toLocation,
                            avatarResId = avatarResId,
                            route = "ride_details",
                            userId = userId,
                            addGoButton = "yes"
                        )
                    }
                    if (filteredRides.isEmpty() && !isLoadingMore) {
                        item {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Không tìm thấy chuyến đi nào khớp với tìm kiếm",
                                    color = Color.Gray,
                                    style = TextStyle(fontSize = 16.sp)
                                )
                                if (viewModel.hasMoreData()) {
                                    Button(
                                        onClick = { viewModel.loadMoreRides(userId) },
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Text("Tải thêm")
                                    }
                                }
                            }
                        }
                    }
                    if (isLoadingMore) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                    if (rideOfferListState is Response.Failure) {
                        item {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Lỗi tải dữ liệu: ${(rideOfferListState as Response.Failure).e?.message ?: "Không xác định"}",
                                    color = Color.Red
                                )
                                Button(
                                    onClick = { viewModel.loadMoreRides(userId) },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Text("Thử lại")
                                }
                            }
                        }
                    }
                }
                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo }
                        .collect { layoutInfo ->
                            val totalItems = layoutInfo.totalItemsCount
                            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            if (lastVisibleItem != null && lastVisibleItem >= totalItems - 1 && viewModel.hasMoreData() && !isLoadingMore) {
                                viewModel.loadMoreRides(userId)
                            }
                        }
                }
            }
        }
    }
}