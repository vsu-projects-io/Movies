package io.android.movies.features.movies.interactor.repository.local.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object MoviesRepositoryModule {

    @Provides
    fun provideFirebaseDatabase(): DatabaseReference = Firebase.database(
        url = "" // TODO: добавить url к БД firebase
    ).reference
}