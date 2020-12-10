package com.demo.trendinggithubrepo.ui.home

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.repositories.HomeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class GitHubTrendingRepoViewModel(private val repository: HomeRepository) : ViewModel() {

    var listener: GitHubRepoListener? = null
    var searchFieldVisibility = ObservableField(View.GONE)
    var titleSearchIconVisibility = ObservableField(View.VISIBLE)

    var repoLiveData: LiveData<List<TrendingRepositories>>? = null

    //Coroutine Error/Exception Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception : $throwable")
//            Coroutines.main { showMessage(throwable.toString()) }
        }

    //On click methods
    fun onSearchIconClicked(view: View) {
        searchFieldVisibility.set(View.VISIBLE)
        titleSearchIconVisibility.set(View.GONE)
    }

    fun onBackArrowClicked(view: View) {
        searchFieldVisibility.set(View.GONE)
        titleSearchIconVisibility.set(View.VISIBLE)
    }

    fun onCloseIconClicked(view: View) {
        searchFieldVisibility.set(View.GONE)
        titleSearchIconVisibility.set(View.VISIBLE)
    }

    // API/Network call
    fun getTrendingRepoList() {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val repoList = repository.getRepositoriesList()
            withContext(Dispatchers.Main) {
                if (repoList.isNotEmpty()) {
                    listener?.updateRepoAdapter(repoList as ArrayList<GitHubRepo>)
                } else listener?.showMessage("No repositories found")
                listener?.stopLoader()
            }
        }
    }
}