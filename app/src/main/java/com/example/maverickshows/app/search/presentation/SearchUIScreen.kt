package com.example.maverickshows.app.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.maverickshows.R
import com.example.maverickshows.app.core.components.ContentLabel
import com.example.maverickshows.app.core.components.MovieCard

@Composable
fun SearchUiScreen(modifier: Modifier = Modifier) {
    var search by remember { mutableStateOf("") }
    val cats = listOf<String>("Horror", "Drama", "USA", "Animation", "Action", "Documentary")
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(top = 35.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = modifier.fillMaxWidth().padding(horizontal = 25.dp),
            ) {
                SearchView(search = search, onValueChange = {
                    search = it
                })
            }
            Column(
                modifier = modifier.fillMaxWidth().padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ContentLabel("You searched for: ", {  })
                LazyRow(
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(1.dp)
                ) {
                    repeat(10) {
                        item {
                            MovieCard(stringResource(R.string.movie_name), "2019", "Thriller", painterResource(R.drawable.peaky).toString(), false, navigateToDetail = { })
                        }
                    }
                }
            }
            Column(
                modifier = modifier.fillMaxWidth().padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ContentLabel("Filters ", {  })
                LazyRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    contentPadding = PaddingValues(vertical = 1.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cats.size) { cat ->
                        Button(
                            onClick = { },
                            modifier = Modifier.height(30.dp).padding(horizontal = 1.dp),
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
                                text = "${cats[cat]}  x",
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