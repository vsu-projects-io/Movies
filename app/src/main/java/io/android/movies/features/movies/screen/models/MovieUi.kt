package io.android.movies.features.movies.screen.models

/**
 * UI модель фильма
 * @property id идентификатор фильма
 * @property name название фильма
 * @property rating рейтинг фильма
 * @property year год выпуска фильма
 * @property posterUrl ссылка на постер фильма
 */
internal data class MovieUi(
    val id: Int,
    val name: String,
    val rating: Double,
    val year: Int,
    val posterUrl: String,
)