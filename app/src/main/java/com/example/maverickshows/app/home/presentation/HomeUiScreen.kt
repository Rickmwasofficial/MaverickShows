package com.example.maverickshows.app.home.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R

@Composable
fun HomeUiScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                TopBox(R.drawable.peaky, R.string.movie_name)
            }
        }
    }
}

@Composable
fun TopBox(@DrawableRes img: Int, @StringRes title: Int, modifier: Modifier = Modifier) {
    val cats = listOf<String>("Horror", "Drama", "United States")
    Box(
        modifier = modifier.fillMaxWidth().height(470.dp).background(Color.Black),
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = stringResource(title),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.59f
        )
        LazyRow(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            items(cats.size) { cat ->
                Button(
                    onClick = { },
                    modifier = Modifier.height(30.dp).padding(horizontal = 1.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0.3f, 0.3f, 0.3f, 0.6f),
                        disabledContainerColor = Color(0.3f, 0.3f, 0.3f, 0.8f),
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = false,
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
}