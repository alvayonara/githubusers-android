package com.alvayonara.github_apps.di

import android.content.Context
import com.alvayonara.github_apps.core.di.CoreComponent
import com.alvayonara.github_apps.ui.user.UserFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class, EpoxyModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: UserFragment)
}