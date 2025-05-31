package com.example.maverickshows.app.details.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import com.example.maverickshows.app.home.presentation.MovieRow
import com.example.maverickshows.ui.theme.MaverickShowsTheme

@Composable
fun DetailUiScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                TopBox(R.drawable.peaky, R.string.movie_name, isFullDesc = true)
            }
            item {
                MovieDesc(stringResource(R.string.movie_description))
            }
            item {
                Trailers()
            }
            item {
                Ratings()
            }
            item {
                CastAndCrew()
            }
            item {
                MoreSuggestions("You may also like", { }, false)
            }
        }
    }
}

@Composable
fun MovieDesc(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth().padding(horizontal = 10.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Trailers(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Trailers & Teasers", { })
        LazyRow(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            repeat(10) {
                item {
                    MovieCard("Official Trailer Sn.1", "Jun 24", "2022", R.drawable.peaky, true)
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
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Ratings(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ContentLabel("Ratings", { })
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Rating("8.1", "321 Ratings", Modifier.weight(1f))
                Spacer(
                    Modifier.height(IntrinsicSize.Max).border(1.dp, Color.White)
                )
                Rating("8.1", "321 Ratings", Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CastCard(name: String, title: String, @DrawableRes img: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(80.dp).width(200.dp).background(Color.Transparent, RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)).padding(vertical = 5.dp),
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
fun CastAndCrew(modifier: Modifier= Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
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
                    CastCard("Erick Mwangi", "Actor", R.drawable.peaky, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun MoreSuggestions(title: String, onClick: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
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
            repeat(10) {
                item {
                    MovieCard(stringResource(R.string.movie_name), "2019", "Thriller", R.drawable.peaky, expanded)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailUiPreview() {
    MaverickShowsTheme(darkTheme = true) {
       DetailUiScreen()
    }
}

