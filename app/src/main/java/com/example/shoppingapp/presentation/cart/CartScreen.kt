package com.example.shoppingapp.presentation.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.CartItemModel
import com.example.shoppingapp.presentation.cart.component.CartItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    paddingValues: PaddingValues,
    viewModel: CartScreenViewModel = koinViewModel(),
    onItemClick: (CartItemModel) -> Unit,
    navigateToPayment: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_delete,
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .align(Alignment.TopEnd).padding(end = 6.dp),
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(
                text = "Your Cart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(R.color.darkBrown))
            }
        }
        if (state.cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cart is Empty",
                )
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                state.cartItems.forEachIndexed { index, cartItem ->
                    CartItem(
                        index = index,
                        cartItem = cartItem,
                        onIncrement = {
                            viewModel.onEvent(CartScreenEvent.IncreaseQuantity(cartItem.itemId))
                        },
                        onDecrement = {
                            viewModel.onEvent(CartScreenEvent.DecreaseQuantity(cartItem.itemId))
                        },
                        onRemove = {
                            viewModel.onEvent(CartScreenEvent.RemoveItem(cartItem.itemId))
                        },
                        onItemClick = {
                            onItemClick(it)
                        }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                PriceInformationSection(state)

                Button(
                    onClick = {
                        navigateToPayment.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,

                    ),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Check Out (${state.cartItems.sumOf { it.quantity }})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }


    }

}

@Composable
fun PriceInformationSection(state: CartScreenState) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Item total:",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Text(
            text = "${state.cartItems.sumOf { it.quantity }}",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
    }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Sub total:",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Text(
            text = "$${state.totalPrice}",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
    }

//    Row(
//        modifier = Modifier,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = "Tax:",
//            color = MaterialTheme.colorScheme.onSurfaceVariant,
//            fontSize = 14.sp
//        )
//
//        Text(
//            text = "$${10.0}",
//            modifier = Modifier.weight(1f),
//            textAlign = TextAlign.End,
//            color = MaterialTheme.colorScheme.onSurface,
//            fontSize = 14.sp
//        )
//    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            .height(1.dp)
    )

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Total Price:",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )

        Text(
            text = "$${state.totalPrice}",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
    }

}

//fun calculateTotalPrice(subTotal: Double): Double {
//    val deliveryFee = 10.0
//    return subTotal + deliveryFee
//}