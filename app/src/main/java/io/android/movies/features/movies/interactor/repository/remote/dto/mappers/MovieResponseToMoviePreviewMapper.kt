package io.android.movies.features.movies.interactor.repository.remote.dto.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.remote.dto.response.MovieResponse
import javax.inject.Inject

/**
 * Маппер из [MovieResponse] в [MoviePreview]
 */
internal class MovieResponseToMoviePreviewMapper @Inject constructor(
) : (MovieResponse) -> MoviePreview {
    override fun invoke(response: MovieResponse): MoviePreview =
        MoviePreview(
            id = response.id,
            name = response.nameRu ?: response.nameEn ?: response.nameOriginal ?: "",
            rating = response.rating ?: 0.0,
            year = response.year ?: 0,
            previewUrl = response.posterUrl ?: ""
        )
}