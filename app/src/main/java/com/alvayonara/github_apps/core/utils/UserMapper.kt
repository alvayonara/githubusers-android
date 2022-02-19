package com.alvayonara.github_apps.core.utils

import com.alvayonara.github_apps.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.github_apps.core.data.source.remote.response.user.UserResponse
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User

object UserMapper {

    fun UserResponse.mapUserResponseToEntities(): User = User(
        avatarUrl = avatarUrl.orEmpty(),
        id = id ?: "-",
        login = login ?: "-",
        reposUrl = reposUrl ?: "-"
    )

    fun ProfileResponse.mapProfileResponseToEntities(): Profile =
        Profile(
            createdAt = createdAt ?: "-",
            email = email ?: "-",
            login = login ?: "-",
            name = name ?: "-"
        )
}