package io.android.movies.features.movies.interactor.domain.write

/**
 * Превью фильма
 * @property id идентификатор
 * @property name название
 * @property rating оценка
 * @property year год
 * @property previewUrl ссылка на превью
 */
internal data class MoviePreview(
    val id: Int,
    val name: String,
    val rating: Double,
    val year: Int,
    val previewUrl: String,
)