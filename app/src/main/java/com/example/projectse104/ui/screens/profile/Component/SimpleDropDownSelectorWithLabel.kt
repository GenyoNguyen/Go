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
import androidx.compose.ui.unit.times
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
    var searchText by remember { mutableStateOf("") }

    // Filter options based on searchText
    val filteredOptions = options.withIndex()
        .filter { it.value.contains(searchText, ignoreCase = true) }
        .toList()

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

        // The search text is entered in the "Select an option" bar
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
                .padding(horizontal = 0.dp, vertical = 0.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = true
                },
                readOnly = false,
                placeholder = {
                    Text(
                        text = selectedIndex?.let { options[it] } ?: "Select an option",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .align(Alignment.CenterStart)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .heightIn(max = 5 * 48.dp)
                    .background(Color.White)
            ) {
                // Only show filtered options, keep original index for selection
                filteredOptions.forEach { (originalIndex, labelText) ->
                    DropdownMenuItem(
                        text = { Text(text = labelText) },
                        onClick = {
                            onOptionSelected(originalIndex)
                            expanded = false
                            searchText = options[originalIndex]
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