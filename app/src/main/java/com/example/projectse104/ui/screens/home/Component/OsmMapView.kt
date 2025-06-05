package com.example.projectse104.ui.screens.home.Component

import android.content.Context
import android.graphics.Color
import android.location.Geocoder
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.ui.draw.clipToBounds
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import java.util.Locale

@Composable
fun OsmMapView(
    modifier: Modifier = Modifier,
    fromLocation: String?,
    toLocation: String?,
    context: Context,
    onDistanceCalculated: (String) -> Unit = {} // Callback để trả về khoảng cách
) {
    var startGeoPoint by remember { mutableStateOf<GeoPoint?>(null) }
    var endGeoPoint by remember { mutableStateOf<GeoPoint?>(null) }

    // Khởi tạo cấu hình OSMDroid
    Configuration.getInstance().userAgentValue = "ProjectSE104"

    // Hàm parseGeoPoint và geocodeAddress giữ nguyên
    fun parseGeoPoint(coordinate: String?): GeoPoint? {
        return try {
            if (coordinate.isNullOrEmpty()) return null
            val parts = coordinate.split(",")
            if (parts.size == 2) {
                val lat = parts[0].trim().toDouble()
                val lng = parts[1].trim().toDouble()
                GeoPoint(lat, lng)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun geocodeAddress(address: String): GeoPoint? {
        return withContext(Dispatchers.IO) {
            try {
                if (Geocoder.isPresent()) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocationName(address, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val location = addresses[0]
                        GeoPoint(location.latitude, location.longitude)
                    } else null
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Convert locations thành GeoPoint
    LaunchedEffect(fromLocation, toLocation) {
        fromLocation?.let { location ->
            val geoPoint = parseGeoPoint(location) ?: geocodeAddress(location)
            startGeoPoint = geoPoint
        }
        toLocation?.let { location ->
            val geoPoint = parseGeoPoint(location) ?: geocodeAddress(location)
            endGeoPoint = geoPoint
        }
    }

    if (startGeoPoint == null || endGeoPoint == null) {
        return
    }

    val mapView = remember(startGeoPoint, endGeoPoint) {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            setBuiltInZoomControls(true)
            controller.setZoom(14.0)
            controller.setCenter(startGeoPoint)
            overlays.clear()
            val startMarker = Marker(this)
            startMarker.position = startGeoPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            startMarker.title = "Điểm bắt đầu: $fromLocation"
            overlays.add(startMarker)
            val endMarker = Marker(this)
            endMarker.position = endGeoPoint
            endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            endMarker.title = "Điểm kết thúc: $toLocation"
            overlays.add(endMarker)

            // Ngăn view cha chặn sự kiện chạm
            setOnTouchListener { _, event ->
                parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }
    }

    // Gọi API định tuyến và tính khoảng cách
    LaunchedEffect(startGeoPoint, endGeoPoint) {
        if (startGeoPoint != null && endGeoPoint != null) {
            withContext(Dispatchers.IO) {
                try {
                    // Tính khoảng cách đường thẳng
                    val straightDistance = startGeoPoint!!.distanceToAsDouble(endGeoPoint!!) / 1000.0 // km

                    // Khởi tạo RoadManager với OSRM
                    val roadManager = OSRMRoadManager(context, "ProjectSE104")
                    val waypoints = arrayListOf(startGeoPoint!!, endGeoPoint!!)
                    val road = roadManager.getRoad(waypoints)

                    // Lấy khoảng cách thực tế từ road
                    val roadDistance = road.mLength // km

                    // Chọn khoảng cách để hiển thị (ưu tiên roadDistance nếu có)
                    val distanceText = if (road.mStatus == Road.STATUS_OK) {
                        "%.2f km (đường đi)".format(roadDistance)
                    } else {
                        "%.2f km (đường thẳng)".format(straightDistance)
                    }

                    // Gửi khoảng cách qua callback
                    withContext(Dispatchers.Main) {
                        onDistanceCalculated(distanceText)
                    }

                    // Vẽ đường đi với độ dày tùy chỉnh
                    if (road.mStatus == Road.STATUS_OK) {
                        val roadOverlay = RoadManager.buildRoadOverlay(road).apply {
                            // Tùy chỉnh độ dày và màu sắc
                            paint.strokeWidth = 12f // Đường dày hơn (mặc định ~4-6f)
                            paint.color = Color.BLUE // Có thể thay đổi màu nếu muốn
                        }
                        withContext(Dispatchers.Main) {
                            mapView.overlays.add(roadOverlay)
                            mapView.invalidate()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { mapView },
        update = { /* Không cần update */ }
    )
}