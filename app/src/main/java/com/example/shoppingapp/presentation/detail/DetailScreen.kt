package com.example.shoppingapp.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.presentation.detail.component.HeaderSection
import com.example.shoppingapp.presentation.detail.component.ImageThumbnail
import com.example.shoppingapp.presentation.detail.component.ModelSelector
import com.example.shoppingapp.presentation.detail.component.RatingBarRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    itemId: Int,
    onBackClick: () -> Unit,
    navigateToPayment: (List<CheckoutItem>) -> Unit,
    detailViewModel: DetailScreenViewModel = koinViewModel(),
) {
    val state by detailViewModel.state.collectAsState()

    LaunchedEffect(itemId) {
        detailViewModel.getItemDetail(itemId)
    }

    val context = LocalContext.current

    val item = state.itemModel
    if (item == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Item not found.")
        }
        return
    }


    var selectedImageUrl by remember(item.id) {
        mutableStateOf(item.picUrl.first())
    }

    var selectedModelIndex by remember(item.id) {
        mutableStateOf(0)
    }

    if (item.size.isNotEmpty() && selectedModelIndex >= item.size.size) {
        selectedModelIndex = 0 
    }

    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    if (state.error != null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${state.error}")
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        HeaderSection(
            selectedImageUrl = selectedImageUrl,
            imageUrls = item.picUrl,
            onBackClick = onBackClick,
            onImageSelected = {
                selectedImageUrl = it
            }
        ) {
            detailViewModel.onEvent(
                event = DetailScreenEvent.AddRemoveItemToFav(item)
            )
        }


        //info section
        Column{

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                        shape = RoundedCornerShape(10.dp)
                    ),
            ) {
                items(item.picUrl) { imageUrl ->
                    ImageThumbnail(
                        imageUrl = imageUrl,
                        isSelected = selectedImageUrl == imageUrl,
                        onClick = {
                            selectedImageUrl = imageUrl
                        }
                    )

                }

            }
            Spacer(modifier.padding(vertical = 8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "$${item.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            RatingBarRow(
                rating = item.rating
            )

            Spacer(modifier.padding(vertical = 16.dp))

            SellerInfoSection(
                item
            )


            Spacer(modifier.padding(vertical = 8.dp))

            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier.padding(vertical = 4.dp))
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier.padding(vertical = 8.dp))

            ModelSelector(
                models = item.size, selectedModelIndex = selectedModelIndex, onModelSelected = {
                    selectedModelIndex = it
                })
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        val selectedSize =
                            if (item.size.isNotEmpty() && selectedModelIndex < item.size.size)
                                item.size[selectedModelIndex]
                            else ""

                        val buyNowItems = listOf(
                            CheckoutItem(
                                itemId = item.id,
                                qty = 1,
                                size = selectedSize
                            )
                        )
                        navigateToPayment(buyNowItems)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Buy Now", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                IconButton(
                    onClick = {
                        Toast
                            .makeText(
                                context,
                                "Item added to cart",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        detailViewModel.onEvent(
                            event = DetailScreenEvent.AddItemToCart(
                                id = item.id,
                                quantity = 1,
                                // Defensive check for item.size and selectedModelIndex
                                size = if (item.size.isNotEmpty() && selectedModelIndex < item.size.size) item.size[selectedModelIndex] else ""
                            )
                        )
                    },
                    modifier = Modifier
                        .width(64.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray.copy(alpha = 0.4f)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.basket),
                        contentDescription = null,
                        modifier = Modifier,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SellerInfoSection(item: ItemModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        AsyncImage(
            model = item.sellerPic,
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )
        Text(
            text = item.sellerName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            /*Image(
                painter = painterResource(R.drawable.message),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )
            Image(
                painter = painterResource(R.drawable.call), contentDescription = null
            )*/
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("View Profile", color = MaterialTheme.colorScheme.onBackground)
            }

        }
    }
}
