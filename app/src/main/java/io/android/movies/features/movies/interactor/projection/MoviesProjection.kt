package io.android.movies.features.movies.interactor.projection

import io.android.movies.features.movies.interactor.sync.MoviesProjector
import javax.inject.Inject

internal class MoviesProjection @Inject constructor(
    private val moviesProjector: MoviesProjector,
) {

    fun getPagingMoviesFlow() = moviesProjector.getMoviesFlow()
}