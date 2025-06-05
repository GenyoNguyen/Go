package com.example.projectse104.ui.screens.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.ConversationsWithLastMessageResponse
import com.example.projectse104.domain.use_case.conversation.GetConversationListWithLastMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getConversationListWithLastMessageUseCase:
    GetConversationListWithLastMessageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _conversationListState =
        MutableStateFlow<ConversationsWithLastMessageResponse>(Response.Loading)
    val conversationListState = _conversationListState.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getConversationList(userId)
        }
    }

    private fun getConversationList(userId: String) {
        viewModelScope.launch {
            getConversationListWithLastMessageUseCase(userId)
                .collect { response ->
                    _conversationListState.value = response
                }
        }
    }
}
