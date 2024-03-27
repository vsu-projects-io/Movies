package io.android.movies.features.movies.interactor.repository.remote.dto.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviesData
import io.android.movies.features.movies.interactor.repository.remote.dto.response.MoviesDataResponse
import javax.inject.Inject

/**
 * Маппер из [MoviesDataResponse] в [MoviesData]
 */
internal class MoviesDataResponseToMoviePreviewMapper @Inject constructor(
    private val movieResponseToMoviePreviewMapper: MovieResponseToMoviePreviewMapper,
) : (MoviesDataResponse) -> MoviesData {
    override fun invoke(response: MoviesDataResponse): MoviesData =
        MoviesData(
            movies = response.movies.map(movieResponseToMoviePreviewMapper),
            total = response.total,
            totalPages = response.totalPages,
        )
}