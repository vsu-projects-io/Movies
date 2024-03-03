package io.android.movies.auth.interactor

import io.android.movies.auth.interactor.command.AuthCommand
import io.android.movies.auth.interactor.domain.write.UserLogin
import io.android.movies.auth.interactor.sync.AuthProjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AuthAggregate @Inject constructor(
    private val authProjector: AuthProjector,
) {

    /**
     * Обрабатывает комманду
     */
    suspend fun handleCommand(command: AuthCommand) = when(command) {
        is AuthCommand.Login -> loginUser(command)
    }

    private suspend fun loginUser(command: AuthCommand.Login) =
        withContext(Dispatchers.IO) {
            val userLogin = UserLogin(
                email = command.email,
                password = command.password,
            )
            authProjector.project(userLogin)
        }
}