package com.example.maverickshows.app.favorites.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoritesUiScreen(modifier: Modifier = Modifier) {
    Text(
        "Hello this is the favorites",
        style = MaterialTheme.typography.labelLarge
    )
}