package io.android.movies.features.movies.interactor.sync

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.repository.local.MoviesLocalRepository
import io.android.movies.features.movies.interactor.repository.remote.MoviesRemoteRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class MoviesRemoteMediator @Inject constructor(
    private val localRepository: MoviesLocalRepository,
    private val remoteRepository: MoviesRemoteRepository,
) : RemoteMediator<Int, MoviePreview>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviePreview>
    ): MediatorResult {
        val loadPage = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                lastItem.id
            }
        }

        return try {
            val response = remoteRepository.getMovies(page = loadPage)

            if (loadType == LoadType.REFRESH) {
                localRepository.clearMovies()
            }
            localRepository.saveMovies(
                currentPage = loadPage,
                moviesData = response,
            )

            val nextPage = loadPage + 1
            val endOfPaginationReached = response.movies.isEmpty() || response.totalPages <= nextPage
            MediatorResult.Success(
                // endOfPaginationReached = response.nextKey == null
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (ioException: IOException) {
            MediatorResult.Error(ioException)
        } catch (networkException: HttpException) {
            MediatorResult.Error(networkException)
        }
    }
}