package com.example.projectse104.ui.screens.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import com.example.projectse104.domain.use_case.conversation.GetConversationListWithLastMessageUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val loadUserAva: LoadUserAva,
    private val getConversationListWithLastMessageUseCase: GetConversationListWithLastMessageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _conversationListState = MutableStateFlow<ConversationsWithLastMessageResponse>(Response.Loading)
    val conversationListState = _conversationListState.asStateFlow()

    private val _avatarUrls = MutableStateFlow<Map<String, Response<String>?>>(emptyMap())
    val avatarUrls = _avatarUrls.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getConversationList(userId)
            loadAvatar(userId) // Tải avatar cho người dùng hiện tại
        }
    }

    private fun getConversationList(userId: String) {
        viewModelScope.launch {
            getConversationListWithLastMessageUseCase(userId)
                .collect { response ->
                    _conversationListState.value = response
                    // Tải avatar cho tất cả otherId khi danh sách cuộc trò chuyện được cập nhật
                    if (response is Response.Success) {
                        response.data?.forEach { conversation ->
                            val otherId = if (conversation.conversation!!.firstUserId == userId) {
                                conversation.conversation.secondUserId
                            } else {
                                conversation.conversation.firstUserId
                            }
                            loadAvatar(otherId)
                        }
                    }
                }
        }
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