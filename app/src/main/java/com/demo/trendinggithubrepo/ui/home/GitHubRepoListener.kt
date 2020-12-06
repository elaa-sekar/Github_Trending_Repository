package com.demo.trendinggithubrepo.ui.home

import com.demo.trendinggithubrepo.data.api_models.GitHubRepo

interface GitHubRepoListener {
    fun updateRepoAdapter(repoList: ArrayList<GitHubRepo>)
    fun showMessage(message: String)
    fun stopLoader()
}