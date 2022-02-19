package com.alvayonara.github_apps.core.ui.user

import android.content.Context
import com.alvayonara.github_apps.core.R
import com.alvayonara.github_apps.core.databinding.ItemListUserBinding
import com.alvayonara.github_apps.core.domain.model.user.User
import com.alvayonara.github_apps.core.utils.ViewBindingKotlinModel
import com.alvayonara.github_apps.core.utils.cacheImage

class UserModel(
    private val context: Context,
    private val user: User,
    private val onUserClickCallback: ((String) -> Unit)
) : ViewBindingKotlinModel<ItemListUserBinding>(R.layout.item_list_user) {

    override fun ItemListUserBinding.bind() {
        ivProfile.cacheImage(user.avatarUrl, R.color.alto)
        ivProfileInner.cacheImage(user.avatarUrl, R.color.alto)
        tvUsername.text = user.login
        tvId.text = context.getString(R.string.txt_id, user.id)
        tvRepositoryUrl.text = user.reposUrl
        cvUser.setOnClickListener { onUserClickCallback.invoke(user.login) }
    }
}