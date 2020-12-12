package com.demo.trendinggithubrepo.ui.home

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.repositories.HomeRepository
import com.demo.trendinggithubrepo.utils.toTrendingRepositories
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class GitHubTrendingRepoViewModel(private val repository: HomeRepository) : ViewModel() {

    var listener: GitHubRepoListener? = null
    var searchFieldVisibility = ObservableField(View.GONE)
    var titleSearchIconVisibility = ObservableField(View.VISIBLE)
    var noInternetVisibility = ObservableField(View.GONE)
    var loaderLayoutVisibility = ObservableField(View.GONE)
    var loaderVisibility = ObservableField(View.GONE)

    var repoLiveData: LiveData<List<TrendingRepositories>>

    init {
        repoLiveData = repository.getAllRepos()
    }

    //Coroutine Error/Exception Handler
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.d("Coroutine Exception : $throwable")
            viewModelScope.launch(Dispatchers.Main) {
                listener?.stopLoader()
                if(throwable.toString().contains("internet", true)){
                    showLoaderLayout()
                }
            }
        }

    //On click methods
    fun onSearchIconClicked(view: View) {
        searchFieldVisibility.set(View.VISIBLE)
        titleSearchIconVisibility.set(View.GONE)
    }

    fun onTryAgainClicked(view: View){
        loaderVisibility.set(View.VISIBLE)
        loaderVisibility.set(View.VISIBLE)
        noInternetVisibility.set(View.GONE)
        getTrendingRepoList()
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
        loaderVisibility.set(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val repoList = repository.getRepositoriesList()
            repository.deleteAllRepos()
            repository.insertRepos(repoList.toTrendingRepositories())
            repoLiveData = repository.getAllRepos()
            withContext(Dispatchers.Main) {
                if (repoList.isEmpty()) {
                    listener?.showMessage("No repositories found")
                }
                listener?.stopLoader()
                loaderVisibility.set(View.GONE)
            }
        }
    }

    fun getRepos(): LiveData<List<TrendingRepositories>> {
        return repoLiveData
    }

    fun searchRepos(searchKey: String) {
        Timber.d("Search Key $searchKey")
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            repoLiveData = repository.getSearchedRepo(searchKey)
        }
    }

    fun showLoaderLayout() {
        loaderLayoutVisibility.set(View.VISIBLE)
        loaderVisibility.set(View.GONE)
        noInternetVisibility.set(View.VISIBLE)
    }

    fun hideLoaderLayout(){
        loaderLayoutVisibility.set(View.GONE)
        noInternetVisibility.set(View.GONE)
    }
}