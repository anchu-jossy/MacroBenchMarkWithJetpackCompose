package com.masterproject.jetpackandmacrobenchmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
@OptIn(ExperimentalComposeUiApi::class)

// Shop.kt
data class Shop(
    val id: Int,
    val name: String,
    val address: String,
    val rating: Float,
    val image: String
)

@Composable
fun ShopList(shops: List<Shop>) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },singleLine = true, // Ensure it remains single line
            maxLines = 1, // Limit to one line
            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filteredShops = search(shops, searchText)
        LazyColumn (  modifier = Modifier
            .testTag("shop_list")){
            items(filteredShops) { shop ->
                ShopItem(shop = shop)

            }
        }
    }
}

fun search(shops: List<Shop>, query: String): List<Shop> {
    return shops.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.address.contains(query, ignoreCase = true)
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
        painter = imageVector,
        contentDescription = null,
        modifier = Modifier.size(16.dp)
    )
}
