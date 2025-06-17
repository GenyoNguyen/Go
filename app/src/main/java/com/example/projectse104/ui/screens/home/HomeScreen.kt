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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.RideItem
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.RideWithRideOfferWithLocation
import com.example.projectse104.domain.model.User
import com.example.projectse104.ui.screens.home.Component.HomeHeader
import com.example.projectse104.ui.screens.home.Component.ShimmerHomeScreen
import com.example.projectse104.ui.screens.home.Component.TopNavBar
import java.util.Date

@Composable
fun HomeScreen(
    navController: NavController,
    userId: String,
    userName: String = "",
    messageCount: Int,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val rideListState by viewModel.rideListState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val userState by viewModel.user.collectAsStateWithLifecycle()
    val avatarUrls by viewModel.avatarUrls.collectAsStateWithLifecycle()
    var rides by remember { mutableStateOf(emptyList<RideWithRideOfferWithLocation>()) }
    var finalUserName by remember { mutableStateOf(userName.split(" ").last()) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Handle userState
    LaunchedEffect(userState) {
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
        isLoading = userState is Response.Loading || rideListState is Response.Loading
    }

    // Handle rideListState
    LaunchedEffect(rideListState) {
        when (val state = rideListState) {
            is Response.Success -> {
                rides = state.data.orEmpty()
            }

            is Response.Failure -> {
                errorMessage = "Không thể tải danh sách chuyến đi. Vui lòng thử lại!"
                showErrorToast = true
            }

            else -> {}
        }
        isLoading = userState is Response.Loading || rideListState is Response.Loading
    }

    if (showErrorToast) {
        ToastMessage(message = errorMessage, show = true)
    }

    if (isLoading) {
        ShimmerHomeScreen(navController, userId, 1, 1, finalUserName)
    } else {
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
                    BottomNavigationBar(navController, userId, 1, messageCount)
                },
                containerColor = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    HomeHeader(finalUserName, "Home", R.drawable.header_home_real)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopNavBar(navController, userId, 1, finalUserName)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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
                            val avatarUrl = avatarUrls[ride.rideOffer.userId]
                            val profilePicUrl: String? = when (avatarUrl) {
                                is Response.Success<String> -> avatarUrl.data
                                else -> null
                            }
                            val isRider = ride.rideOffer.userId == userId
                            RideItem(
                                navController = navController,
                                rideNo = rideNo,
                                rideId = ride.ride.id,
                                estimatedDeparture = estimatedDeparture,
                                fromLocation = fromLocation,
                                toLocation = toLocation,
                                avatarResId = profilePicUrl,
                                route = "ride_details",
                                userId = userId,
                                riderId = ride.rideOffer.userId,
                                addGoButton = "no",
                                canEnd = isRider && ride.ride.status == RideStatus.ONGOING,
                                canStart = isRider && ride.ride.status == RideStatus.PENDING && ride.rideOffer.estimatedDepartTime <= Date(),
                                startRide =
                                    { rideId ->
                                        viewModel.startRide(rideId)
                                    },
                                endRide =
                                    { rideId ->
                                        viewModel.endRide(rideId)
                                    }
                            )
                        }
                        if (rides.isEmpty() && !isLoadingMore) {
                            item {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Không có chuyến đi nào đang chờ xử lý",
                                        color = Color.Gray,
                                        style = TextStyle(fontSize = 16.sp)
                                    )
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
                                val lastVisibleItem =
                                    layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                if (lastVisibleItem != null && lastVisibleItem >= totalItems - 1 && viewModel.hasMoreData() && !isLoadingMore) {
                                    viewModel.loadMoreRides(userId)
                                }
                            }
                    }
                }
            }
        }
    }
}