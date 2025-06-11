package com.example.maverickshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.maverickshows.app.actor.presentation.ActorUiScreen
import com.example.maverickshows.app.actor.presentation.ActorViewModel
import com.example.maverickshows.app.details.presentation.DetailUiScreen
import com.example.maverickshows.app.details.presentation.DetailViewModel
import com.example.maverickshows.app.expanded.presentation.ExpandedScreen
import com.example.maverickshows.app.favorites.presentation.FavoriteViewModel
import com.example.maverickshows.app.favorites.presentation.FavoritesUiScreen
import com.example.maverickshows.app.home.presentation.HomeUiScreen
import com.example.maverickshows.app.home.presentation.HomeViewModel
import com.example.maverickshows.app.search.presentation.SearchUiScreen
import com.example.maverickshows.app.search.presentation.SearchViewModel
import com.example.maverickshows.ui.theme.MaverickShowsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object Search
@Serializable
object Favorites
@Serializable
data class Expanded(
    val title: String
)
@Serializable
data class Detail(
    val id: String,
    val type: String = "movie"
)
@Serializable
data class Actor(
    val id: String
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            MaverickShowsTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AppNavHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier.height(100.dp),
        contentPadding = PaddingValues(0.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate(Home) },
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                val isSelected = currentRoute?.contains("Home") == true
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Go to Home",
                    modifier = Modifier.size(27.dp),
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Gray
                    }
                )
            }

            // Search Icon
            IconButton(
                onClick = { navController.navigate(Search) },
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                val isSelected = currentRoute?.contains("Search") == true
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Go to Search",
                    modifier = Modifier.size(27.dp),
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Gray
                    }
                )
            }

            // Favorites Icon
            IconButton(
                onClick = { navController.navigate(Favorites) },
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                val isSelected = currentRoute?.contains("Favorites") == true
                Icon(
                    painter = painterResource(R.drawable.bookmark_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Go to Favorites",
                    modifier = Modifier.size(27.dp),
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Gray
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            HomeUiScreen(
                navigateToExpanded = { title: String ->
                    navController.navigate(Expanded(title))
                },
                navigateToDetail = { id: String, type: String ->
                    navController.navigate(Detail(id, type))
                },
                homeViewModel = homeViewModel
            )
        }
        composable<Search> {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchUiScreen(
                searchViewModel = searchViewModel,
                navigateToDetail = { id: String, type: String ->
                    navController.navigate(Detail(id, type))
                },
            )
        }
        composable<Favorites> {

            FavoritesUiScreen(
                favoriteViewModel = favoriteViewModel,
                navigateToDetail = { id: String, type: String ->
                    navController.navigate(Detail(id, type))
                },
            )
        }
        composable<Expanded> { backStackEntry ->
            val route = backStackEntry.toRoute<Expanded>()
            ExpandedScreen(title = route.title, homeViewModel = homeViewModel, navigateBack = { navController.popBackStack() }, navigateToDetail = { id: String, type: String ->
                navController.navigate(Detail(id, type))
            },)
        }
        composable<Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<Detail>()
            val detailViewModel: DetailViewModel = hiltViewModel()
            DetailUiScreen(
                id = route.id,
                type = route.type,
                navigateToBack = { navController.popBackStack() },
                navigateToActor = { id: String -> navController.navigate(Actor(id)) },
                navigateToDetail = { id: String, type: String ->
                    navController.navigate(Detail(id, type))
                },
                detailViewModel = detailViewModel
            )
        }
        composable<Actor> { backStackEntry ->
            val route = backStackEntry.toRoute<Actor>()
            val actorViewModel = hiltViewModel<ActorViewModel>()
            ActorUiScreen(
                id = route.id,
                navigateToBack = { navController.popBackStack() },navigateToDetail = { id: String, type: String ->
                    navController.navigate(Detail(id, type))
                },
                actorViewModel = actorViewModel
            )
        }
    }
}

@Preview
@Composable
fun FullAppPreview() {
    val navController = rememberNavController()
    MaverickShowsTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                BottomNavigationBar(navController = navController)
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            AppNavHost(navController, Modifier.padding(innerPadding))
        }
    }
}