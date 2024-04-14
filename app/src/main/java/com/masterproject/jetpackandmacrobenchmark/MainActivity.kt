package com.masterproject.jetpackandmacrobenchmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import com.masterproject.jetpackandmacrobenchmark.ui.theme.JetpackComposeMacrobenchmarkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen {
                setContent {
                    JetpackComposeMacrobenchmarkTheme {
                        var counter by remember { mutableStateOf(0) }
                        val navController = rememberNavController()
                        val scrollState = rememberLazyListState()

                        Column(
                            modifier = Modifier.fillMaxSize()
                                .background(color = Color(255, 229, 204))
                        ) {
                            TopAppBar(
                                title = { Text("Jetpack & Macrobenchmark") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            NavHost(
                                navController = navController,
                                startDestination = "start",
                                modifier = Modifier.weight(1f)
                            ) {
                                composable("start") {
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        Button(onClick = {
                                            counter++
                                            CoroutineScope(Dispatchers.Main).launch {
                                                scrollToFloor(scrollState, counter)
                                            }                                        }) {
                                            Text(text = "EXPLORE FLOORS")
                                        }
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f),
                                            state = scrollState
                                        ) {
                                            items(counter) {
                                                val text = "Floor $it"
                                                val randomColor = Color(
                                                    red = Random.nextFloat(),
                                                    green = Random.nextFloat(),
                                                    blue = Random.nextFloat(),
                                                    alpha = 1f
                                                )
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                    backgroundColor = randomColor,
                                                    elevation = 8.dp
                                                ) {
                                                    Text(
                                                        text = text,
                                                        color = Color.White,
                                                        modifier = Modifier
                                                            .padding(16.dp)
                                                            .clickable {
                                                                navController.navigate("shop/$text")
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                composable(
                                    route = "shop/{text}",
                                    arguments = listOf(
                                        navArgument("text") {
                                            type = NavType.StringType
                                        }
                                    )
                                ) {
                                    ShopList(shops = getStaticShops())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Function to generate dummy shop data for demonstration purposes
    private fun getStaticShops(): List<Shop> {
        return listOf(
            Shop(
                1, "Fashion Emporium", "123 Main St", 4.5f,
                "https://img.freepik.com/free-photo/cheerful-happy-woman-enjoying-shopping-summer-sale-she-is-carrying-shopping-bags-walking_74952-3018.jpg?t=st=1710765372~exp=1710768972~hmac=8406fa5df800660d681b2359c4b038b3918957d7aef802a0cb83d8229347c5d5&w=900",
            ),
            Shop(
                2, "Dominos", "456 Elm St", 4.2f,
                "https://img.freepik.com/free-photo/pizza-pizza-filled-with-tomatoes-salami-olives_140725-1200.jpg?w=1380&t=st=1710765258~exp=1710765858~hmac=289cae493695f77e7f6ce7e062583f2afbd35a3d0d9144ca76a0920e8201de59",
            ),
            // Add more shops as needed
        )
    }

    @Composable
    fun SplashScreen(
        onAnimationEnd: () -> Unit
    ) {
        val alpha = remember { Animatable(0f) }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
            delay(1500)
            onAnimationEnd()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        )
    }

    // Function to scroll to the newly added floor
    private suspend fun scrollToFloor(scrollState: LazyListState, floorNumber: Int) {
        val index = floorNumber - 1 // Adjust index to start from 0
        if (index >= 0) {
            scrollState.scrollToItem(index)
        }
    }
}
