package com.example.projectse104.ui.screens.history

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
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.Header
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ShimmerScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation

@Composable
fun HistoryScreen(
    navController: NavController,
    userId: String,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val rideListState by viewModel.rideListState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val avatarUrls by viewModel.avatarUrls.collectAsStateWithLifecycle()

    var isLoading = true
    var rides = emptyList<RideWithRideOfferWithLocation>()
    var showErrorToast = false
    var errorMessage = ""

    when (val state = rideListState) {
        is Response.Success<List<RideWithRideOfferWithLocation>> -> {
            println("Fetched rides: ${state.data?.size}")
            rides = state.data.orEmpty()
            isLoading = false
        }
        is Response.Failure -> {
            println("Error: ${state.e?.message}")
            errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
            showErrorToast = true
            isLoading = false
        }
        is Response.Loading, is Response.Idle -> {
            println("State: Loading or Idle")
        }
    }

    // Simplify avatarUrls for rendering
    val avatarUrlMap = avatarUrls.mapValues { entry ->
        when (val response = entry.value) {
            is Response.Success -> response.data
            else -> null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            alpha = 0.2f
        )
        if (isLoading) {
            ShimmerScreen(navController, userId, 3, "History", R.drawable.history_svgrepo_com)
        } else {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController, userId, 3)
                },
                containerColor = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Header("History", R.drawable.history_header_icon)
                    val listState = rememberLazyListState()
                    println("Rendering LazyColumn with ${rides.size} items")
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(rides, key = { it.ride.id }) { ride ->
                            val rideNo = ride.rideOffer.rideCode
                            val estimatedDeparture = ride.ride.departTime.toCustomString()
                            val fromLocation = ride.startLocation
                            val toLocation = ride.endLocation
                            RideItem(
                                navController = navController,
                                rideNo = rideNo,
                                rideId = ride.ride.id,
                                estimatedDeparture = estimatedDeparture,
                                fromLocation = fromLocation,
                                toLocation = toLocation,
                                avatarResId = avatarUrlMap[ride.rideOffer.userId] ?: "default_avatar_url",
                                route = "ride_details_history",
                                userId = userId,
                                riderId = ride.rideOffer.userId,
                            )
                        }
                        if (rides.isEmpty() && !isLoadingMore) {
                            item {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Không có lịch sử chuyến đi",
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
                        if (rideListState is Response.Failure) {
                            item {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Lỗi tải dữ liệu: ${(rideListState as Response.Failure).e?.message ?: "Không xác định"}",
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
                                if (lastVisibleItem != null && lastVisibleItem >= totalItems - 2 && viewModel.hasMoreData() && !isLoadingMore) {
                                    println("Loading more rides at index: $lastVisibleItem, total: $totalItems")
                                    viewModel.loadMoreRides(userId)
                                }
                            }
                    }
                    LaunchedEffect(rides, isLoadingMore) {
                        if (rides.size < 4 && viewModel.hasMoreData() && !isLoadingMore) {
                            println("Auto loading more: rides.size = ${rides.size}")
                            viewModel.loadMoreRides(userId)
                        }
                    }
                }
            }
        }
        if (showErrorToast) {
            ToastMessage(
                message = errorMessage,
                show = true
            )
        }
    }
}