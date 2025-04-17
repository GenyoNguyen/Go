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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _savedLocationListState =
        MutableStateFlow<UserLocationWithLocationListResponse>(Response.Loading)
    val savedLocationListState = _savedLocationListState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        savedStateHandle.get<String>(USER_ID_FIELD)?.let { userId ->
            getSavedLocationList(userId)
        }
    }

    private fun getSavedLocationList(userId: String) {
        getSavedLocationListUseCase(userId).onEach { result ->
            _savedLocationListState.value = result
        }.launchIn(viewModelScope)
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}