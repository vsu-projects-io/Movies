package io.android.movies.features.movies.interactor.repository.remote

import io.android.movies.features.movies.interactor.repository.remote.dto.response.MoviesDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api для работы с фильмами
 */
internal interface MoviesApi {

    /**
     * Получить фильмы
     * @param page номер страницы
     */
    @GET(MoviesUrls.MOVIES)
    suspend fun getMovies(
        @Query("page") page: Int,
    ): MoviesDataResponse
}