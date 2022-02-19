package com.alvayonara.github_apps.core.data.source.remote.response.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "created_at")
    val createdAt: String?,
    val email: String?,
    val login: String?,
    val name: String?
)