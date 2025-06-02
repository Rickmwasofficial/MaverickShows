package com.example.maverickshows.app.home.data

import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData

class HomeDataRepositoryImpl(
    private val api: TmdbAPI
): HomeDataRep {
    override suspend fun getAllPopular(): List<HomeData> {
        return api.getPopularTv().results.map {
            it.toHomeData()
        } + api.getPopularMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getAllTopRated(): List<HomeData> {
        return api.getTopRatedTv().results.map {
            it.toHomeData()
        } + api.getTopRatedMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getTrendingMovies(): List<HomeData> {
        return api.getTrendingMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getTrendingTv(): List<HomeData> {
        return api.getTrendingTv().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getPopularMovies(): List<HomeData> {
        return api.getPopularMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getUpcomingMovies(): List<HomeData> {
        return api.getUpcomingMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getTopRatedMovies(): List<HomeData> {
        return api.getTopRatedMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getNowPlayingMovies(): List<HomeData> {
        return api.getNowPlayingMovies().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getPopularTv(): List<HomeData> {
        return api.getPopularTv().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getTopRatedTv(): List<HomeData> {
        return api.getTopRatedTv().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getOnAirTv(): List<HomeData> {
        return api.getOnAirTv().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getAiringTv(): List<HomeData> {
        return api.getAiringTodayTv().results.map {
            it.toHomeData()
        }
    }

    override suspend fun getAllTrending(): List<HomeData> {
        return api.getTrendingMovies().results.map {
            it.toHomeData()
        } + api.getTrendingTv().results.map {
            it.toHomeData()
        }
    }
}