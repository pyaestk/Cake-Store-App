package com.example.shoppingapp.presentation.payment.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PaymentItemModel
import com.example.shoppingapp.presentation.payment.PaymentUiEvent
import com.example.shoppingapp.presentation.payment.PaymentViewModel
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import org.koin.androidx.compose.koinViewModel


@Composable
fun PaymentScreen(
    checkoutItems: List<CheckoutItem>,
    navController: NavController,
    onBackClick: () -> Unit,
    onEditAddress: () -> Unit,
    onEditContact: () -> Unit,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(checkoutItems) {
        if (checkoutItems.isNotEmpty()) {
            viewModel.onEvent(PaymentUiEvent.LoadForItems(checkoutItems))
        }
    }


    val context = LocalContext.current

    val openAlertDialog = remember { mutableStateOf(false) }

    val dotLottieController = remember { DotLottieController() }

    LaunchedEffect(state.lastOrderId) {
        val orderId = state.lastOrderId ?: return@LaunchedEffect
        openAlertDialog.value = true
    }

    if (openAlertDialog.value) {
        // play ONLY after dialog is shown
        LaunchedEffect(Unit) {
            dotLottieController.setLoop(true)
            dotLottieController.setSpeed(1.5f)
            dotLottieController.play()
        }

        Dialog(onDismissRequest = { openAlertDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DotLottieAnimation(
                        source = DotLottieSource.Url(
                            "https://lottie.host/31498cbe-9530-47c9-beeb-23c194189fa7/ynjh8PV20P.lottie"
                        ),
                        controller = dotLottieController,
                        autoplay = true,
                        loop = false,
                        modifier = Modifier.size(120.dp)
                    )

                    Text("Order Success")

                    Button(
                        onClick = {
                            openAlertDialog.value = false
                            viewModel.onEvent(PaymentUiEvent.OrderSuccess)
                            onPayClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }

    val addressUpdated =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("address_updated", false)
            ?.collectAsState()

    LaunchedEffect(addressUpdated?.value) {
        if (addressUpdated?.value == true) {
            viewModel.loadAddress()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("address_updated", false)
        }
    }



    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.clickable { onBackClick() }
                )
                Text(
                    text = "Payment",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            InfoCard(
                title = "Shipping Address",
                body = state.shippingAddress,
                onEdit = onEditAddress
            )

//        InfoCard(
//            title = "Contact Information",
//            body = state.contactText,
//            onEdit = onEditContact
//        )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Items",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                SmallCountPill(count = state.items.size)

                Spacer(Modifier.weight(1f))
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                state.items.forEach { item ->
                    ItemRow(item = item)
                }
            }

            Text(
                text = "Shipping Options",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            ShippingOptionsCard(
                selected = state.selectedShipping,
                onSelected = { option ->
                    viewModel.onEvent(PaymentUiEvent.ChangeShipping(option))
                }
            )

            Text(
                text = "Payment Method",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            PaymentMethodCard(
                selected = state.selectedPaymentMethod,
                onSelected = { method ->
                    viewModel.onEvent(PaymentUiEvent.ChangePaymentMethod(method))
                }
            )

            OrderSummaryCard(
                itemsTotal = state.itemsTotal,
                shippingFee = state.shippingFee,
                grandTotal = state.grandTotal
            )

            PayButton(
                total = state.grandTotal,
                onPayClick = {
                    viewModel.onEvent(PaymentUiEvent.Pay)
                }
            )

        }
    }


}


@Composable
private fun SmallCountPill(count: Int) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = count.toString(),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ItemRow(item: PaymentItemModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(52.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                contentScale = ContentScale.Crop,
            )

            // qty badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.qty.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = item.price.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
