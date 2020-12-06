package com.demo.trendinggithubrepo.repositories

import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.demo.trendinggithubrepo.network.ApiService
import com.demo.trendinggithubrepo.network.SafeApiRequest

class HomeRepository(private val apiService: ApiService) : SafeApiRequest() {

    suspend fun getRepositoriesList(
        language: String,
        since: String,
        searchQuery: String
    ): List<GitHubRepo> {
        return apiRequest { apiService.getRepositoriesList(language, since, searchQuery) }
    }
}