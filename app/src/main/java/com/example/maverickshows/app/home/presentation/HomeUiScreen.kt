package com.example.maverickshows.app.home.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard
import com.example.maverickshows.app.core.components.TopBox

@Composable
fun HomeUiScreen(navigateToExpanded: (String) -> Unit, navigateToDetail: () -> Unit, modifier: Modifier = Modifier) {
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
                TopBox(R.drawable.peaky, R.string.movie_name)
            }
            item {
                CategorySection()
            }
            item {
                MovieRow("For You", { navigateToExpanded("For You") }, { navigateToDetail() }, false)
            }
            item {
                MovieRow("Popular Now", { navigateToExpanded("Popular Now") }, { navigateToDetail() }, true)
            }
            item {
                MovieRow("Top Rated", { navigateToExpanded("Top Rated") }, { navigateToDetail() }, false)
            }
            item {
                MovieRow("Upcoming", { navigateToExpanded("Upcoming") }, { navigateToDetail() }, true)
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
fun MovieRow(title: String, onClick: () -> Unit, navigateToDetail: () -> Unit, expanded: Boolean, modifier: Modifier = Modifier) {
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
                    MovieCard(stringResource(R.string.movie_name), "2019", "Thriller", R.drawable.peaky, expanded, navigateToDetail = { navigateToDetail() })
                }
            }
        }
    }
}