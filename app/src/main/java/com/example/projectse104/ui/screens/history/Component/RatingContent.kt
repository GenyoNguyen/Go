package com.example.projectse104.ui.screens.history.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingContent(
    riderAvatarId: Int,
    trustScore: Int,
    keCoins: Int,
    riderName: String,
    riderUserId: String,
    content: String
) {
    Row(modifier = Modifier.offset(x = 53.dp)) {
        Image(
            painter = painterResource(id = riderAvatarId), // Rider's avatar image
            contentDescription = "Rider Avatar",
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
        )
        Column() {
            Text(
                text = "+$trustScore trust scores",
                fontSize = 14.sp,
                color = Color(0xFF35B82A),
                modifier = Modifier.offset(x = -10.dp)
            )
            Text(
                text = "+$keCoins Ké coins",
                fontSize = 14.sp,
                color = Color(0xFF35B82A)
            )
        }
    }
    Spacer(modifier = Modifier.height(30.dp))

    Column {
        Text(
            text = buildAnnotatedString {
                append("Rider: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderName
                append(riderName) // In đậm tên của người lái
                pop() // Kết thúc phần in đậm
                append(" - UserID: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold)) // Áp dụng đậm cho riderUserId
                append(riderUserId) // In đậm ID người lái
                pop() // Kết thúc phần in đậm
            },
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = content,
            fontSize = 16.sp
        )
    }
}