package io.android.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.android.movies.features.auth.screen.AuthScreen
import io.android.movies.features.movies.screen.MoviesScreen
import io.android.movies.features.reg.screen.RegScreen
import io.android.movies.features.splash.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Movies.route,
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Auth.route) {
            AuthScreen(
                navController = navController
            )
        }
        composable(route = Screens.Reg.route) {
            RegScreen(
                navController = navController
            )
        }
        composable(route = Screens.Movies.route) {
            MoviesScreen(navController = navController)
        }
    }
}