package com.alvayonara.github_apps.core.ui.user

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import com.alvayonara.github_apps.core.domain.model.user.User
import javax.inject.Inject

class UserController @Inject constructor(private val context: Context) : EpoxyController() {

    private val _users: MutableList<User> = mutableListOf()
    private var _isLoadMore = true

    lateinit var onUserClickCallback: ((String) -> Unit)

    fun setIsLoadMore(isLoadMore: Boolean) {
        this._isLoadMore = isLoadMore
        requestModelBuild()
    }

    fun setUsers(data: MutableList<User>) {
        this._users.clear()
        this._users.addAll(data)
        requestModelBuild()
    }

    fun addUsers(data: MutableList<User>) {
        this._users.addAll(data)
        requestModelBuild()
    }

    override fun buildModels() {
        HeaderModel().id("header").addIf(this._users.isNotEmpty(), this)

        this._users.forEach {
            UserModel(context, it, onUserClickCallback)
                .id(it.id)
                .addTo(this)
        }

        LoadMoreModel().id("loadMore").addIf(_isLoadMore, this)
    }
}