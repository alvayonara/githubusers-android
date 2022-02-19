package com.alvayonara.github_apps

import android.view.LayoutInflater
import androidx.navigation.ui.AppBarConfiguration
import com.alvayonara.github_apps.core.base.BaseActivity
import com.alvayonara.github_apps.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {}
}