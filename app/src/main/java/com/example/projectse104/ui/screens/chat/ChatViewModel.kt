package com.example.projectse104.ui.screens.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.ConversationWithLastMessage
import com.example.projectse104.domain.use_case.conversation.SubscribeToConversationsUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val loadUserAva: LoadUserAva,
    private val subscribeToConversationsUseCase: SubscribeToConversationsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _conversationListState =
        MutableStateFlow<List<ConversationWithLastMessage>>(emptyList())
    val conversationListState = _conversationListState.asStateFlow()

    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>?>>(emptyMap())
    val avatarUrls = _avatarUrls.asStateFlow()

    private var _initialized = false

    fun initialize(userId: String) {
        if (_initialized) return
        Log.d("ChatViewModel", "ChatViewModel initialized")
        Log.d("ChatViewModel", "User ID from saved state: $userId")
        getConversationList(userId)
        loadAvatar(userId) // Tải avatar cho người dùng hiện tại
        _initialized = true
    }

    private fun getConversationList(userId: String) {
        subscribeToConversationsUseCase(userId)
            .onEach { response ->
                // Tải avatar cho tất cả otherId khi danh sách cuộc trò chuyện được cập nhật
                if (response is Response.Success) {
                    Log.d("ChatViewModel", "Conversation list updated with response: $response")
                    _conversationListState.update {
                        response.data?.toList() ?: emptyList()
                    }
                    response.data?.forEach { conversation ->
                        val otherId = if (conversation.conversation!!.firstUserId == userId) {
                            conversation.conversation.secondUserId
                        } else {
                            conversation.conversation.firstUserId
                        }
                        loadAvatar(otherId)
                        println("Loaded conversations with length ${response.data.size}")
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadAvatar(userId: String) {
        println("Loading avatar for user $userId...")
        viewModelScope.launch {
            // Kiểm tra xem avatar đã được tải chưa để tránh tải lại
            if (_avatarUrls.value[userId] == null) {
                val result = loadUserAva(userId)
                println("Avatar result for user $userId: $result")
                _avatarUrls.value = _avatarUrls.value.toMutableMap().apply {
                    this[userId] = result
                }
            }
        }
    }
}