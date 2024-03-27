package io.android.movies.features.movies.interactor.repository.remote

import io.android.movies.features.movies.interactor.domain.write.MoviesData
import io.android.movies.features.movies.interactor.repository.remote.dto.mappers.MoviesDataResponseToMoviePreviewMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MoviesRemoteRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDataResponseToMoviePreviewMapper: MoviesDataResponseToMoviePreviewMapper,
) {

    /**
     * Получить фильмы
     * @param page номер страницы
     */
    suspend fun getMovies(page: Int): MoviesData = withContext(Dispatchers.IO) {
        val response = moviesApi.getMovies(page)
        moviesDataResponseToMoviePreviewMapper(response)
    }
}