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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.maverickshows.app.home.domain.HomeData

@Composable
fun HomeUiScreen(navigateToExpanded: (String) -> Unit, navigateToDetail: () -> Unit, homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val uiState by homeViewModel.uiState.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when (uiState) {
            is HomeUIState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    item {
                        TopBox((uiState as HomeUIState.Success).allTrending[0].img2,
                            ((uiState as HomeUIState.Success).allTrending[0].title ?: (uiState as HomeUIState.Success).allTrending[0].name).toString()
                        )
                    }
                    item {
                        CategorySection()
                    }
//                    item {
//                        MovieRow("For You", { navigateToExpanded("For You") }, { navigateToDetail() }, false)
//                    }
                    item {
                        MovieRow("Popular", (uiState as HomeUIState.Success).allPopular, { navigateToExpanded("For You") }, { navigateToDetail() }, false)
                    }
//                    item {
//                        MovieRow("Popular Now", { navigateToExpanded("Popular Now") }, { navigateToDetail() }, true)
//                    }
                    item {
                        MovieRow("Trending", (uiState as HomeUIState.Success).allTrending, { navigateToExpanded("Top Rated") }, { navigateToDetail() }, true)
                    }
                    item {
                        MovieRow("Top Rated", (uiState as HomeUIState.Success).allTopRated, { navigateToExpanded("Upcoming") }, { navigateToDetail() }, false)
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

@Composable
fun CategoryBtn(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier.padding(horizontal = 1.dp).size(30.dp),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(2.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun CategorySection(modifier: Modifier = Modifier) {
    val cats = listOf<String>("All", "Movies", "Series", "Animations")
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(cats.size) { iter ->
            CategoryBtn({  }, cats[iter], modifier.weight(1f))
        }
    }
}

@Composable
fun MovieRow(title: String, data: List<HomeData>, onClick: () -> Unit, navigateToDetail: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
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
                MovieCard(data[num].title ?: data[num].name.toString(), data[num].releaseDate, "Thriller", if (expanded) data[num].img2 else data[num].img, expanded, navigateToDetail = { navigateToDetail() })
            }
        }
    }
}