package com.example.shoppingapp.presentation.address.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.presentation.address.AddressUiEvent
import com.example.shoppingapp.presentation.address.AddressUiState
import com.example.shoppingapp.presentation.common.TextField

@Composable
fun AddressFormSection(
    state: AddressUiState,
    onEvent: (AddressUiEvent) -> Unit,
) {

    TextField(
        label = "Name",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.name,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.NameChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        label = "Address",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.addressNum,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.AddressChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        label = "Town / City",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.city,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.CityChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        label = "Postcode",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.zipCode,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.PostCodeChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        label = "Country",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.country,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.CountryChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        label = "Phone number",
        trailing = "",
        modifier = Modifier.fillMaxWidth(),
        value = state.phoneNumber,
        isPw = false,
        onValueChange = {
            onEvent(AddressUiEvent.PhoneNumChanged(it))
        }
    )
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 10.dp),
        onClick = {
            onEvent(AddressUiEvent.Submit)
        }
    ) {
        Text(
            text = "Save Changes",
            fontSize = 16.sp
        )
    }

}