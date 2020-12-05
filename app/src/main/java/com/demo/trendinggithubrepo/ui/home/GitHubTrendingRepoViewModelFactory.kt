package com.demo.trendinggithubrepo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.trendinggithubrepo.repositories.HomeRepository

@Suppress("UNCHECKED_CAST")
class GitHubTrendingRepoViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GitHubTrendingRepoViewModel(repository) as T
    }

}