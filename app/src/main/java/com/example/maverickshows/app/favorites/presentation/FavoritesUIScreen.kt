package com.example.maverickshows.app.favorites.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maverickshows.Favorites
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox
import com.example.maverickshows.app.details.presentation.CastAndCrew
import com.example.maverickshows.app.details.presentation.MoreSuggestions
import com.example.maverickshows.app.details.presentation.MovieDesc
import com.example.maverickshows.app.details.presentation.Ratings
import com.example.maverickshows.app.details.presentation.Trailers
import com.example.maverickshows.app.search.presentation.SearchView

@Composable
fun FavoritesUiScreen(modifier: Modifier = Modifier) {
    val cats = listOf<String>("All", "Movies", "Series", "Animations")
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
                LazyRow(
                    modifier = Modifier.padding(start = 20.dp),
                    contentPadding = PaddingValues(vertical = 1.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cats.size) { cat ->
                        Button(
                            onClick = { },
                            modifier = Modifier.height(30.dp).padding(horizontal = 1.dp).width(75.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0.3f, 0.3f, 0.3f, 0.6f),
                                disabledContainerColor = Color(0.3f, 0.3f, 0.3f, 0.8f),
                                contentColor = Color.White,
                                disabledContentColor = Color.White
                            ),
                            enabled = true,
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
                        ) {
                            Text(
                                text = cats[cat],
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                modifier = Modifier.fillMaxHeight().padding(vertical = 10.dp).align(Alignment.CenterHorizontally)
                    .widthIn(max = 340.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                repeat(60) {
                    item {
                        MovieCard(
                            stringResource(R.string.movie_name),
                            "2019",
                            "Thriller",
                            painterResource(R.drawable.peaky).toString(),
                            false,
                            Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                    }
                }
            }
        }
    }
}