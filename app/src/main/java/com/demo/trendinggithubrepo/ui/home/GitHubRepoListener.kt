package com.demo.trendinggithubrepo.ui.home

import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.TrendingRepositories

interface GitHubRepoListener {
    fun showMessage(message: String)
    fun stopLoader()
    fun updateRepoAdapter(repoList: List<TrendingRepositories>)
}