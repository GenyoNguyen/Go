
package com.example.projectse104.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.USER_ID_FIELD
import com.example.projectse104.domain.repository.UserLocationWithLocationListResponse
import com.example.projectse104.domain.use_case.saved_location.GetSavedLocationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SavedLocationViewModel @Inject constructor(
    private val getSavedLocationListUseCase: GetSavedLocationListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var currentUserId: String? = null

    private val _savedLocationListState =
        MutableStateFlow<UserLocationWithLocationListResponse>(Response.Loading)
    val savedLocationListState = _savedLocationListState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            currentUserId = userId
            getSavedLocationList(userId)
        }
    }

    fun getSavedLocationList(userId: String? = currentUserId) {
        userId?.let {
            getSavedLocationListUseCase(it).onEach { result ->
                _savedLocationListState.value = result
            }.launchIn(viewModelScope)
        }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}