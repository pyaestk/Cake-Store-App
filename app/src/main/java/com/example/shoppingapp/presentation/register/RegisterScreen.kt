package com.example.shoppingapp.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    .padding(top = 40.dp)
                    .clickable { navigateBack?.invoke() }
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(top = 48.dp).navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Register Now",
                    fontSize = 28.sp,
                    color = colorResource(R.color.midBrown),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(32.dp))
                RegisterFormSection(
                    onEvent = registerViewModel::onEvent,
                    state = state
                )
            }
        }
    }
}