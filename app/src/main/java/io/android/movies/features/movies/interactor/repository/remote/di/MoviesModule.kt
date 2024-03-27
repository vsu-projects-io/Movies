package io.android.movies.features.movies.interactor.repository.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.movies.interactor.repository.remote.MoviesApi
import io.android.movies.features.movies.interactor.repository.remote.MoviesUrls
import io.android.movies.network.HeaderInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MoviesModule {

    @Provides
    @Singleton
    fun provideKotlinxSerializationParser(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addLoggingInterceptor()
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jsonParser: Json,
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(MoviesUrls.BASE_URL)
            .addConverterFactory(jsonParser.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder =
        apply {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(interceptor)
        }
}