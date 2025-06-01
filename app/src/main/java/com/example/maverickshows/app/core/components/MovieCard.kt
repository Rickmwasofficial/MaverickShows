package com.example.maverickshows.app.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MovieCard(title: String, year: String, genre: String, @DrawableRes img: Int, expanded: Boolean, modifier: Modifier = Modifier, navigateToDetail: () -> Unit = { }) {
    Column(
        modifier = if (expanded) {
            modifier.height(190.dp).clip(RoundedCornerShape(8.dp)).width(250.dp).clickable(onClick = { navigateToDetail() })
        } else {
            modifier.height(230.dp).clip(RoundedCornerShape(8.dp)).clickable(onClick = { navigateToDetail() })
        },
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = if (expanded) {
                Modifier.height(
                    150.dp
                ).fillMaxWidth().clip(RoundedCornerShape(5.dp))
            } else {
                Modifier.height(
                    180.dp
                ).clip(RoundedCornerShape(5.dp))
            }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            modifier = Modifier.padding(start = 1.dp)
        )
        Text(
            text = "$year . $genre",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 1.dp)
        )
    }
}