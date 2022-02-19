package com.alvayonara.github_apps.core.data.source

import com.alvayonara.github_apps.core.data.source.remote.UserRemoteSource
import com.alvayonara.github_apps.core.domain.model.profile.Profile
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.domain.repository.IUserRepository
import com.alvayonara.github_apps.core.utils.UserMapper.mapProfileResponseToEntities
import com.alvayonara.github_apps.core.utils.UserMapper.mapUserResponseToEntities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userRemoteSource: UserRemoteSource) :
    IUserRepository {

    override suspend fun getUsers(since: Int): List<User> =
        userRemoteSource.getUsers(since).map { it.mapUserResponseToEntities() }

    override suspend fun getProfile(username: String): Profile =
        userRemoteSource.getProfile(username).mapProfileResponseToEntities()
}