package com.masterproject.jetpackandmacrobenchmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter


// Shop.kt
data class Shop(
    val id: Int,
    val name: String,
    val address: String,
    val rating: Float,val image:String

)

@Composable
fun ShopList(shops: List<Shop>) {
    LazyColumn {
        items(shops) { shop ->
            ShopItem(shop = shop)
        }
    }
}


@Composable
fun ShopItem(shop: Shop) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f) // Maintain aspect ratio
                .padding(vertical = 16.dp) // Add vertical padding
                .border(1.dp, Color.Black) // Add border around the image
        ) {
            // Load image using Coil from the provided URL
            Image(
                painter = rememberImagePainter(
                    data = shop.image,
                    builder = {
                        placeholder(R.drawable.ic_launcher_background) // Placeholder image resource
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp), // Add padding to ensure the border is visible
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = shop.name,
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Address: ${shop.address}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display rating stars
        StarRating(shop = shop)
    }
}

@Composable
fun StarRating(shop: Shop) {
    val filledStars = (shop.rating / 5 * 5).toInt()
    val emptyStars = 5 - filledStars

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Rating: ${shop.rating}",
            fontSize = 16.sp,
            color = Color.Gray
        )
        repeat(filledStars) {
            StarIcon(painterResource(id = R.drawable.ic_star_filled))
            Spacer(modifier = Modifier.width(4.dp))
        }
        repeat(emptyStars) {
            StarIcon(painterResource(id = R.drawable.ic_star_outine))
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun StarIcon(imageVector: Painter) {
    Image(
        painter =imageVector,
        contentDescription = null,

        modifier = Modifier.size(16.dp)

    )
}