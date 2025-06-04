package com.example.maverickshows.app.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieCard(title: String, year: String, genre: String, img: String, expanded: Boolean, modifier: Modifier = Modifier, navigateToDetail: () -> Unit = { }) {
    Column(
        modifier = if (expanded) {
            modifier.height(190.dp).clip(RoundedCornerShape(8.dp)).width(250.dp).clickable(onClick = { navigateToDetail() })
        } else {
            modifier.height(250.dp).clip(RoundedCornerShape(8.dp)).width(145.dp).clickable(onClick = { navigateToDetail() })
        },
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original/$img")
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = if (expanded) {
                Modifier.height(
                    150.dp
                ).fillMaxWidth().clip(RoundedCornerShape(5.dp))
            } else {
                Modifier.height(
                    205.dp
                ).clip(RoundedCornerShape(5.dp))
            }
        )
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                modifier = Modifier.padding(start = 1.dp)
            )
            Text(
                text = "$year . $genre",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(start = 1.dp)
            )
        }
    }
}