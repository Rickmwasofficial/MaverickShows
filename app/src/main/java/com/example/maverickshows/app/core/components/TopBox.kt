package com.example.maverickshows.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.maverickshows.R

@Composable
fun TopBox(img: String, title: String, cats: List<String>, modifier: Modifier = Modifier, isFullDesc: Boolean = false, isActor: Boolean = false, navigateBack: () -> Unit = { }, avg: Double = 0.0, likeTrigger: () -> Unit? = {  }, isLiked: Boolean = false) {
    Box(
        modifier = modifier.fillMaxWidth().height(470.dp).background(Color.Black),
    ) {
        AsyncImage(
            model = if (isFullDesc || isActor) {
                ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w780/$img")
                    .crossfade(true)
                    .fallback(R.drawable.fallback)
                    .crossfade(1000)
                    .build()
            } else {
                ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original/$img")
                    .crossfade(true)
                    .fallback(R.drawable.fallback)
                    .crossfade(1000)
                    .build()
            },
            contentDescription = title,
            contentScale = ContentScale.Crop,
            alpha = 0.59f,
            modifier = Modifier.fillMaxSize(),
        )
        if (isFullDesc || isActor) {
            Row(
                modifier = modifier.fillMaxWidth().padding(horizontal = 25.dp, vertical = 45.dp).align(Alignment.TopStart).height(35.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(17.dp)).size(35.dp).fillMaxWidth()
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "View more"
                    )
                }
                if (isFullDesc && !isActor) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val icon = if (isLiked) Icons.Filled.Favorite else Icons.Rounded.FavoriteBorder
                        IconButton(
                            onClick = { likeTrigger() },
                            modifier = modifier
                                .background(
                                    Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .height(40.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isLiked) Color.Green else Color.White
                            )
                        }
                        Card(
                            modifier = Modifier.width(60.dp).height(25.dp).fillMaxHeight(),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = avg.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxSize().padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.align(alignment = Alignment.BottomCenter).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isFullDesc || isActor) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
            LazyRow(
                modifier = Modifier.padding(bottom = 10.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(cats.size) { cat ->
                    Button(
                        onClick = { },
                        modifier = Modifier.height(30.dp).padding(horizontal = 1.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0.3f, 0.3f, 0.3f, 0.6f),
                            disabledContainerColor = Color(0.3f, 0.3f, 0.3f, 0.5f),
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        enabled = false,
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
                    ) {
                        Text(
                            text = cats[cat],
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TopBoxPreview(){
//    MaverickShowsTheme {
//        TopBox(R.drawable.peaky, R.string.movie_name, isFullDesc = true)
//    }
//}