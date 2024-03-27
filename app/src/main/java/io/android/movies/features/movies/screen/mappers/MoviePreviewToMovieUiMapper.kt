package io.android.movies.features.movies.screen.mappers

import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.screen.models.MovieUi
import javax.inject.Inject

internal class MoviePreviewToMovieUiMapper @Inject constructor(
) : (MoviePreview) -> MovieUi {
    override fun invoke(moviePreview: MoviePreview): MovieUi = MovieUi(
        id = moviePreview.id,
        name = moviePreview.name,
        rating = moviePreview.rating,
        year = moviePreview.year,
        posterUrl = moviePreview.previewUrl,
    )
}