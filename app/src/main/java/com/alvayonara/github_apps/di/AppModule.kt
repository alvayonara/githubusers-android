package com.alvayonara.github_apps.di

import com.alvayonara.github_apps.core.domain.usecase.UserInteractor
import com.alvayonara.github_apps.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase
}