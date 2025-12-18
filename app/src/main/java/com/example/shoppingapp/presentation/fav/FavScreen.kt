package com.example.shoppingapp.presentation.fav

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.presentation.common.ItemCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavScreen(
    paddingValues: PaddingValues,
    viewModel: FavScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onItemClick: (ItemModel) -> Unit,
) {

    val state by viewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = 16.dp, horizontal = 16.dp)
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
                text = "Favorites",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
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
        }else {
            if (state.items.isNotEmpty()){
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(state.items.size) { index ->
                            ItemCard(
                                item = state.items[index],
                                onClick = onItemClick
                            )
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite items yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }


    }
}
