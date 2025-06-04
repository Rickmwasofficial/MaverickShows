package com.example.maverickshows.app.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.LoadingScreen
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import com.example.maverickshows.app.home.domain.HomeData
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUiScreen(navigateToExpanded: (String) -> Unit, navigateToDetail: (String, String) -> Unit, homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val uiState by homeViewModel.uiState.collectAsState()
    val contentState by homeViewModel.contentState.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when (uiState) {
            is HomeUIState.Success -> {
                PullToRefreshBox(
                    isRefreshing = (uiState as HomeUIState.Success).isRefreshing,
                    onRefresh = { homeViewModel.refresh() },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        item {
                            var idx by remember { mutableIntStateOf(0) }
                            LaunchedEffect(Unit) {
                                while (true) {
                                    delay(12000)
                                    idx = (idx + 1) % (uiState as HomeUIState.Success).allTrending.size
                                }
                            }
                            val genres =
                                homeViewModel.getStringGenre((uiState as HomeUIState.Success).allTrending[idx].genre)
                            TopBox(
                                (uiState as HomeUIState.Success).allTrending[idx].img,
                                ((uiState as HomeUIState.Success).allTrending[idx].title
                                    ?: (uiState as HomeUIState.Success).allTrending[0].name).toString(),
                                cats = genres
                            )
                        }
                        item {
                            CategorySection(contentState, homeViewModel)
                        }
                        item {
                            MovieRow(
                                "Popular",
                                (uiState as HomeUIState.Success).allPopular,
                                { navigateToExpanded("Popular") },
                                { id: String, type: String ->
                                    navigateToDetail(id, type)
                                },
                                false,
                                homeViewModel,
                                Modifier.padding(5.dp)
                            )
                        }
                        item {
                            MovieRow(
                                "Trending",
                                (uiState as HomeUIState.Success).allTrending,
                                { navigateToExpanded("Trending") },
                                { id: String, type: String ->
                                    navigateToDetail(id, type)
                                },
                                true,
                                homeViewModel,
                                Modifier.padding(5.dp)
                            )
                        }
                        item {
                            MovieRow(
                                "Top Rated",
                                (uiState as HomeUIState.Success).allTopRated,
                                { navigateToExpanded("Top Rated") },
                                { id: String, type: String ->
                                    navigateToDetail(id, type)
                                },
                                false,
                                homeViewModel,
                                Modifier.padding(5.dp)
                            )
                        }
                        if (contentState is ContentUIState.Movie || contentState is ContentUIState.All) {
                            item {
                                MovieRow(
                                    "Upcoming",
                                    (uiState as HomeUIState.Success).upcomingMovies,
                                    { navigateToExpanded("Upcoming") },
                                    { id: String, type: String ->
                                        navigateToDetail(id, type)
                                    },
                                    true,
                                    homeViewModel,
                                    Modifier.padding(5.dp)
                                )
                            }
                            item {
                                MovieRow(
                                    "Now Playing",
                                    (uiState as HomeUIState.Success).nowPlaying,
                                    { navigateToExpanded("Now Playing") },
                                    { id: String, type: String ->
                                        navigateToDetail(id, type)
                                    },
                                    false,
                                    homeViewModel,
                                    Modifier.padding(5.dp)
                                )
                            }
                        }
                        if (contentState is ContentUIState.Series || contentState is ContentUIState.All) {
                            item {
                                MovieRow(
                                    "Airing Today",
                                    (uiState as HomeUIState.Success).airingTv,
                                    { navigateToExpanded("Airing Today") },
                                    { id: String, type: String ->
                                        navigateToDetail(id, type)
                                    },
                                    true,
                                    homeViewModel,
                                    Modifier.padding(5.dp)
                                )
                            }
                            item {
                                MovieRow(
                                    "On Air",
                                    (uiState as HomeUIState.Success).onAirTv,
                                    { navigateToExpanded("On Air") },
                                    { id: String, type: String ->
                                        navigateToDetail(id, type)
                                    },
                                    false,
                                    homeViewModel,
                                    Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
                }
            }
            is HomeUIState.Loading -> {
                LoadingScreen("Fetching Data")
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

@Composable
fun CategoryBtn(onClick: () -> Unit, isSelected: Boolean, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier.padding(horizontal = 1.dp).size(30.dp),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(2.dp),
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun CategorySection(contentUIState: ContentUIState, homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val cats = listOf<String>("All", "Movies", "Series")
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(cats.size) { iter ->
            if (contentUIState is ContentUIState.All) {
                CategoryBtn({ homeViewModel.setContentState(cats[iter]) }, cats[iter] == "All", cats[iter], modifier.weight(1f))
            } else if (contentUIState is ContentUIState.Series) {
                CategoryBtn({ homeViewModel.setContentState(cats[iter]) }, cats[iter] == "Series", cats[iter], modifier.weight(1f))
            } else {
                CategoryBtn({ homeViewModel.setContentState(cats[iter]) }, cats[iter] == "Movies", cats[iter], modifier.weight(1f))

            }
        }
    }
}

@Composable
fun MovieRow(title: String, data: List<HomeData>, onClick: () -> Unit, navigateToDetail: (String, String) -> Unit, expanded: Boolean, homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel(title, { onClick() })
        LazyRow(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(data.size) { num ->
                val genres = homeViewModel.getStringGenre(data[num].genre)
                MovieCard(data[num].title ?: data[num].name.toString(), data[num].releaseDate, genres[0], if (expanded) data[num].img2 else data[num].img, expanded, navigateToDetail = { navigateToDetail((data[num].id.toString()), data[num].type) })
            }
        }
    }
}