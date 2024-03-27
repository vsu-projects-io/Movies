package io.android.movies.features.movies.interactor.domain.write

/**
 * Данные фильмов
 * @property movies список фильмов
 * @property total общее количество фильмов
 * @property totalPages общее количество страниц
 */
internal data class MoviesData(
    val movies: List<MoviePreview>,
    val total: Int,
    val totalPages: Int,
)