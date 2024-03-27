package io.android.movies.features.movies.interactor.repository.local.dto

import io.android.movies.features.movies.interactor.domain.write.MoviePreview

internal class RemoteKey(
    val nextPage: Int?,
    val currentPage: Int?,
    val movies: List<MoviePreview>
)