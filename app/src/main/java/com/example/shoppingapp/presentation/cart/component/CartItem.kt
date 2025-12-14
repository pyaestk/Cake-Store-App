package com.example.shoppingapp.presentation.cart.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.CartItemModel


@Composable
fun CartItem(
    index: Int,
    cartItem: CartItemModel,
    onItemClick: (CartItemModel) -> Unit,
    onIncrement: (CartItemModel) -> Unit,
    onDecrement: (CartItemModel) -> Unit,
    onRemove: (CartItemModel) -> Unit
) {

//    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(
                onClick = {
                    onItemClick(cartItem)
                }
            )
    ) {

        Text(
            text = (index + 1).toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(25.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                ),
            color = MaterialTheme.colorScheme.onSurface
        )
        /*Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
        )*/
        Spacer(modifier = Modifier.size(10.dp))
        AsyncImage(
            model = cartItem.picUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = cartItem.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "$${cartItem.price}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = cartItem.size,
                color =  MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
        Box(
            modifier = Modifier.fillMaxHeight()
        ) {

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp),
                onClick = { onRemove(cartItem) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .clickable { onDecrement(cartItem) }
                ) {
                    Text("-", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold)
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.width(25.dp),
                )

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .clickable { onIncrement(cartItem) }
                ) {
                    Text("+", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold)
                }

            }
        }
    }

}