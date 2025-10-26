package com.example.shoppingapp.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.repository.AuthRepository
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.presentation.util.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validator: Validator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginFormState = _loginUiState.asStateFlow()

//    init {
//        checkLoginStatus()
//    }
//
//    private fun checkLoginStatus() {
//        viewModelScope.launch {
//            when (authRepository.isLogin()) {
//                is Response.Success -> {
//                    _loginUiState.update { it.copy(navigateToHome = true) }
//                }
//                else -> {
//                    // Not logged in, do nothing
//                }
//            }
//        }
//    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                _loginUiState.update { it.copy(email = event.email, emailError = null) }
            }
            is LoginUiEvent.PasswordChanged -> {
                _loginUiState.update { it.copy(password = event.password, passwordError = null) }
            }
            LoginUiEvent.Submit -> {
                val email = _loginUiState.value.email
                val password = _loginUiState.value.password

                val emailResult = validator.emailValidator(email)
                val passwordResult = validator.passwordValidator(password)

                if (emailResult.successful && passwordResult.successful) {
                    submitData(email, password)
                } else {
                    _loginUiState.update {
                        it.copy(
                            emailError = emailResult.errorMessage,
                            passwordError = passwordResult.errorMessage
                        )
                    }
                }
            }


        }
    }

    private fun submitData(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true, error = null) }

            when (val result = authRepository.loginWithEmail(email, password)) {
                is Response.Success -> {
                    Log.i("LoginWithEmail", "Success ")
                    _loginUiState.update { it.copy(isLoading = false, error = null, navigateToHome = true) }
                }
                is Response.Error -> {
                    Log.i("LoginWithEmail", result.message.toString())
                    _loginUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Login failed"
                        )
                    }
                }

                is Response.Loading<*> -> {
                    Log.i("LoginWithEmail", "Loading")
                    _loginUiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }
}
