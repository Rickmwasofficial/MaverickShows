package com.example.maverickshows.app.home.data

import com.example.maverickshows.app.core.data.FavoritesDao
import com.example.maverickshows.app.core.data.FavoritesEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData
import kotlinx.coroutines.flow.Flow

class HomeDataRepositoryImpl(
    private val api: TmdbAPI
): HomeDataRep {

    private fun <T> List<T>.filterValidDisplayItems(
        getId: (T) -> Int?,
        getTitleOrName: (T) -> String?,
        getImg: (T) -> String?,
        getBackgroundImg: (T) -> String? // NEW: Added background image parameter
    ): List<T> {
        return this.filter { item ->
            val isValid = getId(item) != null &&
                    getTitleOrName(item)?.isNotBlank() == true &&
                    getImg(item)?.isNotBlank() == true &&
                    getBackgroundImg(item)?.isNotBlank() == true // NEW: Added check for background image
            isValid
        }
    }


    override suspend fun getAllPopular(page: String): List<HomeData> {
        val popularTvResults = api.getPopularTv(page = page).results
        val popularMoviesResults = api.getPopularMovies(page = page).results

        return (popularTvResults
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name }, // Use 'name' for Tv
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() } +
                popularMoviesResults
                    .filterValidDisplayItems(
                        getId = { it.id },
                        getTitleOrName = { it.title }, // Use 'title' for Movie
                        getImg = { it.img },
                        getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
                    )
                    .map { it.toHomeData() }
                ).shuffled()
    }

    override suspend fun getAllTopRated(page: String): List<HomeData> {
        val topRatedTvResults = api.getTopRatedTv(page = page).results
        val topRatedMoviesResults = api.getTopRatedMovies(page = page).results

        return (topRatedTvResults
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() } +
                topRatedMoviesResults
                    .filterValidDisplayItems(
                        getId = { it.id },
                        getTitleOrName = { it.title },
                        getImg = { it.img },
                        getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
                    )
                    .map { it.toHomeData() }
                ).shuffled()
    }

    override suspend fun getTrendingMovies(page: String): List<HomeData> {
        return api.getTrendingMovies(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title }, // Use 'title' for Results (movie type)
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getTrendingTv(page: String): List<HomeData> {
        return api.getTrendingTv(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name }, // Use 'name' for TvResults
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getPopularMovies(page: String): List<HomeData> {
        return api.getPopularMovies(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getUpcomingMovies(page: String): List<HomeData> {
        return api.getUpcomingMovies(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title }, // Assuming movie type
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getTopRatedMovies(page: String): List<HomeData> {
        return api.getTopRatedMovies(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getNowPlayingMovies(page: String): List<HomeData> {
        return api.getNowPlayingMovies(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getPopularTv(page: String): List<HomeData> {
        return api.getPopularTv(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getTopRatedTv(page: String): List<HomeData> {
        return api.getTopRatedTv(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getOnAirTv(page: String): List<HomeData> {
        return api.getOnAirTv(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getAiringTv(page: String): List<HomeData> {
        return api.getAiringTodayTv(page = page).results
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.name },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() }
    }

    override suspend fun getAllTrending(page: String): List<HomeData> {
        val trendingMoviesResults = api.getTrendingMovies(page = page).results
        val trendingTvResults = api.getTrendingTv(page = page).results

        return (trendingMoviesResults
            .filterValidDisplayItems(
                getId = { it.id },
                getTitleOrName = { it.title },
                getImg = { it.img },
                getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
            )
            .map { it.toHomeData() } +
                trendingTvResults
                    .filterValidDisplayItems(
                        getId = { it.id },
                        getTitleOrName = { it.name },
                        getImg = { it.img },
                        getBackgroundImg = { it.backgroundImg } // NEW: Pass backgroundImg
                    )
                    .map { it.toHomeData() }
                ).shuffled()
    }

    override suspend fun getAllGenres(): List<Genre> {
        // Genres are usually robust, but added filter for safety based on your models
        return (api.getTvGenres().genres.filter { it.id != null && it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.id != null && it.name.isNotBlank() })
    }
}