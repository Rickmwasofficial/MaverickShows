package com.example.maverickshows.app.details.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.LoadingScreen
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.details.domain.DetailData
import com.example.maverickshows.app.home.presentation.HomeUIState
import com.example.maverickshows.ui.theme.MaverickShowsTheme

@Composable
fun DetailUiScreen(
    id: String,
    type: String,
    navigateToBack: () -> Unit,
    navigateToActor: () -> Unit,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by detailViewModel.uiState.collectAsState()
    LaunchedEffect(id, type) {
        if (type.lowercase() == "tv") {
            detailViewModel.getTvDetails(id)
        } else {
            detailViewModel.getMovieDetails(id)
        }
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when (uiState) {
            is DetailUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    item {
                        val genres = mutableListOf<String>()
                        for (genre in (uiState as DetailUiState.Success).data.genres) {
                            genres.add(genre.name)
                        }
                        TopBox((uiState as DetailUiState.Success).data.bg.toString(),
                            (uiState as DetailUiState.Success).data.title.toString(), genres, isFullDesc = true, navigateBack = { navigateToBack() },
                            popularity = (uiState as DetailUiState.Success).data.popularity,
                            avg = (uiState as DetailUiState.Success).data.avg)
                    }
                    item {
                        RuntimeLang((uiState as DetailUiState.Success).data)
                    }
                    item {
                        MovieDesc((uiState as DetailUiState.Success).data.overview.toString())
                    }
                    item {
                        Dates((uiState as DetailUiState.Success).data)
                    }
                    item {
                        Companies((uiState as DetailUiState.Success).data)
                    }
                    item {
                        Ratings((uiState as DetailUiState.Success).data)
                    }
                    item {
                        CastAndCrew(navigateToActor = { navigateToActor() })
                    }
                    item {
                        MoreSuggestions("You may also like", { }, false)
                    }
                }
            }
            is DetailUiState.Loading -> {
                LoadingScreen("Fetching Data")
            }
            is DetailUiState.Error -> {
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
                            contentDescription = "Could Not fetch data",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(46.dp)
                        )
                        Text(
                            text = (uiState as DetailUiState.Error).message,
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
fun MovieDesc(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text(
            text = "Overview",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun Companies(data: DetailData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Production Companies",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify
        )
        LazyRow(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            repeat(data.companies.size) { num ->
                item {
                    Column(
                        modifier = Modifier.width(90.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(65.dp).clip(CircleShape).background(
                                MaterialTheme.colorScheme.onSurfaceVariant)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://image.tmdb.org/t/p/original/${data.companies[num].logo}")
                                    .build(),
                                contentDescription = data.companies[num].name,
                                contentScale = ContentScale.Fit,
                                alpha = 1f,
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                            )
                        }
                        Text(
                            text = data.companies[num].name.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Rating(num: String, desc: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = num,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun Ratings(data: DetailData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Ratings", { })
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Rating(data.avg.toString(), "${data.count} ratings", Modifier.weight(1f))
                Spacer(
                    Modifier.height(IntrinsicSize.Max).border(1.dp, Color.White)
                )
                Rating(data.popularity.toString(), "Popularity", Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun Dates(data: DetailData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            shape = RoundedCornerShape(1.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Rating(data.date.toString(), "Release Date", Modifier.weight(1f))
                Spacer(
                    Modifier.height(IntrinsicSize.Max).border(1.dp, Color.White)
                )
                Rating(data.status.toString(), "Status", Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RuntimeLang(data: DetailData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
            shape = RoundedCornerShape(1.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Rating("${data.runtime} Min", "Runtime", Modifier.weight(1f))
                Spacer(
                    Modifier.height(IntrinsicSize.Max).border(1.dp, Color.White)
                )
                Rating(data.language.toString().toUpperCase(), "Original Language", Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CastCard(name: String, title: String, @DrawableRes img: Int,
             navigateToActor: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(80.dp).width(200.dp).background(Color.Transparent, RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)).padding(vertical = 5.dp).clickable(onClick = { navigateToActor() }),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = name,
            modifier = Modifier.fillMaxHeight().clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        )
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CastAndCrew(navigateToActor: () -> Unit, modifier: Modifier= Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Cast & Crew", { })
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(50.dp),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).height(150.dp),
        ) {
            repeat(20) {
                item {
                    CastCard("Erick Mwangi", "Actor", R.drawable.peaky, { navigateToActor() }, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun MoreSuggestions(title: String, onClick: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel(title, { onClick() })
        LazyRow(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            repeat(10) {
                item {
                    MovieCard(stringResource(R.string.movie_name), "2019", "Thriller", painterResource(R.drawable.peaky).toString(), expanded)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailUiPreview() {
    MaverickShowsTheme(darkTheme = true) {
//       DetailUiScreen({ }, { })
    }
}

