package com.demo.trendinggithubrepo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.demo.trendinggithubrepo.ui.home.GitHubTrendingRepoActivity

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (!isInternetAvailable(it)) {
                (it as GitHubTrendingRepoActivity).getGitHubTrendingRepositories()
                it.viewModel.showRepoListView()
            } else (it as GitHubTrendingRepoActivity).viewModel.hideLoaderLayout()
        }
    }
}