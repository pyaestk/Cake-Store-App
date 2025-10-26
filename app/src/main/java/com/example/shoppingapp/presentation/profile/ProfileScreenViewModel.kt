package com.example.shoppingapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<ProfileScreenEvent>()
    val events = _events.asSharedFlow()

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.Logout -> signOut()
            else -> Unit
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}
