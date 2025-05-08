package com.example.projectse104.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.projectse104.ui.screens.profile.Component.ProfileCustomTextFieldWithLabel

@Composable
fun AddNewAddressScreen(navController: NavController, userId: String) {
    val iconList = listOf(
        R.drawable.saved_location_home to "Home Icon",
        R.drawable.saved_location_work to "Work Icon",
        R.drawable.saved_location_other to "Other Icon"
    )
    var showError by remember { mutableStateOf(false) }
    var selectedIcon by remember { mutableStateOf<Int?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackArrowWithText(navController, "Add New Address")

        Spacer(modifier = Modifier.height(20.dp))

        // ✅ Hiển thị Dropdown được tách riêng
        IconDropdownSelectorWithLabel(
            label = "ICON",
            selectedIconId = selectedIcon,
            iconList = iconList,
            onIconSelected = { selectedIcon = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Trường nhập tên và mô tả
        ProfileCustomTextFieldWithLabel("NAME", name) {
            name = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileCustomTextFieldWithLabel("DESCRIPTION", description) {
            name = it
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Nút lưu địa chỉ
        BigButton(
            navController = navController,
            text = "SAVE ADDRESS",
            onClick = {
                if (selectedIcon != null && name.isNotBlank() && description.isNotBlank()) {
                    navController.popBackStack()
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
