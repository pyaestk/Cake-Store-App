package com.example.shoppingapp.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
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

    LaunchedEffect(loginState.navigateToHome) {
        if (loginState.navigateToHome) {
            navigateToHome?.invoke()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Top section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "WELCOME BACK",
                    fontSize = 28.sp,
                    color = colorResource(R.color.midBrown),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                LoginFormSection(
                    loginUiState = loginState,
                    onEvent = loginViewModel::onEvent
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Or continue with",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFF64748B),
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                SocialMediaLogin(
                    modifier = Modifier,
                    icon = R.drawable.google,
                    text = "Google"
                ) {
                    // connect google

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CreateAccountSection(navigateToRegister = navigateToRegister)
                }
            }
        }
    }
}


@Composable
fun ColumnScope.CreateAccountSection(
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

