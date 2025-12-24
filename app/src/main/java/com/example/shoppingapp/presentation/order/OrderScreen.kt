package com.example.shoppingapp.presentation.order

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.OrderItemModel
import com.example.shoppingapp.domain.model.OrderModel
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun OrderScreen(
    onBackClick: () -> Unit,
    viewModel: OrderScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadOrders() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ){
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.clickable { onBackClick() }
                )
                Text(
                    text = "Orders",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                state.orders.forEach { order ->
                    OrderCard(
                        order = order,
                        statusText = guessStatus(order),
                    )
                }
            }

            state.error?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }
}

@Composable
fun OrderCard(
    order: OrderModel,
    statusText: String,
) {
    val shape = RoundedCornerShape(15.dp)
    val isClosed = statusText.equals("Closed", ignoreCase = true)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Header: store + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = statusText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isClosed) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(10.dp))

            // Items (like screenshot)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                order.items.forEach { item ->
                    ItemRow(item)
                }
            }


            Spacer(Modifier.height(10.dp))

            // Total row (right aligned)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = money(order.grandTotal),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isClosed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(12.dp))

        }
    }
}

@Composable
private fun ItemRow(item: OrderItemModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rounded square thumbnail like screenshot
        val imgShape = RoundedCornerShape(12.dp)

        Box(modifier = Modifier.size(56.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(imgShape)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, imgShape),
                contentScale = ContentScale.Crop,
            )

            // qty badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-6).dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp)),
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
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = money(item.price),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


private fun money(value: Double): String =
    String.format(Locale.US, "$ %.2f", value)

private fun guessStatus(order: OrderModel): String {
    // Example: you can base on paymentMethod/shippingOption for now
    return if (order.paymentMethod.contains("closed", true)) "Closed" else "Shipped"
}


@Composable
@Preview(showBackground = true)
fun PreviewOrderScreen(){
    ShoppingAppTheme {
        OrderScreen(
            onBackClick = {}
        )
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewOrderCard() {
    ShoppingAppTheme {
        val fake = OrderModel(
            itemsTotal = 41.82,
            shippingFee = 0.0,
            grandTotal = 41.82,
            shippingOption = "Express",
            paymentMethod = "Card",
            items = listOf(
                OrderItemModel(
                    itemId = "1",
                    title = "Artisanal Avocado Oil",
                    subtitle = "Premium French Import...",
                    imageUrl = null,
                    qty = 1,
                    price = 20.99
                ),
                OrderItemModel(
                    itemId = "2",
                    title = "Premium French Imported...",
                    subtitle = "Avocado oil 100ML *1",
                    imageUrl = null,
                    qty = 1,
                    price = 20.99
                )
            )
        )

        Column(Modifier.padding(16.dp)) {
            OrderCard(
                order = fake,
                statusText = "Shipped",
            )
        }
    }
}
