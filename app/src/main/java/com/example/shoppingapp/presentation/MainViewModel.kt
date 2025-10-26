package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    authRepository: AuthRepository
): ViewModel() {

    val isLoggedIn: StateFlow<Boolean?> = authRepository.isLogin()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null // default
        )

}