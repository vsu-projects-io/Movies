package io.android.movies.features.movies.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import io.android.movies.R
import io.android.movies.features.movies.screen.components.MovieComponent
import io.android.movies.features.movies.screen.components.RefreshComponent
import io.android.movies.features.movies.screen.models.MovieUi
import kotlinx.coroutines.flow.Flow

@Composable
internal fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    val moviesPagingFlow = viewModel.moviesFlow

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            // TODO: Добавить строку поиска
            MoviesContentState(
                moviesFlow = viewModel.moviesFlow
            )
        }
    }
}

/**
 * Состояние загрузки экрана
 */
@Composable
internal fun MoviesLoadingState() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

/**
 * Состояние ошибки экрана
 * @param onRefreshClicked Функция обновления
 */
@Composable
internal fun MoviesErrorState(
    onRefreshClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.movies_refresh_text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = onRefreshClicked,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.movies_refresh_text)
            )
        }
    }
}

/**
 * Состояние отображения списка фильмов
 */
@Composable
internal fun MoviesContentState(
    moviesFlow: Flow<PagingData<MovieUi>>
) {
    val movies = moviesFlow.collectAsLazyPagingItems()
    val loadState = movies.loadState.mediator
    
    // LazyVerticalGrid(columns = GridCells.Fixed(2)) {
    //     if (movies.loadState.refresh is LoadState.Loading) {
    //         item {
    //             RefreshComponent(
    //                 modifier = Modifier
    //                     .fillMaxWidth()
    //                     .padding(horizontal = 16.dp)
    //             )
    //         }
    //     }
    //
    //     items(
    //         count = movies.itemCount
    //     ) { index ->
    //         movies[index]?.let { movie ->
    //             MovieComponent(movie = movie)
    //         }
    //     }
    //
    //     if (movies.loadState.append is LoadState.Loading) {
    //         item {
    //             CircularProgressIndicator(
    //                 modifier = Modifier
    //                     .fillMaxWidth()
    //                     .wrapContentWidth(Alignment.CenterHorizontally)
    //             )
    //         }
    //     }
    // }

    // TODO: обучный список
    LazyColumn {
        if (loadState?.refresh is LoadState.Loading) {
            item {
                RefreshComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        items(
            count = movies.itemCount
        ) { index ->
            movies[index]?.let { movie ->
                MovieComponent(movie = movie)
            }
        }

        if (loadState?.append is LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
