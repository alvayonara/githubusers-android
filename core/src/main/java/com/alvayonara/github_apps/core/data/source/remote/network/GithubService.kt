package com.alvayonara.github_apps.core.data.source.remote.network

import com.alvayonara.github_apps.core.data.source.remote.response.profile.ProfileResponse
import com.alvayonara.github_apps.core.data.source.remote.response.user.UserResponse
import com.alvayonara.github_apps.core.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun users(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = Constant.Services.PER_PAGE
    ): List<UserResponse>

    @GET("users/{username}")
    suspend fun profile(
        @Path("username") username: String
    ): ProfileResponse
}