package com.example.maverickshows.app.actor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.details.presentation.CastCard
import com.example.maverickshows.ui.theme.MaverickShowsTheme

@Composable
fun ActorUiScreen(
    navigateToBack: () -> Unit,
    navigateToActor: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
//                TopBox(R.drawable.peaky, R.string.movie_name, isActor = true, navigateBack = { navigateToBack() })
            }
            item {
                ActorDetails()
            }
            item {
                Filmography("Filmography", { }, false)
            }
            item {
                SimilarPersonalities(navigateToActor = { navigateToActor() })
            }
        }
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
fun ActorDetails(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        DetailItem("Date of Birth", "June 9, 1963(age 61)")
        DetailItem("Birth Name", "John Christopher Deep II")
        DetailItem("Place of Birth", "Owensboro, Kentucky, US")
        DetailItem("Height", "5ft 9 (175.3 cm)")
        DetailItem("All Projects", "300, 1983 - 2024")
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

@Composable
fun SimilarPersonalities(navigateToActor: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Similar Personalities", { })
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(50.dp),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp).height(200.dp),
        ) {
            repeat(20) {
                item {
                    CastCard("Erick Mwangi", "Actor", R.drawable.peaky, { navigateToActor() }, Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun ActorPreview() {
    MaverickShowsTheme {
        ActorUiScreen({ }, { })
    }
}