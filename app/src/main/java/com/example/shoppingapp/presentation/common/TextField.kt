package com.example.shoppingapp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R

@Composable
fun TextField(
    isPw: Boolean,
    modifier: Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    val uiColor = if (isSystemInDarkTheme()) Color.White else Black

    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(
                text = label,
                color = uiColor,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            TextButton(
                onClick = {
                    showPassword = !showPassword
                }
            ) {
                Text(
                    text = trailing,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = uiColor,
                    fontSize = 12.sp
                )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.midBrown),
            unfocusedIndicatorColor = colorResource(R.color.midBrown),
            focusedLabelColor = colorResource(R.color.midBrown),
            unfocusedLabelColor = colorResource(R.color.midBrown)
        ),
        visualTransformation = if (!showPassword && isPw) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )

}