package com.example.maverickshows.app.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.LoadingScreen
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.data.RecentSearchEntity
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.presentation.HomeUIState
import java.util.Locale

@Composable
fun SearchUiScreen(searchViewModel: SearchViewModel, navigateToDetail: (String, String) -> Unit, modifier: Modifier = Modifier) {
    val searchState by searchViewModel.uiState.collectAsState()
    val search by searchViewModel.query.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(top = 35.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
            ) {
                SearchView(search = search, onValueChange = {
                    searchViewModel.updateQuery(it)
                })
            }
            when (searchState) {
                is SearchUIState.Success -> {
                    if (search.isNotEmpty()) {
                        Column(
                            modifier = modifier.fillMaxWidth().padding(start = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            ContentLabel("You searched for: ${search.capitalize(Locale.ROOT)}", { })
                            LazyRow(
                                modifier = Modifier.padding(start = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                contentPadding = PaddingValues(1.dp)
                            ) {
                                var data = (searchState as SearchUIState.Success).data
                                items(data.size) { num ->
                                    val genres = if (data[num].genre.isNotEmpty()) {
                                        searchViewModel.getStringGenre(data[num].genre)
                                    } else {
                                        listOf("Null")
                                    }
                                    MovieCard(data[num].title ?: data[num].name.toString(), data[num].releaseDate, genres[0], data[num].img, false, navigateToDetail = {
                                        searchViewModel.saveItem(RecentSearchEntity(
                                            id = data[num].id.toString(),
                                            title = data[num].title ?: data[num].name.toString(),
                                            type = data[num].type
                                        ))
                                        navigateToDetail(data[num].id.toString(), data[num].type)
                                    })
                                }
                            }
                        }
                        var data = (searchState as SearchUIState.Success).recentSearches
                        if (data.isEmpty()) {
                            NoSearch()
                        } else {
                            RecentSearches(data, searchViewModel, { id: String, type: String ->
                                navigateToDetail(id, type)
                            }, Modifier.weight(1f))
                        }
                    }
                }
                is SearchUIState.Loading -> {
                    LoadingScreen("Fetching Results")
                }
                is SearchUIState.Error -> {
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
                                contentDescription = (searchState as SearchUIState.Error).message,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(46.dp)
                            )
                            Text(
                                text = (searchState as SearchUIState.Error).message,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is SearchUIState.Idle -> {
                    val data = (searchState as SearchUIState.Idle).recentSearches
                    if (data.isEmpty()) {
                        NoSearch()
                    } else {
                        RecentSearches(data, searchViewModel, { id: String, type: String ->
                            navigateToDetail(id, type)
                        }, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun NoSearch(modifier: Modifier = Modifier) {
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
                contentDescription = "No Searches",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(46.dp)
            )
            Text(
                text = "No Recent Searches",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RecentSearches(dbData: List<HomeData>, searchViewModel: SearchViewModel, navigateToDetail: (String, String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Recent Searches", {  })
    }
    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 10.dp).height(80.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        items(dbData, key = { it.id }) {item ->
            SearchedCard(item.img2, item.title ?: item.name.toString(), item.releaseDate, item.type.toUpperCase(), { searchViewModel.deleteItem(
                RecentSearchEntity(
                    id = item.id.toString(),
                    title = item.title ?: item.name.toString(),
                    type = item.type
                )) }, {
                navigateToDetail(item.id.toString(), item.type)
            },
                Modifier.animateItem())
        }
    }
    Button(
        onClick = { searchViewModel.deleteAll() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
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

@Composable
fun SearchedCard(img: String, title: String, genre: String, overview: String, onClick: () -> Unit, goToDetail: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        onClick = { goToDetail() },
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original/$img")
                        .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                alpha = 1f,
                modifier = Modifier.height(80.dp).width(100.dp).clip(RoundedCornerShape(topStart = 1.dp, bottomStart = 1.dp)),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(3.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                )
                Text(
                    text = overview,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier,
                    maxLines = 2
                )
                Text(
                    text = genre,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clickable(onClick = { onClick() })
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = search,
        onValueChange = onValueChange,
        trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
        placeholder = { Text(text = "Search", style = MaterialTheme.typography.bodyMedium) },
        modifier = modifier.fillMaxWidth().height(50.dp).padding(0.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            errorCursorColor = MaterialTheme.colorScheme.error,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorLeadingIconColor = MaterialTheme.colorScheme.error,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorTrailingIconColor = MaterialTheme.colorScheme.error,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorLabelColor = MaterialTheme.colorScheme.error,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorPlaceholderColor = MaterialTheme.colorScheme.error,
            focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorSupportingTextColor = MaterialTheme.colorScheme.error,
            focusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorPrefixColor = MaterialTheme.colorScheme.error,
            textSelectionColors = OutlinedTextFieldDefaults.colors().textSelectionColors,
            focusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorSuffixColor = MaterialTheme.colorScheme.error
        ),
        singleLine = true
    )
}