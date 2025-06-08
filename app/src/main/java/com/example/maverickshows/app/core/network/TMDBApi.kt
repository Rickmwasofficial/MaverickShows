package com.example.maverickshows.app.core.network

import com.example.maverickshows.app.core.models.Actor
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.AllTrending
import com.example.maverickshows.app.core.models.Credits
import com.example.maverickshows.app.core.models.Genres
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.core.models.Movie
import com.example.maverickshows.app.core.models.MovieDetails
import com.example.maverickshows.app.core.models.MovieFilmography
import com.example.maverickshows.app.core.models.NowPlayingAndUpcomingMovies
import com.example.maverickshows.app.core.models.PopularAndTopRatedMovies
import com.example.maverickshows.app.core.models.Trailer
import com.example.maverickshows.app.core.models.TrendingTv
import com.example.maverickshows.app.core.models.Tv
import com.example.maverickshows.app.core.models.TvData
import com.example.maverickshows.app.core.models.TvDetails
import com.example.maverickshows.app.core.models.TvFilmography
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/all/week")
    suspend fun getAllTrending(
        @Query("language") lang: String = "en-US"
    ): AllTrending

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): AllTrending

    @GET("trending/tv/week")
    suspend fun getTrendingTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): TrendingTv

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): NowPlayingAndUpcomingMovies

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): PopularAndTopRatedMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): PopularAndTopRatedMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): NowPlayingAndUpcomingMovies

    @GET("tv/airing_today")
    suspend fun getAiringTodayTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): TvData

    @GET("tv/on_the_air")
    suspend fun getOnAirTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): TvData

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): TvData

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String
    ): TvData

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query("language") lang: String = "en"
    ): Genres

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") lang: String = "en"
    ): Genres

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") id: String,
        @Query("language") lang: String = "en"
    ): MovieDetails

    @GET("tv/{seriesId}")
    suspend fun getTvDetails(
        @Path("seriesId") id: String,
        @Query("language") lang: String = "en"
    ): TvDetails

    @GET("tv/{seriesId}/images")
    suspend fun getTvImages(
        @Path("seriesId") id: String
    ): ImageData

    @GET("movie/{movieId}/images")
    suspend fun getMovieImages(
        @Path("movieId") id: String
    ): ImageData

    @GET("tv/{seriesId}/credits")
    suspend fun getTvCredits(
        @Path("seriesId") id: String
    ): Credits

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") id: String
    ): Credits

    @GET("tv/{seriesId}/recommendations")
    suspend fun getTvRecommendations(
        @Path("seriesId") id: String
    ): TrendingTv

    @GET("movie/{movieId}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movieId") id: String
    ): AllTrending

    @GET("person/{personId}")
    suspend fun getPersonDetails(
        @Path("personId") id: String
    ): Actor

    @GET("person/{personId}/images")
    suspend fun getActorImages(
        @Path("personId") id: String
    ): ActorImages

    @GET("person/{personId}/movie_credits")
    suspend fun getMovieFilmography(
        @Path("personId") id: String
    ): MovieFilmography

    @GET("person/{personId}/tv_credits")
    suspend fun getTvFilmography(
        @Path("personId") id: String
    ): TvFilmography

    @GET("tv/{seriesId}/videos")
    suspend fun getTvTrailers(
        @Path("seriesId") id: String
    ): Trailer

    @GET("movie/{movieId}/videos")
    suspend fun getMovieTrailers(
        @Path("movieId") id: String
    ): Trailer

    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") q: String
    ): PopularAndTopRatedMovies

    @GET("search/tv")
    suspend fun getTvSearch(
        @Query("query") q: String
    ): TvData

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") id: String
    ): MovieDetails

    @GET("tv/{tvId}")
    suspend fun getTvById(
        @Path("tvId") id: String
    ): TvDetails
}