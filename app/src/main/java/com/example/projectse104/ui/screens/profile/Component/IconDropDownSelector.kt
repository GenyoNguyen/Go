package com.example.projectse104.ui.screens.profile.Component
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.projectse104.R
import com.example.projectse104.*
import com.example.projectse104.Component.*
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.rememberShimmer
import androidx.compose.runtime.*

@Composable
fun IconDropdownSelectorWithLabel(
    label: String,
    selectedIconId: Int?,
    iconList: List<Pair<Int, String>>,
    onIconSelected: (Int) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)
    val unfocusedColor = Color.Gray
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {

        Text(
            text = label,
            fontSize = 12.sp,
            color = if (expanded) focusedColor else Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = -7.dp)
                .zIndex(1f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 1.dp,
                    color = if (expanded) focusedColor else unfocusedColor,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(Color.White)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (selectedIconId != null) {
                    Icon(
                        painter = painterResource(id = selectedIconId),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = iconList.find { it.first == selectedIconId }?.second ?: "Select an icon",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                iconList.forEach { (iconRes, labelText) ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = labelText)
                            }
                        },
                        onClick = {
                            onIconSelected(iconRes)
                            expanded = false
                        }
                    )
                }
            }
        }

        // Label background mask
        val coverWidth = when (label.length) {
            in 1..5 -> 55.dp
            in 6..8 -> 75.dp
            else -> 110.dp
        }

        Box(
            modifier = Modifier
                .width(coverWidth)
                .height(6.dp)
                .background(Color.White)
                .offset(x = 20.dp, y = 7.dp)
        )
    }

    Spacer(modifier = Modifier.height(40.dp))
}
