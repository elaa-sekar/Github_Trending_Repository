package com.demo.trendinggithubrepo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.repositories.HomeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class GitHubTrendingRepoViewModel(val repository: HomeRepository) : ViewModel() {

    var listener: GitHubRepoListener? = null

    //Coroutine Error/Exception Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception : $throwable")
//            Coroutines.main { showMessage(throwable.toString()) }
        }

    // API/Network call
    fun getTrendingRepoList(searchQuery: String) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val repoList = repository.getRepositoriesList("", "", searchQuery)
            withContext(Dispatchers.Main) {
                if (repoList.isNotEmpty()) {
                    listener?.updateRepoAdapter(repoList as ArrayList<GitHubRepo>)
                } else listener?.showMessage("No repositories found")
                listener?.stopLoader()
            }
        }
    }
}