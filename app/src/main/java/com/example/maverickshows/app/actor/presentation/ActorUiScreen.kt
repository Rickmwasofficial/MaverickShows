package com.example.maverickshows.app.actor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.LoadingScreen
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.details.presentation.CastCard
import com.example.maverickshows.app.details.presentation.DetailUiState
import com.example.maverickshows.app.details.presentation.ImageRows
import com.example.maverickshows.ui.theme.MaverickShowsTheme

@Composable
fun ActorUiScreen(
    id: String,
    navigateToBack: () -> Unit,
    navigateToActor: (String) -> Unit,
    actorViewModel: ActorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by actorViewModel.uiState.collectAsState()
    LaunchedEffect(id) {
        actorViewModel.getActorDetails(id)
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when (uiState) {
            is ActorUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    item {
                        TopBox((uiState as ActorUiState.Success).data.profile, (uiState as ActorUiState.Success).data.name, isActor = true, cats = listOf((uiState as ActorUiState.Success).data.department), navigateBack = { navigateToBack() })
                    }
                    item {
                        ActorBio((uiState as ActorUiState.Success).data.biography)
                    }
                    item {
                        ActorDetails((uiState as ActorUiState.Success).data)
                    }
                    item {
                        ActorImageRows((uiState as ActorUiState.Success).img)
                    }
                    item {
                        Filmography("Filmography", { }, false)
                    }
                }
            }
            is ActorUiState.Loading -> {
                LoadingScreen("Fetching Person Details")
            }
            is ActorUiState.Error -> {
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
                            text = (uiState as ActorUiState.Error).message,
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
fun ActorImageRows(data: ActorImages, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "Images",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(data.profiles.size) { num ->
                MovieCard("", "", "", data.profiles[num].path, true, navigateToDetail = { }, isImageData = true)
            }
        }
    }
}

@Composable
fun ActorBio(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text(
            text = "Biography",
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
fun DetailItem(title: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall
        )
    }
    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().border(1.dp, color = MaterialTheme.colorScheme.onSecondaryContainer))
}

@Composable
fun ActorDetails(data: ActorData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        DetailItem("Date of Birth", data.birthday)
        DetailItem("Gender", data.gender)
        DetailItem("Place", data.birthPlace)
    }
}

@Composable
fun Filmography(title: String, onClick: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 5.dp),
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

@Preview
@Composable
fun ActorPreview() {
    MaverickShowsTheme {
//        ActorUiScreen({ }, { })
    }
}