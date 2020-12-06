package com.demo.trendinggithubrepo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.trendinggithubrepo.repositories.HomeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class GitHubTrendingRepoViewModel(val repository: HomeRepository) : ViewModel(){

    var listener: GitHubRepoListener? = null

    //Coroutine Error/Exception Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception : $throwable")
//            Coroutines.main { showMessage(throwable.toString()) }
        }

    // API/Network call

    fun getTrendingRepoList(searchQuery: String){

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val response = repository.getRepositoriesList("","",searchQuery)

        }
    }
}