package io.android.movies.features.profile.screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.movies.features.profile.interactor.aggregate.ProfileAggregate
import io.android.movies.features.profile.interactor.command.ProfileCommand
import io.android.movies.features.profile.interactor.projection.ProfileProjection
import io.android.movies.features.profile.screen.event.ProfileEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel экрана авторизации
 */
@HiltViewModel
internal class ProfileViewModel @Inject constructor(
  private val profileProjection: ProfileProjection,
  private val profileAggregate: ProfileAggregate,
) : ViewModel() {

  private val _state = MutableStateFlow(ProfileState())
  val state: StateFlow<ProfileState> = _state.asStateFlow()

  private val _event = MutableSharedFlow<ProfileEvent>()
  val event: SharedFlow<ProfileEvent> = _event.asSharedFlow()

   init {
    fetchUserData()
  }

  private fun fetchUserData() {
    profileProjection.getNickname()?.let {
      _state.value = _state.value.copy(nickname = it)
    }
  }

  fun onNicknameChanged(nickname: String) {
    _state.value = _state.value.copy(nickname = nickname)
  }


  fun setNickname() {
    viewModelScope.launch {
      profileAggregate.handleCommand(ProfileCommand.SetNicknameCommand(_state.value.nickname))
    }
  }


   fun logOut() {
    FirebaseAuth.getInstance().signOut();
    viewModelScope.launch {
      profileAggregate.handleCommand(ProfileCommand.LogOutCommand)
      _event.emit(ProfileEvent.LogOutEvent)
    }
  }

}