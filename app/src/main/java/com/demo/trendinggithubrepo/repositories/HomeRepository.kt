package com.demo.trendinggithubrepo.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.data.database.DatabaseClient
import com.demo.trendinggithubrepo.data.database.RepoDataBase
import com.demo.trendinggithubrepo.data.database.TrendingRepositories
import com.demo.trendinggithubrepo.network.ApiService
import com.demo.trendinggithubrepo.network.SafeApiRequest

class HomeRepository(private val apiService: ApiService) : SafeApiRequest() {

    suspend fun getRepositoriesList(): List<GitHubRepo> {
        return apiRequest { apiService.getRepositoriesList() }
    }

    companion object{
        var repoDataBase: RepoDataBase? = null
        var repoListLiveData : LiveData<List<TrendingRepositories>>? = null

        private fun initializeDB(context: Context) : RepoDataBase {
            return DatabaseClient.getInstance(context)!!.getRepoDatabase()
        }

        fun insertRepo(context: Context, repoList: List<TrendingRepositories>){
            repoDataBase = initializeDB(context)
            repoListLiveData = repoDataBase!!.repoDao().insertAll(repoList)
        }
    }
}