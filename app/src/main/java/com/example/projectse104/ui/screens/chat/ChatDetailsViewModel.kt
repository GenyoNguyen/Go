package com.example.projectse104.ui.screens.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.model.Conversation
import com.example.projectse104.domain.model.Message
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.use_case.conversation.GetMessageListUseCase
import com.example.projectse104.domain.use_case.conversation.SendMessageUseCase
import com.example.projectse104.domain.use_case.conversation.StartConversationUseCase
import com.example.projectse104.domain.use_case.conversation.SubscribeToMessagesUseCase
import com.example.projectse104.domain.use_case.user.GetUserUseCase
import com.example.projectse104.domain.use_case.user.LoadUserAva
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailsViewModel @Inject constructor(
    private val startConversationUseCase: StartConversationUseCase,
    private val subscribeToMessagesUseCase: SubscribeToMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val loadUserAva: LoadUserAva,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _currentUserId = MutableStateFlow("")
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

    private val _otherUser = MutableStateFlow<User?>(null)
    val otherUser = _otherUser.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _currentConversation = MutableStateFlow<Conversation?>(null)
    val currentConversation: StateFlow<Conversation?> = _currentConversation.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _avatarUrl = MutableStateFlow<Response<String>?>(null)
    val avatarUrl = _avatarUrl.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            _currentUserId.value = userId
        }
        savedStateHandle.get<String>("otherId")?.let { otherId ->
            startConversation(otherId)
            getOtherUser(otherId)
            loadAvatar(otherId)
        }
    }

    private fun getOtherUser(otherUserId: String) {
        getUserUseCase(otherUserId)
            .onEach { result ->
                when (result) {
                    is Response.Success -> {
                        _otherUser.value = result.data!!
                        _isLoading.value = false
                        println("Turned off loading")
                    }

                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    private fun startConversation(otherUserId: String) {
        viewModelScope.launch {
            val conversationResponse =
                startConversationUseCase(_currentUserId.value, otherUserId)
            when (conversationResponse) {
                is Response.Success -> {
                    // Handle successful conversation start, e.g., navigate to chat screen
                    conversationResponse.data?.let {
                        _currentConversation.value = it
                        loadMessages(it.id)
                        subscribeToMessages(it.id)
                    }
                }

                else -> {}
            }
        }
    }

    private fun subscribeToMessages(conversationId: String) {
        viewModelScope.launch {
            subscribeToMessagesUseCase(conversationId)
                .collect { newMessage ->
                    println("New message received: $newMessage")
                    // Handle incoming messages, e.g., update UI or store in database
                    _messages.value = listOf(newMessage) + _messages.value
                }
        }
    }

    private fun loadMessages(conversationId: String) {
        viewModelScope.launch {
            when (val messageResponse = getMessageListUseCase(conversationId)) {
                is Response.Success -> {
                    _messages.value = messageResponse.data ?: emptyList()
                }

                else -> {}
            }
        }
    }

    fun sendMessage(message: String) {
        val conversation = _currentConversation.value ?: return
        val senderId = _currentUserId.value

        if (message.isBlank() || senderId.isEmpty()) return

        viewModelScope.launch {
            sendMessageUseCase(conversation.id, senderId, message)
        }
    }

    private fun loadAvatar(userId: String) {
        println("Loading avatar...")
        viewModelScope.launch {
            val result = loadUserAva(userId)
            println("Avatar result: $result")
            _avatarUrl.value = result
        }
    }
}
