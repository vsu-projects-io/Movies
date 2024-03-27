package io.android.movies.features.movies.interactor.repository.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Ответ с фильмами
 * @property total общее количество фильмов
 * @property totalPages общее количество страниц
 * @property movies список фильмов
 */
@Serializable
internal class MoviesDataResponse(
    @SerialName("total")
    val total: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("items")
    val movies: List<MovieResponse>
)

/**
 * Фильм
 * @property id идентификатор
 * @property nameRu название на русском
 * @property rating оценка
 * @property year год
 * @property posterUrl ссылка на постер
 */
@Serializable
internal class MovieResponse(
    @SerialName("kinopoiskId")
    val id: Int,
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("nameEn")
    val nameEn: String?,
    @SerialName("nameOriginal")
    val nameOriginal: String?,
    @SerialName("ratingKinopoisk")
    val rating: Double?,
    @SerialName("year")
    val year: Int?,
    @SerialName("posterUrlPreview")
    val posterUrl: String?,
)
