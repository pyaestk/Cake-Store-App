package com.example.shoppingapp.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.login.component.LoginFormSection
import com.example.shoppingapp.presentation.login.component.SocialMediaLogin
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navigateToRegister: (() -> Unit)? = null,
    navigateToHome: (() -> Unit)? = null,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val loginState by loginViewModel.loginFormState.collectAsState()

    LaunchedEffect(loginState.isFormValid) {
//        loginViewModel.navigationEvent.collectLatest { event ->
//            when (event) {
//                is LoginViewModel.LoginNavigationEvent.NavigateToHome -> {
//                    navigateToHome?.invoke()
//                }
//            }
//        }
        if (loginState.isFormValid){
            navigateToHome?.invoke()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LoginFormSection(
                loginUiState = loginState,
                onEvent = loginViewModel::onEvent
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Or continue with",
                style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFF64748B))
            )
            Spacer(modifier = Modifier.height(16.dp))
            SocialMediaLogin(
                modifier = Modifier, icon = R.drawable.google, text = "Google"
            ) {
                //connect google
            }
            Spacer(modifier = Modifier.height(16.dp))
            CreateAccountSection(
                navigateToRegister = navigateToRegister
            )
        }
    }
}

@Composable
private fun ColumnScope.CreateAccountSection(
    navigateToRegister: (() -> Unit)? = null
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Text(
        modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .clickable {
                navigateToRegister?.invoke()
            },
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF94A3B8),
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Don't have account?")
            }
            withStyle(
                style = SpanStyle(
                    color = uiColor,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" ")
                append("Create account")
            }
        },
        fontSize = 14.sp
    )

}

