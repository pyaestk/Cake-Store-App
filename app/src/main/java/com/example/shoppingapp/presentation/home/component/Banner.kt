package com.example.shoppingapp.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shoppingapp.domain.model.BannerModel


@Composable
fun Banner(
    banners: List<BannerModel>
) {
    val pagerState = rememberPagerState(initialPage = 0){
        banners.size
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ){ index ->
        SliderItem(
            page = banners[index],
            modifier = Modifier
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        PageIndicator(
            modifier = Modifier.width(28.dp),
            pageSize = banners.size,
            selectedPage = pagerState.currentPage)
    }
}
@Composable
fun SliderItem(page: BannerModel, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = page.url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = Color.Black.copy(alpha = 0.2f)),
            contentScale = ContentScale.Crop
        )
    }
}