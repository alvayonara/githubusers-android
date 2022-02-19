package com.alvayonara.github_apps.di

import android.content.Context
import com.alvayonara.github_apps.core.ui.user.UserController
import dagger.Module
import dagger.Provides

@Module
class EpoxyModule {

    @Provides
    fun provideUserController(context: Context) = UserController(context)
}