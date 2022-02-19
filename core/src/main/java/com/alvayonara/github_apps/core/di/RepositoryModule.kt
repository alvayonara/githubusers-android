package com.alvayonara.github_apps.core.di

import com.alvayonara.github_apps.core.data.source.UserRepository
import com.alvayonara.github_apps.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepository): IUserRepository
}