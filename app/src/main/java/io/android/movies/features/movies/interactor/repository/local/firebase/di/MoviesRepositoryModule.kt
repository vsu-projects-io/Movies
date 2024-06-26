package io.android.movies.features.movies.interactor.repository.local.firebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesDatabaseRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesFirestoreRepository
import io.android.movies.features.movies.interactor.repository.local.firebase.MoviesLocalRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseRepository

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirestoreRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface MoviesRepositoryModule {

    @Binds
    @Singleton
    @DatabaseRepository
    fun bindMoviesDatabaseRepository(
        repository: MoviesDatabaseRepository,
    ): MoviesLocalRepository

    @Binds
    @Singleton
    @FirestoreRepository
    fun bindMoviesFirestoreRepository(
        repository: MoviesFirestoreRepository,
    ): MoviesLocalRepository
}