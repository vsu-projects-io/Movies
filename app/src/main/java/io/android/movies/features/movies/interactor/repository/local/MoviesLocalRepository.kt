package io.android.movies.features.movies.interactor.repository.local

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import io.android.movies.features.movies.interactor.domain.write.MoviePreview
import io.android.movies.features.movies.interactor.domain.write.MoviesData
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKey
import io.android.movies.features.movies.interactor.repository.local.dto.RemoteKeyType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class MoviesLocalRepository @Inject constructor(
    private val database: DatabaseReference,
) {

    suspend fun saveMovies(
        currentPage: Int,
        moviesData: MoviesData
    ) = withContext(Dispatchers.IO) {
        // database.runTransaction(object : Transaction.Handler {
        //     override fun doTransaction(currentData: MutableData): Transaction.Result {
        //         TODO("Not yet implemented")
        //     }
        //
        //     override fun onComplete(
        //         error: DatabaseError?,
        //         committed: Boolean,
        //         currentData: DataSnapshot?
        //     ) {
        //         TODO("Not yet implemented")
        //     }
        // })
        val nextPage = (currentPage + 1).takeIf { nextLoadPage ->
            nextLoadPage <= moviesData.totalPages
        }
        val remoteKey = RemoteKey(
            nextPage = nextPage,
            currentPage = currentPage,
            movies = moviesData.movies
        )
        database.child(CHILD_REMOTE_KEY).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newRemoteKey = buildList {
                        snapshot.getValue(RemoteKeyType())?.let(::addAll)
                        add(remoteKey)
                    }
                    database.child(CHILD_REMOTE_KEY).setValue(newRemoteKey)
                }

                override fun onCancelled(error: DatabaseError) = Unit
            }
        )
        // database.child(CHILD_MOVIES).push().setValue(movies)
    }

    suspend fun clearMovies() = withContext(Dispatchers.IO) {
        database.child(CHILD_REMOTE_KEY).removeValue()
    }

    suspend fun getRemoteKey(
        page: Int
    ): RemoteKey = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            database.child(CHILD_REMOTE_KEY)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val remoteKeys = snapshot.getValue(RemoteKeyType()) ?: emptyList()
                        val remoteKey = remoteKeys.firstOrNull { it.currentPage == page }
                        remoteKey?.let { key ->
                            continuation.resume(key)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(error.toException())
                    }
                })
        }
    }

    private companion object {
        const val CHILD_REMOTE_KEY = "remote_key"
    }
}