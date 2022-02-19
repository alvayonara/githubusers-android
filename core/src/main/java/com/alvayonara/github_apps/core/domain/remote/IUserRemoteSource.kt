package com.alvayonara.github_apps.core.domain.remote

import com.alvayonara.github_apps.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.github_apps.core.data.source.remote.response.user.UserResponse

interface IUserRemoteSource {

    suspend fun getUsers(since: Int): List<UserResponse>
    suspend fun getProfile(username: String): ProfileResponse
}