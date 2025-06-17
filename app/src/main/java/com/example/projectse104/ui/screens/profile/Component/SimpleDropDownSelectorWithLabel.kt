package com.example.projectse104.ui.screens.profile.Component
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun SimpleDropdownSelectorWithLabel(
    label: String,
    options: List<String>,
    selectedIndex: Int?,
    onOptionSelected: (Int) -> Unit
) {
    val focusedColor = Color(0xFF8FC79A)
    val unfocusedColor = Color.Gray
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
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
            Text(
                text = selectedIndex?.let { options[it] } ?: "Select an option",
                color = Color.Black,
                fontSize = 16.sp
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEachIndexed { index, labelText ->
                    DropdownMenuItem(
                        text = { Text(text = labelText) },
                        onClick = {
                            onOptionSelected(index)
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