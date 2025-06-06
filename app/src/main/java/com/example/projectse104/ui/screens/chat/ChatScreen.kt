package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.projectse104.Component.BottomNavigationBar
import com.example.projectse104.Component.Header
import com.example.projectse104.Component.ShimmerScreen
import com.example.projectse104.Component.ToastMessage
import com.example.projectse104.R
import com.example.projectse104.core.Response
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.ui.screens.chat.Component.ChatItem

@Composable
fun ChatScreen(
    navController: NavController,
    userId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val conversationListState by viewModel.conversationListState.collectAsStateWithLifecycle()
    var isLoading = true
    var conversationWithLastMessageList = listOf<ConversationWithLastMessage>()
    var showErrorToast = false

    when (val state = conversationListState) {
        is Response.Success -> {
            println("Fetched conversations")
            conversationWithLastMessageList = state.data.orEmpty()
            isLoading = false
        }
        is Response.Loading -> {
            println("Loading conversations")
            isLoading = true
        }
        is Response.Failure -> {
            println("Error: ${state.e}")
            showErrorToast = true
            isLoading = false
        }
        else -> {
            println("Idle state")
            showErrorToast = true
            isLoading = false
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
            ShimmerScreen(navController, userId, 2, "Chat", R.drawable.chat_icon_header)
        } else {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController, userId, 2)
                },
                containerColor = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Header("Chat", R.drawable.chat_icon_header)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp)
                    ) {
                        for (conversation in conversationWithLastMessageList) {
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
                            ChatItem(
                                navController = navController,
                                userId = userId,
                                otherId = otherId,
                                otherName = name,
                                message = message,
                                time = time,
                                imageRes = imageRes,
                                haveSeen = haveSeen,
                                isOnline = isOnline
                            )
                        }
                    }
                }
            }
        }
        if (showErrorToast) {
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }
    }
}