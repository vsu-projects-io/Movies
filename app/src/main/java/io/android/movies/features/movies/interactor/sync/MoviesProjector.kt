package io.android.movies.features.movies.interactor.sync

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MoviesProjector @Inject constructor(
    private val moviesRemoteMediator: MoviesRemoteMediator,
    private val moviesPagingSource: MoviesPagingSource,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMoviesFlow(): Flow<PagingData<MoviePreview>> = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_NUMBER,
            initialLoadSize = DEFAULT_PAGE_NUMBER,
        ),
        remoteMediator = moviesRemoteMediator,
        pagingSourceFactory = { moviesPagingSource }
    ).flow

    private companion object {
        const val DEFAULT_PAGE_NUMBER = 20
    }
}