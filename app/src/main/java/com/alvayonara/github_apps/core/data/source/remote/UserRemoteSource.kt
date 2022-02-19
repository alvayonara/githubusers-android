package com.alvayonara.github_apps.core.data.source.remote

import com.alvayonara.github_apps.core.data.source.remote.network.GithubService
import com.alvayonara.github_apps.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.github_apps.core.data.source.remote.response.user.UserResponse
import com.alvayonara.github_apps.core.domain.remote.IUserRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteSource @Inject constructor(
    private val githubService: GithubService
) : IUserRemoteSource {

    override suspend fun getUsers(since: Int): List<UserResponse> =
        githubService.users(since)

    override suspend fun getProfile(username: String): ProfileResponse =
        githubService.profile(username)
}