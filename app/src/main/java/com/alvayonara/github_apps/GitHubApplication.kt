package com.alvayonara.github_apps

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.alvayonara.github_apps.core.di.CoreComponent
import com.alvayonara.github_apps.core.di.DaggerCoreComponent
import com.alvayonara.github_apps.di.AppComponent
import com.alvayonara.github_apps.di.DaggerAppComponent
import timber.log.Timber

class GitHubApplication: Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, coreComponent)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}