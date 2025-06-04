package com.example.maverickshows.app.home.domain

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import com.example.maverickshows.app.home.presentation.ContentUIState
import javax.inject.Inject

data class HomeUseCases(
    val getAllTrending: GetAllTrending,
    val getAllPopular: GetAllPopular,
    val getAllTopRated: GetAllTopRated,
    val getMoviePopular: GetMoviePopular,
    val getMovieTopRated: GetMovieTopRated,
    val getMovieTrending: GetMovieTrending,
    val getMovieUpcoming: GetMovieUpcoming,
    val getMovieNowPlaying: GetMovieNowPlaying,
    val getTvTrending: GetTvTrending,
    val getTvPopular: GetTvPopular,
    val getTvTopRated: GetTvTopRated,
    val getAllGenres: GetAllGenres,
    val getTvAiring: GetTvAiring,
    val getTvOnAir: GetTvOnAir
)

class GetAllTrending(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getAllTrending(page)
    }
}

class GetAllPopular(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getAllPopular(page)
    }
}

class GetAllTopRated(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getAllTopRated(page)
    }
}

class GetMovieTrending(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getTrendingMovies(page)
    }
}

class GetMovieNowPlaying(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getNowPlayingMovies(page)
    }
}

class GetMoviePopular(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getPopularMovies(page)
    }
}

class GetMovieUpcoming(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getUpcomingMovies(page)
    }
}

class GetMovieTopRated(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getTopRatedMovies(page)
    }
}

class GetTvTrending(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getTrendingTv(page)
    }
}

class GetTvAiring(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getAiringTv(page)
    }
}

class GetTvOnAir(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getOnAirTv(page)
    }
}

class GetTvPopular(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getPopularTv(page)
    }
}

class GetTvTopRated(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(page: String): List<HomeData> {
        return repository.getTopRatedTv(page)
    }
}

class GetAllGenres(
    private val repository: HomeDataRepositoryImpl
) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getAllGenres()
    }
}

data class HomeContent(
    val trending: List<HomeData>,
    val popular: List<HomeData>,
    val topRated: List<HomeData>,
    val genres: List<Genre>,
    val upcoming: List<HomeData>,
    val airing: List<HomeData>,
    val onAir: List<HomeData>,
    val nowPlaying: List<HomeData>
)

class GetHomeContentUseCase @Inject constructor(
    private val homeUseCases: HomeUseCases
) {
    suspend operator fun invoke(state: ContentUIState, page: Int): HomeContent {
        lateinit var trending: List<HomeData>
        lateinit var popular: List<HomeData>
        lateinit var topRated: List<HomeData>

        if (state is ContentUIState.All) {
            trending = homeUseCases.getAllTrending(page = page.toString())
            popular = homeUseCases.getAllPopular(page = page.toString())
            topRated = homeUseCases.getAllTopRated(page = page.toString())
        } else if (state is ContentUIState.Movie) {
            trending = homeUseCases.getMovieTrending(page = page.toString())
            popular = homeUseCases.getMoviePopular(page = page.toString())
            topRated = homeUseCases.getMovieTopRated(page = page.toString())
        } else if (state is ContentUIState.Series) {
            trending = homeUseCases.getTvTrending(page = page.toString())
            popular = homeUseCases.getTvPopular(page = page.toString())
            topRated = homeUseCases.getTvTopRated(page = page.toString())
        }

        val genres = homeUseCases.getAllGenres()
        val upcoming = homeUseCases.getMovieUpcoming(page = page.toString())
        val airing = homeUseCases.getTvAiring(page = page.toString())
        val onair = homeUseCases.getTvOnAir(page = page.toString())
        val nowPlaying = homeUseCases.getMovieNowPlaying(page = page.toString())

        return HomeContent(
            trending,
            popular,
            topRated,
            genres,
            upcoming,
            airing,
            onair,
            nowPlaying
        )
    }
}

class RefreshUseCase @Inject constructor(
    private val getHomeContentUseCase: GetHomeContentUseCase
) {
    suspend operator fun invoke(state: ContentUIState): HomeContent {
        val pages = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        val results = getHomeContentUseCase(state, pages.random())
        return results
    }
}