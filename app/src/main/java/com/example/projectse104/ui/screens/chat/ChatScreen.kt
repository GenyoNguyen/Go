package com.example.projectse104.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.Header
import com.example.projectse104.Component.ShimmerScreen
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.ui.screens.chat.Component.ChatItem

@Composable
fun ChatScreen(
    navController: NavController,
    userId: String,
    messageCount: Int,
    viewModel: ChatViewModel = hiltViewModel()
) {
    // Ensure ViewModel is initialized when the screen is composed
    LaunchedEffect(userId) {
        viewModel.initialize(userId)
    }
    val conversationListState by viewModel.conversationListState.collectAsStateWithLifecycle()
    Log.d("ChatScreen", "Conversation List State Updated")
    val avatarUrls by viewModel.avatarUrls.collectAsStateWithLifecycle()
    val isLoading = false

    if (isLoading) {
        ShimmerScreen(navController, userId, 2, "Chat", R.drawable.chat_unread_svgrepo_com)
    } else {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 2, messageCount)
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Header("Chat", R.drawable.chat_icon_header)
                TextButton(onClick = {
                    Log.d("ChatScreen", "Current state: $conversationListState")
                }) {
                    Text("Log Conversation List State")
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        conversationListState,
                        key = { it.conversation?.id ?: it.hashCode() }
                    ) { conversation ->
                        val otherId = if (conversation.conversation!!.firstUserId == userId) {
                            conversation.conversation.secondUserId
                        } else {
                            conversation.conversation.firstUserId
                        }
                        val name = conversation.otherName
                        val message = conversation.lastMessage?.content.toString()
                        val time = conversation.lastMessage?.timeSent.toCustomString()
                        val imageRes = R.drawable.avatar_1
                        val haveSeen = conversation.lastMessage?.isRead ?: false
                        val isOnline = true // TODO: Implement online function

                        val profilePicUrl = avatarUrls[otherId]?.let { response ->
                            when (response) {
                                is Response.Success -> response.data
                                is Response.Loading -> null
                                is Response.Failure -> null
                                Response.Idle -> TODO()
                            }
                        }

                        ChatItem(
                            navController = navController,
                            userId = userId,
                            otherId = otherId,
                            otherName = name,
                            message = message,
                            time = time,
                            imageRes = imageRes,
                            haveSeen = haveSeen,
                            isOnline = isOnline,
                            profilePicUrl = profilePicUrl
                        )
                    }
                }
            }
        }
    }
}