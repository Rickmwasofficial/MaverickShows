package com.example.maverickshows.app.expanded.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.presentation.HomeUIState
import com.example.maverickshows.app.home.presentation.HomeViewModel
import com.example.maverickshows.ui.theme.MaverickShowsTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun ExpandedScreen(
    title: String,
    homeViewModel: HomeViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(top = 45.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = modifier.fillMaxWidth().padding(horizontal = 25.dp),
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(30.dp)).size(40.dp).fillMaxWidth()
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Go Back"
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                )
            }
            when (uiState) {
                is HomeUIState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.fillMaxHeight().padding(vertical = 10.dp).widthIn(max = 320.dp).align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        val data = when (title) {
                            "Popular" -> (uiState as HomeUIState.Success).allPopular
                            "Trending" -> (uiState as HomeUIState.Success).allTrending
                            else -> (uiState as HomeUIState.Success).allTopRated
                        }
                        items(data.size) { num ->
                            val genres = homeViewModel.getStringGenre(data[num].genre)
                            MovieCard(data[num].title ?: data[num].name.toString(), data[num].releaseDate, genres[0], data[num].img, false, navigateToDetail = {  })
                        }
                    }
                }
                is HomeUIState.Loading -> {
                    Text("Loading")
                }
                is HomeUIState.Error -> {
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
                                contentDescription = (uiState as HomeUIState.Error).message,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(46.dp)
                            )
                            Text(
                                text = (uiState as HomeUIState.Error).message,
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

@Preview
@Composable
fun ExpandedPreview() {
    MaverickShowsTheme {
//        ExpandedScreen("Text", {  })
    }
}