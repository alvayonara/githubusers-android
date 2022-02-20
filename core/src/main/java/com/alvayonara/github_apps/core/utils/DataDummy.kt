package com.alvayonara.github_apps.core.utils

import com.alvayonara.github_apps.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.github_apps.core.data.source.remote.response.user.UserResponse
import com.alvayonara.github_apps.core.utils.UserMapper.mapProfileResponseToEntities
import com.alvayonara.github_apps.core.utils.UserMapper.mapUserResponseToEntities

object DataDummy {

    fun getUserResponses() = listOf(
        UserResponse(
            avatarUrl = "https://avatars.githubusercontent.com/u/42828307?v=4",
            id = "42828307",
            login = "alvayonara",
            reposUrl = "https://api.github.com/users/alvayonara/repos"
        )
    )

    fun getUser() = getUserResponses().map { it.mapUserResponseToEntities() }

    fun getProfileResponses() = ProfileResponse(
        createdAt = "2018-08-30T05:36:23Z",
        email = "alvayonara@outlook.com",
        login = "alvayonara",
        name = "Alva Yonara Puramandya"
    )

    fun getProfile() = getProfileResponses().mapProfileResponseToEntities()
}