package com.demo.trendinggithubrepo.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.DatabaseClient
import com.demo.trendinggithubrepo.data.database.RepoDataBase
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.network.ApiService
import com.demo.trendinggithubrepo.network.SafeApiRequest

class HomeRepository(private val apiService: ApiService, private val context: Context) :
    SafeApiRequest() {

    suspend fun getRepositoriesList(): List<GitHubRepo> {
        return apiRequest { apiService.getRepositoriesList() }
    }

    private fun getRepoDatabase(): RepoDataBase {
        return DatabaseClient.getInstance(
            context
        )!!.getRepoDatabase()
    }

    fun insertRepos(repoList: List<TrendingRepositories>) {
        getRepoDatabase().repoDao().insertAll(repoList)
    }

    fun getAllRepos(): LiveData<List<TrendingRepositories>> {
        return getRepoDatabase().repoDao().allRepos
    }

    fun getSearchedRepo(searchQuery: String): List<TrendingRepositories> {
        return getRepoDatabase().repoDao().findBySearchKey(searchQuery)
    }

    fun deleteAllRepos() {
        getRepoDatabase().repoDao().deleteAll()
    }

}