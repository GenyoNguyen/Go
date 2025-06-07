package com.example.projectse104.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    val avatarUrls by viewModel.avatarUrls.collectAsStateWithLifecycle()
    var isLoading = true
    var conversationWithLastMessageList = listOf<ConversationWithLastMessage>()

    when (val state = conversationListState) {
        is Response.Success -> {
            conversationWithLastMessageList = state.data.orEmpty()
            isLoading = false
        }
        is Response.Loading -> {
            isLoading = true
        }
        else -> {
            ToastMessage(
                message = "Không thể tải dữ liệu. Vui lòng thử lại!",
                show = true
            )
        }
    }

    if (isLoading) {
        ShimmerScreen(navController, userId, 2, "Chat", R.drawable.chat_unread_svgrepo_com)
    } else {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, userId, 2)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Header("Chat", R.drawable.chat_icon_header)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(conversationWithLastMessageList.size, key = { conversationWithLastMessageList[it].conversation!!.id }) { index ->
                        val conversation = conversationWithLastMessageList[index]
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

                        // Lấy avatar cho otherId
                        val profilePicUrl = avatarUrls[otherId]?.let { response ->
                            when (response) {
                                is Response.Success -> response.data
                                is Response.Loading -> null // Có thể hiển thị placeholder
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