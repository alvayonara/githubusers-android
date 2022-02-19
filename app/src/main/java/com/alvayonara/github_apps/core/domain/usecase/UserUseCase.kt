package com.alvayonara.github_apps.core.domain.usecase

import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User

interface UserUseCase {

    suspend fun getUsers(since: Int): List<User>
    suspend fun getProfile(username: String): Profile
}