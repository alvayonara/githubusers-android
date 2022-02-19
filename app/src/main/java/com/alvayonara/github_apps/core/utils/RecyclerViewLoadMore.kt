package com.alvayonara.github_apps.core.utils

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewLoadMore : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}