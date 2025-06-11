package com.example.maverickshows.app.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.LoadingScreen
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.search.presentation.SearchUIState

@Composable
fun FavoritesUiScreen(
    favoriteViewModel: FavoriteViewModel,
    navigateToDetail: (String, String) -> Unit,
    modifier: Modifier = Modifier) {
    val uiState by favoriteViewModel.uiState.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(top = 45.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Column(
                modifier = modifier.fillMaxWidth().padding(horizontal = 1.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                    )
                }
            }
            when(uiState) {
                is FavoriteUiState.Success -> {
                    if ((uiState as FavoriteUiState.Success).favorites.isEmpty()) {
                        NoFavorites()
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            modifier = Modifier.fillMaxHeight().weight(1f).padding(20.dp)
                                .widthIn(max = 330.dp).align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            val data = (uiState as FavoriteUiState.Success).favorites
                            items(data.size) { num ->
                                val genres = if (data[num].genre.isNotEmpty()) {
                                    favoriteViewModel.getStringGenre(data[num].genre)
                                } else {
                                    listOf("Null")
                                }
                                MovieCard(
                                    data[num].title ?: data[num].name.toString(),
                                    data[num].releaseDate,
                                    genres[0],
                                    data[num].img,
                                    false,
                                    navigateToDetail = { navigateToDetail((data[num].id.toString()), data[num].type) })
                            }
                        }
                        Button(
                            onClick = { favoriteViewModel.deleteAll() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Text(
                                text = "Delete all",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier
                            )
                        }
                    }
                }
                is FavoriteUiState.Loading -> {
                    LoadingScreen("Fetching Favorites")
                }
                is FavoriteUiState.Error -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = (uiState as FavoriteUiState.Error).message,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(46.dp)
                            )
                            Text(
                                text = (uiState as FavoriteUiState.Error).message,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoFavorites(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "No Favorites",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(46.dp)
            )
            Text(
                text = "No Favorites",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}