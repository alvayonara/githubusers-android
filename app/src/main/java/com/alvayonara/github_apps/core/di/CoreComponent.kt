package com.alvayonara.github_apps.core.di

import android.content.Context
import com.alvayonara.github_apps.core.domain.repository.IUserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideUserRepository(): IUserRepository
}