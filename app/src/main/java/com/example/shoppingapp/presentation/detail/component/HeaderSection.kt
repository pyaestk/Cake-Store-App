package com.example.shoppingapp.presentation.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.shoppingapp.R

@Composable
fun HeaderSection(
    selectedImageUrl: String,
    imageUrls: List<String>,
    onBackClick: () -> Unit,
    onImageSelected: (String) -> Unit,
    onFavClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .padding(bottom = 16.dp)
    ) {
        val (back, fav, mainImage, thumbnail) = createRefs()

        /*Image(
            painter = rememberImagePainter(selectedImageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.brown),
                    *//*shape = RoundedCornerShape(16.dp)*//*
                )
                .constrainAs(mainImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )*/

        AsyncImage(
            model = selectedImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.brown),
                )
                .constrainAs(mainImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .constrainAs(back) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ){
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        onClick = onBackClick
                    )
                    .align(Alignment.TopStart)

            )

            Image(
                painter = painterResource(R.drawable.fav_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        onClick = onFavClick
                    )
                    .align(Alignment.TopEnd)
            )
        }

//        LazyRow(
//            modifier = Modifier
//                .padding(vertical = 16.dp)
//                .background(
//                    color = colorResource(R.color.white).copy(alpha = 0.6f),
//                    shape = RoundedCornerShape(10.dp)
//                )
//                .constrainAs(thumbnail) {
//                    bottom.linkTo(parent.bottom)
//                    end.linkTo(parent.end)
//                    start.linkTo(parent.start)
//                }
//        ) {
//            items(imageUrls) { imageUrl ->
//                ImageThumbnail(
//                    imageUrl = imageUrl,
//                    isSelected = selectedImageUrl == imageUrl,
//                    onClick = {
//                        onImageSelected(imageUrl)
//                    }
//                )
//
//            }
//
//        }

    }

}