package com.example.shoppingapp.presentation.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.address.component.AddressFormSection
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddressScreen(
    navigateBack: (() -> Unit)? = null,
    viewModel: AddressViewModel = koinViewModel()
) {
    val state by viewModel.addressFormState.collectAsState()

    LaunchedEffect(state.isFormValid) {
        if (state.isFormValid) {
            navigateBack?.invoke()

        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { navigateBack?.invoke() },
                )
                Text(
                    text = "Shipping Address",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(top = 48.dp).navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AddressFormSection(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun AddressScreenPreview(){
    ShoppingAppTheme {
        AddressScreen()
    }
}