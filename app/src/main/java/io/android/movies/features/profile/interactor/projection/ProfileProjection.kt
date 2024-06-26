package io.android.movies.features.profile.interactor.projection

import android.net.Uri
import io.android.movies.features.profile.interactor.projector.ProfileProjector
import javax.inject.Inject

internal class ProfileProjection @Inject constructor(
  private val projector: ProfileProjector
) {

  /**
   * Получить никнейм
   */
  fun getNickname(): String? {
    return projector.getNickname()
  }

  /**
   * Получить аватар
   */
  fun getAvatar(): Uri? {
    return projector.getAvatar()
  }
}