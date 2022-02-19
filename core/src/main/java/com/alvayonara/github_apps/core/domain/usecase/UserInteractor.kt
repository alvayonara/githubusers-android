package com.alvayonara.github_apps.core.domain.usecase

import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.domain.repository.IUserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository) : UserUseCase {

    override suspend fun getUsers(since: Int): List<User> =
        userRepository.getUsers(since)

    override suspend fun getProfile(username: String): Profile =
        userRepository.getProfile(username)
}