package com.example.shoppingapp.presentation.register.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.common.TextField
import com.example.shoppingapp.presentation.register.RegisterUiEvent
import com.example.shoppingapp.presentation.register.RegisterUiState
import com.example.shoppingapp.ui.theme.BlueGray


@Composable
fun RegisterFormSection(
    state: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
) {

    TextField(
        label = "Email",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.email,
        isPw = false,
        onValueChange = {
            onEvent(RegisterUiEvent.EmailChanged(it))
        }
    )
    if (state.emailError != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.emailError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Password",
        trailing = "Show",
        isPw = true,
        value = state.password,
        onValueChange = {
            onEvent(RegisterUiEvent.PasswordChanged(it))
        }
    )
    if (state.passwordError != null) {
        Text(
            text = state.passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Confirm Password",
        trailing = "Show",
        isPw = true,
        value = state.confirmPassword,
        onValueChange = {
            onEvent(RegisterUiEvent.ConfirmPasswordChanged(it))
        }
    )
    if (state.passwordError != null) {
        Text(
            text = state.passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) BlueGray else colorResource(R.color.midBrown),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 10.dp),
        onClick = {
           onEvent(RegisterUiEvent.Submit)
        }
    ) {
        Text(
            text = "Register",
            fontSize = 16.sp
        )
    }
}