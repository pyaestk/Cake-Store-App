package com.example.shoppingapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.register.component.RegisterFormSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navigateBack: (() -> Unit)? = null,
    navigateToHome: (() -> Unit)? = null,
    registerViewModel: RegisterViewModel = koinViewModel()
) {
    val state by registerViewModel.registerFormState.collectAsState()

    LaunchedEffect(state.isFormValid) {
        if (state.isFormValid) {
            navigateToHome?.invoke()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable { navigateBack?.invoke() }
            )

            Column(
                modifier = Modifier.fillMaxSize().navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RegisterFormSection(
                    onEvent = registerViewModel::onEvent,
                    state = state
                )
            }
        }
    }
}