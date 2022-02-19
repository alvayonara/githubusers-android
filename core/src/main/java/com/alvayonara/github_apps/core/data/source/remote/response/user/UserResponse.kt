package com.alvayonara.github_apps.core.data.source.remote.response.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    val id: String?,
    val login: String?,
    @Json(name = "repos_url")
    val reposUrl: String?
)