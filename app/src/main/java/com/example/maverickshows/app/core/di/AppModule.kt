package com.example.maverickshows.app.core.di

import com.example.maverickshows.app.actor.data.ActorDataRepImpl
import com.example.maverickshows.app.actor.domain.ActorUseCase
import com.example.maverickshows.app.actor.domain.GetActorData
import com.example.maverickshows.app.actor.domain.GetActorFilmography
import com.example.maverickshows.app.actor.domain.GetActorImages
import com.example.maverickshows.app.actor.domain.GetAllActorData
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.details.data.DetailDataRepImpl
import com.example.maverickshows.app.details.domain.DetailUseCase
import com.example.maverickshows.app.details.domain.GetCredits
import com.example.maverickshows.app.details.domain.GetDetails
import com.example.maverickshows.app.details.domain.GetGenres
import com.example.maverickshows.app.details.domain.GetImages
import com.example.maverickshows.app.details.domain.GetRecommendations
import com.example.maverickshows.app.details.domain.GetTrailers
import com.example.maverickshows.app.details.domain.ShowDetail
import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import com.example.maverickshows.app.home.domain.GetAllGenres
import com.example.maverickshows.app.home.domain.GetAllPopular
import com.example.maverickshows.app.home.domain.GetAllTopRated
import com.example.maverickshows.app.home.domain.GetAllTrending
import com.example.maverickshows.app.home.domain.GetHomeContentUseCase
import com.example.maverickshows.app.home.domain.GetMovieNowPlaying
import com.example.maverickshows.app.home.domain.GetMoviePopular
import com.example.maverickshows.app.home.domain.GetMovieTopRated
import com.example.maverickshows.app.home.domain.GetMovieTrending
import com.example.maverickshows.app.home.domain.GetMovieUpcoming
import com.example.maverickshows.app.home.domain.GetTvAiring
import com.example.maverickshows.app.home.domain.GetTvOnAir
import com.example.maverickshows.app.home.domain.GetTvPopular
import com.example.maverickshows.app.home.domain.GetTvTopRated
import com.example.maverickshows.app.home.domain.GetTvTrending
import com.example.maverickshows.app.home.domain.HomeUseCases
import com.example.maverickshows.app.home.domain.RefreshUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApiKey(): String {
        val apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3Y2ZmNGE2NzU4MmQ1MTIzNTg1OWVhMTE5ZDMwYWE0MiIsIm5iZiI6MTcwMzg1MzU1My4yNzQsInN1YiI6IjY1OGViZGYxY2ZlNDhmNjQyNmQ5NWI0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HuFao_QBgA2_FJwXDJUnOHJ8cY97ZyU98H78AU49vvU"
        if (apiKey.isEmpty()) {
            println("API Key is empty")
        }
        return apiKey
    }
    @Provides
    @Singleton
    //Interceptor to add apikey to every request
    fun provideAPiKeyInterceptor(apiKey: String): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()

            // Add the Authorization header with the Bearer token
            val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer $apiKey")
                .build() // Build the request with the new header

            chain.proceed(requestBuilder)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbAPI {
        return retrofit.create(TmdbAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeDataImpl(api: TmdbAPI): HomeDataRepositoryImpl {
        return HomeDataRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDetailDataImpl(api: TmdbAPI): DetailDataRepImpl {
        return DetailDataRepImpl(api)
    }

    @Provides
    @Singleton
    fun provideActorDataImpl(api: TmdbAPI): ActorDataRepImpl {
        return ActorDataRepImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(repositoryImpl: HomeDataRepositoryImpl): HomeUseCases {
        return HomeUseCases(
            getAllTrending = GetAllTrending(repositoryImpl),
            getAllPopular = GetAllPopular(repositoryImpl),
            getAllTopRated = GetAllTopRated(repositoryImpl),
            getMoviePopular = GetMoviePopular(repositoryImpl),
            getMovieTopRated = GetMovieTopRated(repositoryImpl),
            getMovieTrending = GetMovieTrending(repositoryImpl),
            getMovieUpcoming = GetMovieUpcoming(repositoryImpl),
            getMovieNowPlaying = GetMovieNowPlaying(repositoryImpl),
            getTvTrending = GetTvTrending(repositoryImpl),
            getTvPopular = GetTvPopular(repositoryImpl),
            getTvTopRated = GetTvTopRated(repositoryImpl),
            getAllGenres = GetAllGenres(repositoryImpl),
            getTvAiring = GetTvAiring(repositoryImpl),
            getTvOnAir = GetTvOnAir(repositoryImpl)
        )
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(detailDataRepImpl: DetailDataRepImpl): DetailUseCase {
        return DetailUseCase(
            getDetails = GetDetails(detailDataRepImpl),
            getGenres = GetGenres(detailDataRepImpl),
            getImages = GetImages(detailDataRepImpl),
            getCredits = GetCredits(detailDataRepImpl),
            getRecommendations = GetRecommendations(detailDataRepImpl),
            getTrailers = GetTrailers(detailDataRepImpl)
        )
    }

    @Provides
    @Singleton
    fun provideActorUseCase(actorDataRepImpl: ActorDataRepImpl): ActorUseCase {
        return ActorUseCase(
            getActorData = GetActorData(actorDataRepImpl),
            getActorImages = GetActorImages(actorDataRepImpl),
            getActorFilmography = GetActorFilmography(actorDataRepImpl),
            getGenres = com.example.maverickshows.app.actor.domain.GetGenres(actorDataRepImpl)
        )
    }

    @Provides
    @Singleton
    fun provideAllActorData(actorUseCase: ActorUseCase): GetAllActorData {
        return GetAllActorData(actorUseCase)
    }

    @Provides
    @Singleton
    fun provideDetailData(detailUseCase: DetailUseCase): ShowDetail {
        return ShowDetail(detailUseCase)
    }

    @Provides
    @Singleton
    fun provideHomeContentUseCase(homeUseCases: HomeUseCases): GetHomeContentUseCase {
        return GetHomeContentUseCase(homeUseCases)
    }

    @Provides
    @Singleton
    fun provideRefreshUseCase(getHomeContentUseCase: GetHomeContentUseCase): RefreshUseCase {
        return RefreshUseCase(getHomeContentUseCase)
    }
}